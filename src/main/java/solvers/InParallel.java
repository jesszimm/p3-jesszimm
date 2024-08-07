package solvers;

import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxInTask;

import java.util.List;

public class InParallel implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List[] adjList = Parser.parseInverse(adjMatrix);
        int X = GraphUtil.INF; //actually Integer.MAX_VALUE
        int[] D1 = new int[adjMatrix.length]; //array of costs
        for (int i = 0; i < adjMatrix.length; i++) { D1[i] = X;} //set all unknown costs to infinity
        D1[source] = 0; //set cost of source to 0
        int[] P = new int[adjMatrix.length]; //array of predecessors
        for (int i = 0; i < adjMatrix.length; i++) { P[i] = -1;}
        for (int u = 0; u < adjMatrix.length; u++) { //loop through |V| times
            int[] D2 = ArrayCopyTask.copy(D1); //parallel copy of array
            RelaxInTask.parallel(adjList, adjMatrix, D1, D2, P); //relaxing the edges
        }
        return GraphUtil.getCycle(P);
    }
}