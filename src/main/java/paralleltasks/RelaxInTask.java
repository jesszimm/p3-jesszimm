package paralleltasks;

import cse332.exceptions.NotYetImplementedException;
import cse332.graph.GraphUtil;
import main.Parser;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class RelaxInTask {
    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    public static void parallel(List[] adjList, int[][] matrix, int[] D1, int[] D2, int[] P) {
        pool.invoke(new RelaxInTask.relaxInTask(adjList, matrix, D1, D2, P,0, matrix.length));
    }

    public static  class relaxInTask extends RecursiveAction {
        int lo, hi;
        public int[] P, D1, D2;
        List[] adjList;
        int[][] adjMatrix;

        relaxInTask(List[] adjList, int[][] adjMatrix, int[] D1, int[] D2, int[] P, int lo, int hi) {
            this.lo = lo;
            this.hi = hi;
            this.P = P;
            this.D1 = D1;
            this.D2 = D2;
            this.adjList = adjList;
            this.adjMatrix = adjMatrix;
        }

        protected void compute() {
            if (hi - lo <= CUTOFF) {
                sequential();
                return;
            }
            int mid = lo + (hi - lo) / 2;
            RelaxInTask.relaxInTask left = new RelaxInTask.relaxInTask(adjList, adjMatrix, D1, D2, P, lo, mid);
            RelaxInTask.relaxInTask right = new RelaxInTask.relaxInTask(adjList, adjMatrix, D1, D2, P, mid, hi);
            left.fork();
            right.compute();
            left.join();
        }

        public void sequential() {
            int v = lo;
            for (int w = 0; w < adjList[v].size(); w++) { //for each incoming edge from adjList[w] to v
                int vertex = (int) adjList[v].get(w); //assign adjList[w] to vertex source
                int cost = adjMatrix[vertex][v]; //cost from vertex to v
                if (D2[vertex] != GraphUtil.INF && D1[v] > D2[vertex] + cost) { //if old cost > this cost
                    D1[v] = D2[vertex] + cost; //replace old cost with this cost
                    P[v] = vertex; //update predecessor
                }
            }
        }
    }
}
