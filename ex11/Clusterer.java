import java.util.*;

public class Clusterer {
    private List<List<WeightedEdge<Integer, Double>>> adjList; // the adjacency list of the original graph
    private List<List<WeightedEdge<Integer, Double>>> mstAdjList; // the adjacency list of the minimum spanning tree
    private List<List<Integer>> clusters; // a list of k points, each representing one of the clusters.
    private double cost; // the distance between the closest pair of clusters

    public Clusterer(double[][] distances, int k){
        int n = distances.length;
        adjList = new ArrayList<>(n);
        mstAdjList = new ArrayList<>(n);       

        // Initialize adjacency lists for the original graph and MST
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
            mstAdjList.add(new ArrayList<>());
        }

        // Build the adjacency list for the original graph
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double weight = distances[i][j];
                adjList.get(i).add(new WeightedEdge<>(i, j, weight));
                adjList.get(j).add(new WeightedEdge<>(j, i, weight)); // Undirected graph
            }
        }     
        // Run Prim's algorithm to construct the MST
        prims(0);

        // Create k clusters by removing the k-1 heaviest edges in the MST
        makeKCluster(k);   
    }

    // implement Prim's algorithm to find a MST of the graph.
    // in my implementation I used the mstAdjList field to store this.
    private void prims(int start){
        int n = adjList.size();
        boolean[] visited = new boolean[n];
        PriorityQueue<WeightedEdge<Integer, Double>> pq = new PriorityQueue<>();

        // Start with the initial node
        visited[start] = true;       
        for (WeightedEdge<Integer, Double> edge : adjList.get(start)) {
            pq.add(edge);
        }

        while (!pq.isEmpty()) {
            WeightedEdge<Integer, Double> edge = pq.poll();
            int u = edge.destination;  
            if (!visited[u]) {
                visited[u] = true;
                // Add the edge to the MST adjacency list (both directions for undirected graph)
                mstAdjList.get(edge.source).add(edge);
                mstAdjList.get(u).add(new WeightedEdge<>(u, edge.source, edge.weight));

                for (WeightedEdge<Integer, Double> nextEdge : adjList.get(u)) {
                    if (!visited[nextEdge.destination]) {
                        pq.add(nextEdge);
                    }                
                }
            }          
        }

    }


    // After making the minimum spanning tree, use this method to
    // remove its k-1 heaviest edges, then assign integers
    // to clusters based on which nodes are still connected by
    // the remaining MST edges.
    private void makeKCluster(int k){
        // Collect all edges in the MST
        List<WeightedEdge<Integer, Double>> edges = new ArrayList<>();
        for (int i = 0; i < mstAdjList.size(); i++) {
            for (WeightedEdge<Integer, Double> edge : mstAdjList.get(i)) {
                if (edge.source < edge.destination) { // Avoid adding duplicate edges
                    edges.add(edge);
                }
            }
        }
        // Sort edges in descending order of weight
        edges.sort((e1, e2) -> e2.weight.compareTo(e1.weight));

        // Remove the k-1 heaviest edges
        for (int i = 0; i < k - 1; i++) {
            WeightedEdge<Integer, Double> edge = edges.get(i);
            mstAdjList.get(edge.source).remove(edge);
            mstAdjList.get(edge.destination).removeIf(e -> e.destination.equals(edge.source));
        }
        // Use the weight of the lowest-weight edge you removed
        // as the value of the cost field of the object.
        // Set the cost to the weight of the last removed edge
        if (k > 1) {
            cost = edges.get(k - 2).weight;
        } else {
            cost = 0; // If k == 1, no edges are removed
        }

        // Perform BFS to identify clusters
        clusters = new ArrayList<>();
        boolean[] visited = new boolean[mstAdjList.size()];
        for (int i = 0; i < mstAdjList.size(); i++) {
            if (!visited[i]) {
                List<Integer> cluster = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i);
                visited[i] = true;          
                
                while (!queue.isEmpty()) {
                    int node = queue.poll();
                    cluster.add(node);

                    for (WeightedEdge<Integer, Double> edge : mstAdjList.get(node)) {
                        if (!visited[edge.destination]) {
                            visited[edge.destination] = true;
                            queue.add(edge.destination);
                        }
                    }
                }

                clusters.add(cluster);
            }
        }
    }

    public List<List<Integer>> getClusters(){
        return clusters;
    }

    public double getCost(){
        return cost;
    }

}
