import java.io.*;
import java.util.*;

/**
 * NOTE: CHANGE ALL REFERENCE TO MYCITS2200PROJECT TO THE INTERFACE CLASS CITS2200PROJECT FOR TEST
 */

public class CITS2200ProjectTester {
	private MyCITS2200Project myProj;
	private static final boolean SP = true;
	private static final boolean SCC = true;
	private static final boolean HAM = true;
	private static final boolean CEN = true;

	/** 
	 * MAIN FUNCTION
	 */
	public static void main(String[] args) {
		// Change this to be the path to the graph file.
		String pathToGraphFile = args[0];
		// Create an instance of your implementation.
		CITS2200Project proj = new MyCITS2200Project();
		// Load the graph into the project.
		loadGraph(proj, pathToGraphFile);

		/** TESTING **/
		//printLookupTable(proj.wikiLookup);
		//printAdjacencyList(proj.edgeList);

		print("TESTING");
		if(SP)
			getShortestPath(proj, "NODE1", "NODE3");
		if(SCC)
			getSCC(proj.getStronglyConnectedComponents());
		if(HAM)
			getHamPath(proj.getHamiltonianPath());
		if(CEN)
			getGraphCenters(proj.getCenters());
	}
	
	/**
	 * READS GIVEN FILE AND ESTABLISHES GRAPH NODES AND EDGES.
	 */
	public static void loadGraph(CITS2200Project project, String path) {
		// The graph is in the following format:
		// Every pair of consecutive lines represent a directed edge.
		// The edge goes from the URL in the first line to the URL in the second line.
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while (reader.ready()) {
				String from = reader.readLine();
				String to = reader.readLine();
				System.out.println("Adding edge from " + from + " to " + to);
				project.addEdge(from, to);
			}
		} catch (Exception e) {
			System.out.println("There was a problem:");
			e.printStackTrace();
		}
	}


	/**
	 * SIMPLIFIED PRINTING TO STANDARD OUTPUT STREAM
	 */
	public static void print(String s) {
		System.out.println(s);
	}

	/**
	 * PRINT THE VERTEX DESCRIPTORS OF RECOGNISED WIKI ADDRESS NODES.
	 * DEBUGGING FUNCTION
	 */
	public static void printLookupTable(HashMap<Integer, String> map) {
		int size = map.size();
		for(int i = 0; i < size; i++) { 
			System.out.println("vertex descriptor:\t" + i + "\taddress:\t" + map.get(i));
		}
	}

	/**
	 * PRINT A CERTAIN ADJACENCY LIST OF THE ESTABLISHED GRAPH AND GIVEN NODE.
	 * DEBUGGING FUNCTION.
	 */
	public static void printAdjacencyList(ArrayList<LinkedList<Integer>> map) {
		int size = map.size();
		for(int node = 0; node < size; node++) { 
			LinkedList<Integer> ll = map.get(node);
			if(ll == null) continue;
			int len = ll.size();
			for(int i = 0; i < len; i++) {
				System.out.println(node + " " + ll.get(i));
			}		
		}
	}

	/**
	* TEST AND PRINT OUT THE SHORTEST PATH BETWEEN TWO DEFINED NODES.
	**/
	public static void getShortestPath(CITS2200Project cits, String str1, String str2) {
		String startVertex = str1;
		String endVertex = str2;

		int sp = cits.getShortestPath(startVertex, endVertex);
		print("SHORTEST PATH BETWEEN: \n\t" + startVertex + "\n\t" + endVertex + "\n\t ==> " + sp);
	}

	/**
	* TEST AND PRINT OUT THE STRONGLY CONNECTED COMPONENTS OF THE GIVEN GRAPH
	**/
	public static void getSCC(String[][] scc) {
		//String[][] scc = cits.getStronglyConnectedComponents();
		int size = scc.length;
		int n;
		print("STRONGLY CONNECTED COMPONENTS TESTING:");
		for(int i = 0; i < size; i++) {
			n = scc[i].length;
			print("COMPONENT:\t" + i);
			for(int y = 0; y < n; y++) {
				print("-> " + scc[i][y]);
			}
		}
	}

	public static void getHamPath(String[] hp) {
		int size = hp.length;
		print("HAMILTONIAN PATH TESTING:");
		for(int i = 0; i < size; i++) {
			print("->\t" + hp[i]);
		}
	}

	public static void printDP(ArrayList<BitSet> arr, int n) {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < (1 << n); j++) {
				System.out.println("dp[" + i + "][" + j + "] =\t" + arr.get(i).get(j));
			}
		}
	}

	public static void getGraphCenters(String[] gc) {
		int size = gc.length;
		print("GRAPH CENTERS:");
		for(int i = 0; i < size; i++) {
			print("->\t" + gc[i]);
		}
	}
}