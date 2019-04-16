import CITS2200.*;
import java.util.LinkedList; //java implementation of queue

public class SearchImp implements CITS2200.Search
{
    enum Colour { WHITE, GREY, BLACK;} //states of a vertex

    /**
     * Runs a *BFS* on a given directed, unweighted graph.
     * @param g Graph to be searched
     * @param sv the vertex on which to start the search (starting vertex)
     * @return an array listing the parent of each vertex in the spanning tree, or -1 is the vertex
     * is not reachable from the start vertex.
    **/
    public int[] getConnectedTree(Graph g, int startingvertex)
    {
        //BFS ADT's
        LinkedList q = new LinkedList(); //queue implementation
        Colour[] colour = setVertexColour(numvertices);
        int[] pi = initialiseParentArray(numvertices); //parent or predecessor array

        //GRAPH INFORMATION
        int[][] edgematrix = g.getEdgeMatrix();
        int numvertices = g.getNumberofVertices();

        //BFS ALGORITHM
        q.add(startingvertex);
        while(q.peek() != null) //NOT EMPTY
        {
            int w = q.remove(); //REMOVE FIRST ELEMENT IN THE QUEUE
            //FIND ADJACENT VERTICES TO w
            for(int x = 0; x < numvertices; x++)
            {
                if(edgematrix[w][x] == 1) //CONNECTED OR "CHILD" OF w
                {
                    if(colour[x] == Colour.WHITE) //WHITE
                    {
                        pi[x] = w;
                        colour[x] = Colour.GREY; //SET TO GREY
                        q.add(x);
                    }
                }
            }
            colour[w] = Colour.BLACK; //SET TO BLACK
        }

    }

    private static int[] intialiseParentArray(int size)
    {
        int[] a = new int[size];
        for(int i = 0; i < size; i++)
        {
            a[i] = -1;
        }
        return a;
    }

    private static Colour[] setVertexColour(int size)
    {
        Colour[] c = new Colour[size];
        for(int i = 0; i < size; i++)
        {
            c[i] = Colour.WHITE;
        }
        return c;
    }
    
    /**
     * Runs a *BFS* on a given directed, unweighted graph to find the distances of vertices from
     * the start vertex
     * @param g the Graph to eb searched
     * @param startVertex the vertex on which to statr the search
     * @result an array listing the parent of each vertex in the spann:ing tree,
     * or -1 is the vertex is not reachable from teh start vertex
    **/
    public int[] getDistances(Graph g, int startVertex)
    {
        
    }
    
    /**
     * Runs a *DFS on a given directed, unweighted graph to find the start time and finish time
     * for each vertex.
     * @param g the Graph to be searched
     * @param startVertex the vertex on which to start the search
     * @return a 2-dimensional array, where each sub-array has two elements:
     * the first is the start time, the second is the end time.
    **/
    public int[][] getTimes(Graph g, int startVertex)
    {
        
    }
}
