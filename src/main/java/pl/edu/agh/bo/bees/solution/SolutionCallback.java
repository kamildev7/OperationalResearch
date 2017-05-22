package pl.edu.agh.bo.bees.solution;

public interface SolutionCallback {
    void onSolution(Solution actualSolution, Solution bestSolution);
    void onAlgorithmEnd(Solution bestSolution);
}
