package pl.edu.agh.bo.bees.input;

import java.util.LinkedList;
import java.util.List;


public class Input {

    private int stockProvidersAmount;
    private StockProvider[] stockProviders;
    private int factoriesAmount;
    private Factory[] factories;
    private List<Demand> demands = new LinkedList<>();

    public void setStockProvidersAmount(int stockProvidersAmount) {
        this.stockProvidersAmount = stockProvidersAmount;
        this.stockProviders = new StockProvider[stockProvidersAmount];
    }

    public void setFactoriesAmount(int factoriesAmount) {
        this.factoriesAmount = factoriesAmount;
        this.factories = new Factory[factoriesAmount];
    }

    public void addStockProvider(int i, StockProvider sp) {
        stockProviders[i] = sp;
    }

    public void addFactory(int i, Factory factory) {
        factories[i] = factory;
    }

    public List<Demand> getDemands() {
        return this.demands;
    }

    public int getStockProvidersAmount() {
        return stockProvidersAmount;
    }

    public int getFactoriesAmount() {
        return factoriesAmount;
    }

    public StockProvider getStockProvider(int i) {
        return stockProviders[i];
    }

    public Factory getFactory(int i) {
        return factories[i];
    }

    public void addDemand(Demand demand) {
        demands.add(demand);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < stockProvidersAmount; i++) {
            result += String.format("StockProvider #%d:\n", i + 1);
            result += stockProviders[i].toString();
        }

        for (Demand demand : demands) {
            result += demand.toString();
        }

        return result;
    }

    public boolean isInputOK() {
        if (stockProviders == null || demands == null) {
            return false;
        }
        int size = 0;
        int demandsAmount = 0;
        for (StockProvider sp : stockProviders) {
            size += sp.getSize();
        }
        for (Demand demand : demands) {
            demandsAmount += demand.getGoodAmount();
        }
        return size >= demandsAmount;
    }

}
