package pl.edu.agh.bo.bees.solution;

import pl.edu.agh.bo.bees.input.Demand;
import pl.edu.agh.bo.bees.input.Input;
import pl.edu.agh.bo.bees.input.StockProvider;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Solution implements Comparable<Solution> {
    private Map<Demand, StockProvider> solution = new HashMap<>();
    private CompletableFuture<Double> evaluation;

    Solution(Input input) {
        int spsAmount = input.getStockProvidersAmount();
        Random random = new Random();
        for (Demand demand : input.getDemands()) {
            int index = random.nextInt(spsAmount);
            solution.put(demand, input.getStockProvider(index));
        }
        evaluation = CompletableFuture.supplyAsync(this::doEvaluation);
    }

    Solution(Solution neighbour) {
        Set<Demand> keySet = neighbour.getSolution().keySet();
        for (Demand demand : keySet) {
            this.solution.put(demand, neighbour.getSolution().get(demand));
        }

        Set<StockProvider> values = new HashSet<>(solution.values());
        if (values.size() > 1) {
            Random random = new Random();
            List<Demand> keys = new ArrayList<>(solution.keySet());
            Demand key1 = keys.get(random.nextInt(keys.size()));
            Demand key2 = keys.get(random.nextInt(keys.size()));
            StockProvider sp1 = solution.get(key1);
            StockProvider sp2 = solution.get(key2);
            while (key1.equals(key2) || sp1.equals(sp2)) {
                key1 = keys.get(random.nextInt(keys.size()));
                key2 = keys.get(random.nextInt(keys.size()));
                sp1 = solution.get(key1);
                sp2 = solution.get(key2);
            }
            StockProvider tmp = this.solution.get(key1);
            this.solution.put(key1, solution.get(key2));
            this.solution.put(key2, tmp);
        }
        evaluation = CompletableFuture.supplyAsync(this::doEvaluation);
    }

    public double evaluate() {
        try {
            return evaluation.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Double.MAX_VALUE;
        }
    }

    private double doEvaluation() {
        if (isProper() > 1 || isProper() < 0) {
            return Double.MAX_VALUE;
        }
        Set<Demand> demandSet = solution.keySet();
        double result = 0;

        for (Demand demand : demandSet) {
            result += demand.getFactory().getPosition().getDistance(solution.get(demand).getPosition()) * demand.getGoodAmount();
        }

        return result;
    }

    private Map<Demand, StockProvider> getSolution() {
        return this.solution;
    }

    private int isProper() {
        Map<StockProvider, Integer> map = new HashMap<>();
        Set<Demand> keys = solution.keySet();
        for (Demand demand : keys) {
            StockProvider sp = solution.get(demand);
            if (map.containsKey(sp)) {
                int value = map.get(sp) + demand.getGoodAmount();
                map.put(sp, value);
            } else {
                map.put(sp, demand.getGoodAmount());
            }
        }

        int result = 0;

        if (solution.keySet().size() > 50) {
            Set<StockProvider> keys2 = map.keySet();
            for (StockProvider sp : keys2) {
                if (map.get(sp) > sp.getSize()) result++;
            }
        } else {
            Set<StockProvider> keys2 = map.keySet();
            for (StockProvider sp : keys2) {
                if (map.get(sp) > sp.getSize()) result = -1;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        Set<Demand> demandSet = solution.keySet();
        for (Demand demand : demandSet) {
            result += String.format("%s\n%s\n", demand.toString(), solution.get(demand).toString());
        }

        result += String.format("Score: %f\n", evaluate());
        result += String.format("Is proper: %s", isProper() == 0 ? "yes" : String.format("no, wrong on %d positions", isProper()));

        return result;
    }


    @Override
    public int compareTo(Solution other) {
        double thisValue = evaluate();
        double otherValue = other.evaluate();
        if (thisValue == otherValue) return 0;
        else if (thisValue > otherValue) return 1;
        else return -1;
    }
}
