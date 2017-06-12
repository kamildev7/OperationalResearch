package pl.edu.agh.bo.bees.solution;

import pl.edu.agh.bo.bees.input.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Swarm {
    private ArrayList<Solution> bees;
    private int beesQuantity;
    private Input input;

    Swarm(int beesQuantity, Input input) {
        this.input = input;
        this.beesQuantity = beesQuantity;
        this.bees = new ArrayList<>(beesQuantity);
        scout();
    }

    void scout() {
        bees = new ArrayList<>(beesQuantity);
        for (int i = 0; i < beesQuantity; i++) {
            bees.add(new Solution(input));
        }
        bees.parallelStream().forEach(Solution::evaluate);
    }

    void goToSite(Solution site) {
        bees = new ArrayList<>(beesQuantity);
        for (int i = 0; i < beesQuantity; i++) {
            bees.add(new Solution(site));
        }
        bees.parallelStream().forEach(Solution::evaluate);
    }

    Solution getBest() {
        return getBest(1).get(0);
    }

    List<Solution> getBest(int n) {
        Collections.sort(bees);
        if (bees.size() >= n) {
            return bees.subList(0, n);
        } else {
            return bees;
        }
    }

    List<Solution> getAllBees() {
        return bees;
    }
}
