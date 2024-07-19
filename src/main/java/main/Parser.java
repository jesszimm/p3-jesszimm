package main;

import cse332.exceptions.NotYetImplementedException;

import java.util.LinkedList;
import java.util.List;

public class Parser {

    /**
     * Parse an adjacency matrix into an adjacency list.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list
     */
    public static List[] parse(int[][] adjMatrix) {
        //return an array of lists of outgoing edges
        List[] adjList = new List[adjMatrix.length]; //create list to hold results
        for (int i = 0; i < adjMatrix.length; i++) { //for each vertex i
            adjList[i] = new LinkedList<Integer>(); //create list to hold outgoing edges of vertex i
            for (int j = 0; j < adjMatrix[i].length; j++) { //for each vertex j
                if (adjMatrix[i][j] != Integer.MAX_VALUE) { //if there is an edge going from i to j
                    adjList[i].add(j); //insert edge into list
                }
            }
        }
        return adjList;
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list with incoming edges
     */
    public static List[] parseInverse(int[][] adjMatrix) {
        //return an array of lists of incoming edges
        List[] adjList = new List[adjMatrix.length]; //create list to hold results
        for (int i = 0; i < adjMatrix.length; i++) { //for each vertex i
            adjList[i] = new LinkedList<Integer>(); //create list to hold incoming edges of vertex i
            for (int j = 0; j < adjMatrix[i].length; j++) { //for each vertex j
                if (adjMatrix[j][i] != Integer.MAX_VALUE) { //if there is an edge from j to i
                    adjList[i].add(j); //insert edge into list
                }
            }
        }
        return adjList;
    }
}
