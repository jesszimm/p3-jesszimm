package solvers;

import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskBad;

import java.util.List;

public class OutParallelBad implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        int X = GraphUtil.INF; //actually Integer.MAX_VALUE
        List[] adjList = Parser.parse(adjMatrix);
        int[] D1 = new int[adjMatrix.length]; //array of costs
        for (int i = 0; i < adjMatrix.length; i++) { D1[i] = X;} //set all unknown costs to infinity
        D1[source] = 0; //set cost of source to 0
        int[] P = new int[adjMatrix.length]; //array of predecessors
        for (int i = 0; i < adjMatrix.length; i++) { P[i] = -1;}
        for (int u = 0; u < adjMatrix.length; u++) { //loop through |V| times
            int[] D2 = ArrayCopyTask.copy(D1); //parallel copy of array
            P = RelaxOutTaskBad.parallel(adjList, adjMatrix, D1, D2, P); //relaxing the edges
        }
        return GraphUtil.getCycle(P);
    }
}