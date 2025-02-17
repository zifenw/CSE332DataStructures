import java.util.*;

public class DonorGraph {
    private List<List<Match>> adjList;

    // The donatingTo array indicates which repient each donor is
    // affiliated with. Specifically, the donor at index i has volunteered
    // to donate a kidney on behalf of recipient donatingTo[i].
    // The matchScores 2d array gives the match scores associated with each
    // donor-recipient pair. Specificically, matchScores[x][y] gives the
    // HLA score for donor x and reciplient y.
    public DonorGraph(int[] donorToBenefit, int[][] matchScores){
        // TODO
        int numDonors = donorToBenefit.length;
        adjList = new ArrayList<>(numDonors);

        // Initialize the adjacency list with empty lists for each donor
        for (int i = 0; i < numDonors; i++) {
            adjList.add(new ArrayList<>());
        }

        // Populate the adjacency list with matches
        for (int donor = 0; donor < numDonors; donor++) {
            // The beneficiary this donor is affiliated with
            int beneficiary = donorToBenefit[donor];
            for (int recipient = 0; recipient < matchScores[donor].length; recipient++) {
                // If the match score >= 60, add a match to the adjacency list
                if (matchScores[donor][recipient] >= 60) {
                    adjList.get(beneficiary).add(new Match(donor, beneficiary, recipient));
                }
            }
        }
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public boolean isAdjacent(int start, int end){
        for(Match m : adjList.get(start)){
            if(m.recipient == end)
                return true;
        }
        return false;
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public int getDonor(int beneficiary, int recipient){
        for(Match m : adjList.get(beneficiary)){
            if(m.recipient == recipient)
                return m.donor;
        }
        return -1;
    }


    // returns a chain of matches to make a donor cycle
    // which includes the given recipient.
    // Returns an empty list if no cycle exists. 
    public List<Match> findCycle(int recipient){
        List<Match> cycle = new ArrayList<>();
        boolean[] visited = new boolean[adjList.size()]; // Track visited nodes to avoid cycles
        if (dfs(recipient, recipient, visited, cycle)) {
            return cycle; // Return the cycle if found
        }
        return new ArrayList<>(); // Return an empty list if no cycle exists
    }

    // Helper method for DFS to find a cycle
    private boolean dfs(int start, int current, boolean[] visited, List<Match> cycle) {
        visited[current] = true;  // Mark the current node as visited

        // Explore all matches from the current node
        for (Match match : adjList.get(current)) {
            // If we find a match that completes the cycle, add it and return true
            if (match.recipient == start && cycle.size() > 0) {
                cycle.add(match);
                return true;
            }
            // If the recipient hasn't been visited, recursively explore
            if (!visited[match.recipient]) {
                cycle.add(match); // Add the current match to the cycle
                if (dfs(start, match.recipient, visited, cycle)) {
                    return true; // If a cycle is found, return true
                }
                cycle.remove(cycle.size() - 1); // Backtrack if no cycle is found
            }
        }
        return false; // No cycle found
    }

    // returns true or false to indicate whether there
    // is some cycle which includes the given recipient.
    public boolean hasCycle(int recipient){
        boolean[] visited = new boolean[adjList.size()]; // Track visited nodes
        return dfs(recipient, recipient, visited, new ArrayList<>()); // Use DFS to check for a cycle
    }
}
