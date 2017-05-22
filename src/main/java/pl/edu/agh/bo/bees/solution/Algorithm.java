package pl.edu.agh.bo.bees.solution;

import pl.edu.agh.bo.bees.Main;
import pl.edu.agh.bo.bees.input.Input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Algorithm {
    private int selectedSites;
    private int bestSites;
    private int scouts;
    private int beesForSelectedSites;
    private int beesForBestSites;
    private int iterations;

    private Input input;

    public Algorithm(Input input) throws IOException {
        this.input = input;

        InputStream is = null;

        try {
            is = new FileInputStream("C:/Users/Kamil/SkyDrive/Studia Semestr 6/6 BO/BO/src/main/resources/algorithm.properties");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        properties.load(is);
        is.close();

        selectedSites =Integer.valueOf(properties.getProperty("SELECTED_SITES"));
        bestSites =Integer.valueOf(properties.getProperty("BEST_SITES"));
        scouts =Integer.valueOf(properties.getProperty("SCOUTS"));
        beesForSelectedSites =Integer.valueOf(properties.getProperty("BEES_FOR_SELECTED_SITES"));
        beesForBestSites =Integer.valueOf(properties.getProperty("BEES_FOR_BEST_SITES"));
        iterations =Integer.valueOf(properties.getProperty("ITERATIONS"));
    }

    public void calculate(SolutionCallback callback) {
        List<Solution> bestSolutionHistory = new ArrayList<>(iterations + 2);
        Solution bestSolution;

        Swarm scouts = new Swarm(this.scouts, input);
        List<Swarm> swarmsForSelectedSites = prepareSwarms(input, selectedSites - bestSites, beesForSelectedSites);
        List<Swarm> swarmsForBestSites = prepareSwarms(input, bestSites, beesForBestSites);

        List<Solution> selectedSites = getFirstSelectedSites(scouts, swarmsForSelectedSites, swarmsForBestSites);
        List<Solution> bestSites = selectedSites.subList(0, this.bestSites);
        selectedSites = selectedSites.subList(this.bestSites, this.selectedSites);
        bestSolution = bestSites.get(0);
        bestSolutionHistory.add(bestSolution);

        boolean stop = false;
        int iterations = 0;
        while (!stop) {
            scouts.scout();

            for (int i = 0; i < this.selectedSites - this.bestSites; i++) {
                swarmsForSelectedSites.get(i).goToSite(selectedSites.get(i));
            }
            for (int i = 0; i < this.bestSites; i++) {
                swarmsForBestSites.get(i).goToSite(bestSites.get(i));
            }
            List<Solution> allSites = getNewSelection(scouts, swarmsForSelectedSites, swarmsForBestSites);
            bestSites = allSites.subList(0, this.bestSites);
            selectedSites = allSites.subList(this.bestSites, this.selectedSites);

            if (bestSites.get(0).evaluate() < bestSolution.evaluate()) {
                bestSolution = bestSites.get(0);
            }

            if (callback != null)
                callback.onSolution(bestSites.get(0), bestSolution);

            if(++iterations >= this.iterations) {
                stop = true;
            }
        }

        bestSolution = chooseBestSolution(this.selectedSites - this.bestSites, swarmsForSelectedSites, selectedSites, bestSolution);
        bestSolution = chooseBestSolution(this.bestSites, swarmsForBestSites, bestSites, bestSolution);
        if (callback != null)
            callback.onAlgorithmEnd(bestSolution);
    }

    private Solution chooseBestSolution(int sitesCount, List<Swarm> swarms, List<Solution> solutions, Solution bestSolution) {
        for (int i = 0; i < sitesCount; i++) {
            swarms.get(i).goToSite(solutions.get(i));
            if (swarms.get(i).getBest().evaluate() < bestSolution.evaluate()) {
                bestSolution = swarms.get(i).getBest();
            }
        }
        return bestSolution;
    }

    private List<Swarm> prepareSwarms(Input input, int sites, int beesPerSite) {
        List<Swarm> swarmsForSelectedSites = new ArrayList<>();
        for (int i = 0; i < sites; i++) {
            swarmsForSelectedSites.add(new Swarm(beesPerSite, input));
        }
        return swarmsForSelectedSites;
    }

    private List<Solution> getNewSelection(Swarm scouts, List<Swarm> swarmsForSelectedSites, List<Swarm> swarmsForBestSites) {
        List<Solution> allSites = new ArrayList<>(this.scouts + selectedSites + bestSites);
        allSites.addAll(scouts.getAllBees());
        swarmsForBestSites.forEach(swarm -> allSites.add(swarm.getBest()));
        swarmsForSelectedSites.forEach(swarm -> allSites.add(swarm.getBest()));
        Collections.sort(allSites);
        return allSites;
    }

    private List<Solution> getFirstSelectedSites(Swarm scouts, List<Swarm> swarms1, List<Swarm> swarms2) {
        List<Solution> bestSitesCandidates = scouts.getBest(selectedSites);
        swarms1.forEach(swarm -> bestSitesCandidates.addAll(swarm.getBest(selectedSites)));
        swarms2.forEach(swarm -> bestSitesCandidates.addAll(swarm.getBest(selectedSites)));
        Collections.sort(bestSitesCandidates);
        return bestSitesCandidates.subList(0, selectedSites+bestSites);
    }

    public int getSelectedSites() {
        return selectedSites;
    }
    public void setSelectedSites(int selectedSites) {
        this.selectedSites = selectedSites;
    }

    public int getBestSites() {
        return bestSites;
    }
    public void setBestSites(int bestSites) {
        this.bestSites = bestSites;
    }

    public int getScouts() {
        return scouts;
    }
    public void setScouts(int scouts) {
        this.scouts = scouts;
    }

    public int getBeesForSelectedSites() {
        return beesForSelectedSites;
    }
    public void setBeesForSelectedSites(int beesForSelectedSites) {
        this.beesForSelectedSites = beesForSelectedSites;
    }

    public int getBeesForBestSites() {
        return beesForBestSites;
    }
    public void setBeesForBestSites(int beesForBestSites) {
        this.beesForBestSites = beesForBestSites;
    }

    public int getIterations() {
        return iterations;
    }
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

}
