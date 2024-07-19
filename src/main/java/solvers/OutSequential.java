package solvers;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;

import java.util.List;

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        int X = GraphUtil.INF; //actually Integer.MAX_VALUE
        int V = adjMatrix.length; //number of vertices
        //array of lists where for all edges (u, v) u is array index and v is in list at that index
        List[] g = Parser.parse(adjMatrix);
        int[] D1 = new int[V]; //array of costs
        int[] costs = new int[V]; //array of costs
        for (int i = 0; i < V; i++) { D1[i] = X;} //set all unknown costs to infinity
        D1[source] = 0; //set cost of source to 0
        int[] P = new int[V]; //array of predecessors
        for (int i = 0; i < P.length; i++) { P[i] = -1;}
        for (int u = 0; u < V; u++) { //loop through |V| times
            costs = ArrayCopyTask.copy(D1); //parallel copy of array
            //relaxing the edges
            for (int v = 0; v < V; v++) { //for each vertex v
                for (int w = 0; w < g[v].size(); w++) { //for each outgoing edge from v
                    int edge = (int)g[v].get(w);
                    int cost = adjMatrix[v][edge]; //find cost in matrix
                    if (costs[v] != X && D1[edge] > costs[v] + cost) { //if old cost > this cost
                        D1[edge] = costs[v] + cost; //replace old cost with this cost
                        P[edge] = v; //update predecessor
                    }
                }
            }
        }
        return GraphUtil.getCycle(P);
    }

}