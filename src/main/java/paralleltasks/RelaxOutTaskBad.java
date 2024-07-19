package paralleltasks;

import cse332.graph.GraphUtil;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RelaxOutTaskBad {
    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;
    public static int[][] adjMatrix;
    public static List[] adjList;

    public static int[] parallel(List[] list, int[][] matrix, int[] D1, int[] D2, int[] P) {
        adjMatrix = matrix;
        adjList = list;
        return pool.invoke(new RelaxOutTaskBad.relaxOutBadTask(D1, D2, P, 0, adjMatrix.length));
    }

    private static class relaxOutBadTask extends RecursiveTask<int[]> {
        int[] D1, D2, P;
        int hi, lo;
        relaxOutBadTask(int[] D1, int[] D2, int[] P, int lo, int hi) {
            this.D1 = D1;
            this.D2 = D2;
            this.P = P;
            this.lo = lo;
            this.hi = hi;
        }
        protected int[] compute() {
            if (hi - lo <= CUTOFF) {
                sequential(P, lo);
                return P;
            }
            int mid = lo + (hi - lo) / 2;
            RelaxOutTaskBad.relaxOutBadTask left = new RelaxOutTaskBad.relaxOutBadTask(D1, D2, P, lo, mid);
            RelaxOutTaskBad.relaxOutBadTask right = new RelaxOutTaskBad.relaxOutBadTask(D1, D2, P, mid, hi);
            left.fork();
            right.compute();
            left.join();
            return P;
        }

        public void sequential(int[] P, int v) {
            for (int w = 0; w < adjList[v].size(); w++) { //for each outgoing edge from v
                int edge = (int) adjList[v].get(w); //other vertex in edge
                int cost = adjMatrix[v][edge]; //find cost in matrix
                if (D2[v] != GraphUtil.INF && D1[edge] > D2[v] + cost) { //if old cost > this cost
                    D1[edge] = D2[v] + cost; //replace old cost with this cost
                    P[edge] = v; //update predecessor
                }
            }
        }
    }
}
