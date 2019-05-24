import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Integer;
import java.lang.String;
import java.lang.Exception;
import java.util.Stack;
import java.util.Iterator;

public class MyCITS2200Project implements CITS2200Project {
	//ENUMERATIONS
	enum Colour {WHITE, GREY, BLACK;} //colours representing the states of a vertex

	//FIELDS
	public  ArrayList<String>                     wikiAddr; //LOOKUP TABLE
	private int                                   maxvd; //number of vertex descriptor (similar to file descriptor)
	public  HashMap<Integer, LinkedList<Integer>> edgeList;
	public  ArrayList<Colour>					  colour;

	//CONSTRUCTOR
	public MyCITS2200Project() {
		this.wikiAddr = new ArrayList<>();
		this.edgeList = new HashMap<>();
		this.colour = new ArrayList<>();
		this.maxvd = 0;
	}

	//METHODS
	/**
	* Adds an edge to the Wikipedia page graph. If the pages do not
	* already exist in the graph, they will be added to the graph.
	* @param urlFrom the URL which has a link to urlTo.
	* @param urlTo the URL which urlFrom has a link to.
	*/
	public void addEdge(String urlFrom, String urlTo) {
		//SEARCH PARENT
		int parentvd = this.wikiAddr.indexOf(urlFrom);
		int childvd = this.wikiAddr.indexOf(urlTo);

		//TODO --> REMOVE CYCLICAL CASE
		if(parentvd < 0) { //vertex does not exist in lookup table
			parentvd = this.maxvd;
			this.wikiAddr.add(parentvd, urlFrom);
			this.edgeList.put(parentvd, new LinkedList<>());
			this.colour.add(Colour.WHITE);
			this.maxvd++;
		}
		else if(!this.edgeList.containsKey(parentvd)) { //does not have a linkedlist mapped
			this.edgeList.put(parentvd, new LinkedList<>());
		}
		if(childvd < 0) { //vertex does not exist in lookup table
			childvd = this.maxvd;
			this.wikiAddr.add(childvd, urlTo);
			this.colour.add(Colour.WHITE);
			this.maxvd++;
		}

		//ADD VERTICE DESCRIPTORS TO LINKED LIST
		//CHECK IF EDGE ALREADY EXISTS
		if(!edgeList.get(parentvd).contains(childvd)) { //edge does not exist in LinkedList
			edgeList.get(parentvd).add(childvd);
		}
		//Linked list already contains the edge
		return;
	}

	/**
	 * Search lookup table to find the vertex descriptor for a particular string
	 * @param String url of wiki address
	 * @return int vertex decriptor of node/vertex
	 */
	private int getVertexDescriptor(String url) throws Exception {
		int vd = this.wikiAddr.indexOf(url);
		if(vd < 0) { //vertex descriptor does not exist in lookup table
			throw new Exception("Wiki URL does not exist in lookup table and hence is not part of graph.");
		}
		return vd;
	}

	/**
	* Finds the shorest path in number of links between two pages.
	* If there is no path, returns -1.
	* 
	* @param urlFrom the URL where the path should start.
	* @param urlTo the URL where the path should end.
	* @return the length of the shorest path in number of links followed.
	*/
	public int getShortestPath(String urlFrom, String urlTo) {
		//arraylist.trimToSize();
		// bfs lab work: /Users/herbsca/OneDrive/UWA/CITS2200/cits2200/lab6/SearchImpS.java
		
		try {
			int startVertex = getVertexDescriptor(urlFrom); //vertex descriptor
			int endVertex = getVertexDescriptor(urlTo); //vertex descriptor

			MyBFS bfs = new MyBFS();
			return bfs.shortestPath(startVertex, endVertex);

		} catch(Exception e) {
				System.out.println(e.toString());
				return -1;
		}
	}

	/**
	* Finds all the centers of the page graph. The order of pages
	* in the output does not matter. Any order is correct as long as
	* all the centers are in the array, and no pages that aren't centers
	* are in the array.
	* 
	* @return an array containing all the URLs that correspond to pages that are centers.
	*/
	public String[] getCenters() {
	return new String[1];
	}

	/**
	* Finds all the strongly connected components of the page graph.
	* Every strongly connected component can be represented as an array 
	* containing the page URLs in the component. The return value is thus an array
	* of strongly connected components. The order of elements in these arrays
	* does not matter. Any output that contains all the strongly connected
	* components is considered correct.
	* 
	* @return an array containing every strongly connected component.
	*/
	public String[][] getStronglyConnectedComponents() {
		return new String[1][1];
	}

	/**
	* Finds a Hamiltonian path in the page graph. There may be many
	* possible Hamiltonian paths. Any of these paths is a correct output.
	* This method should never be called on a graph with more than 20
	* vertices. If there is no Hamiltonian path, this method will
	* return an empty array. The output array should contain the URLs of pages
	* in a Hamiltonian path. The order matters, as the elements of the
	* array represent this path in sequence. So the element [0] is the start
	* of the path, and [1] is the next page, and so on.
	* 
	* @return a Hamiltonian path of the page graph.
	*/
	public String[] getHamiltonianPath() {
		return new String[1];
	}

	/*******************************************************************/
	private class MyBFS {
		//FIELDS
		private int[] distance; 
		private HashMap<Integer, LinkedList<Integer>> vertexEdges;

		/**
		 * CONSTRUCTOR OF BFS CLASS
		 */
		public MyBFS() throws Exception {
			int numNodes = MyCITS2200Project.this.maxvd;
			this.distance = new int[numNodes];
			this.vertexEdges = MyCITS2200Project.this.edgeList;
		}

		/**
		 * RUN ENTIRE BFS ALGORITHM ON THE ESTABLISHED GRAPH.
		 * GARUANTEES SHORTEST PATH DISTANCE FIELD WILL BE UP-TO-DATE.
		 * @param startVertex the starting vertex of the BFS.
		 */
		public void run(int startVertex) throws Exception {

			LinkedList<Integer> q = new LinkedList<>(); //queue implementation
			LinkedList<Integer> ll; //adjacency list implementation
			ArrayList<Colour> colour = MyCITS2200Project.this.colour; //all nodes are set as unvisited
			if(colour.size() < MyCITS2200Project.this.maxvd) throw new Exception("ALL NODES ARE NOT SET AS 'UNVISITED'.");
			int w, x;

			//BFS ALGORITHM
			this.distance[startVertex] = 0;
			q.add(startVertex);
			while(q.peek() != null) {
				w = (int) q.remove(); //REMOVE FIRST ELEMENT IN THE QUEUE
				//FIND ADJACENT VERTICES TO w
				ll = MyCITS2200Project.this.edgeList.get(w); //adjacency list for the node
				if(ll == null) continue; //NO VALUE MAPPED TO KEY
				System.out.println(ll.toString());
				Iterator<Integer> it = ll.listIterator(0);
				while(it.hasNext()) {
					//CONNECTED OR "CHILD" OF w
					x = it.next();
					if(x == w) continue; //non-cyclical
					System.out.println(x);
					if(colour.get(x) == Colour.WHITE) { //WHITE OR NOT SEEN
						distance[x] = distance[w] + 1;
						colour.set(x, Colour.GREY); //SET TO GREY OR SEEN
						q.add(x);
					}
				}
				colour.set(w, Colour.BLACK); //SET TO BLACK
			}
		}

		/**
		 * GET THE SHORTEST PATH TO EACH NODE.
		 */
		public int[] getDistances() {
			return this.distance;
		}

		/**
		 * GET THE SHORTEST PATH FOR A SPECIFIC NODE.
		 */
		public int getDistances(int node) {
			return this.distance[node];
		}

		/**
		 * RUN A BFS OVER AN ESTABLISHED GRAPH AND EXIT AS SOON AS
		 * THE ENDING NODE IS REACHED.
		 * NOT GARUANTEED TO COMPLETELY FILL SHORTEST PATH DISTANCE ARRAY
		 * @param int startVertex is the starting vertex.
		 * @param int endVertex is the ending vertex.
		 * @return int shortest path to get to the ending vertex. 
		 */
		public int shortestPath(int startVertex, int endVertex) throws Exception {

			LinkedList<Integer> q = new LinkedList<>(); //queue implementation
			LinkedList<Integer> ll;
			Iterator<Integer> it;
			int w, x;
			ArrayList<Colour> colour = MyCITS2200Project.this.colour; //all nodes are set as unvisited
			if(colour.size() < MyCITS2200Project.this.maxvd) throw new Exception("ALL NODES ARE NOT SET AS 'UNVISITED'.");

			//BFS ALGORITHM
			distance[startVertex] = 0;
			q.add(startVertex);
			while(q.peek() != null) {
				w = (int) q.remove(); //REMOVE FIRST ELEMENT IN THE QUEUE
				//FIND ADJACENT VERTICES TO w
				ll = MyCITS2200Project.this.edgeList.get(w); //adjacency list for the node
				if(ll == null) continue; //NO VALUE MAPPED TO KEY
				System.out.println(ll.toString());
				it = ll.listIterator(0); //iterator starting at the first element
				while(it.hasNext()) {
					//CONNECTED OR "CHILD" OF w
					x = it.next();
					System.out.println(x);
					if(colour.get(x) == Colour.WHITE) { //WHITE OR NOT SEEN
						if(x == w) continue; //non-cyclical
						distance[x] = distance[w] + 1;
						if(x == endVertex) return distance[endVertex]; //seen end point
						colour.set(x, Colour.GREY); //SET TO GREY OR SEEN
						q.add(x);
					}
				}
				colour.set(w, Colour.BLACK); //SET TO BLACK
			}
			throw new Exception("BFS FAILED TO FIND THE END VERTEX");
		}
	}

	/*************************************************************/
	private class MySCC {
		//FIELDS
		private Stack<Integer> stack;
		private ArrayList<ArrayList<Integer>> scc;
		private ArrayList<LinkedList<Integer>> transAdjList; //transposed adjacency list
		private ArrayList<Colour> colour;

		/**
		 * CONSTRUCTOR OF STRONGLY CONNECTED COMPONENTS (SCC) CLASS.
		 */
		public MySCC() {
			this.stack = new Stack<>();
			this.scc = new ArrayList<>();
			this.transAdjList = new ArrayList<>();
			this.colour = MyCITS2200Project.this.colour;
		}

		/**
		* Recursive DFS implementation starting at the vertex descriptor specified
		* pushing to the stack 
		* Colour.WHITE indicates node has not been visited.
		* Colour.BLACK indicates node has been visited.
		* @param node int vertex decriptor
		*/
		private void dfs(int node, int index) {
			LinkedList<Integer> ll = transAdjList.get(node); //adjacently list for vertices
			if(ll == null) return; //NO CONNECTED NODES TO ADD TO SCC
			//ArrayList<Integer> al = this.scc.get(index); //ARRAYLIST CONTAINING NODE DESCRIPTOR OF SCCs
			int x;
			this.colour.set(node, Colour.BLACK);

			while(ll.peek() != null) {
				x = ll.remove();
				this.scc.get(index).add(x); //ADD NODE TO ARRAYLIST OF ARRAYLIST CONTAINING SCC's
				if(colour.get(x) == Colour.WHITE) //Colour.WHITE symbolises NOT VISITED
					this.dfs(x, index);
			}
		}
		
		/**
		* MUTATOR METHOD TRANSPOSING THE ADJACENCY LIST OF THE KNOWN GRAPH.
		* 
		*/
		private void transpose() {
			Iterator<Integer> it;
			int vd;

			int numEdges = MyCITS2200Project.this.edgeList.size();
			for(int i = 0; i < numEdges; i++) { //CYCLE THROUGH EACH VERTEX AND ITS CONNECTIONS
				if(MyCITS2200Project.this.edgeList.get(i) == null) continue; //NO DEFINED EDGES
				it = MyCITS2200Project.this.edgeList.get(i).listIterator();
				while(it.hasNext()) {
					vd = it.next();
					if(this.transAdjList.get(vd) == null) this.transAdjList.set(vd, new LinkedList<>());
					this.transAdjList.get(vd).add(i); //order of adjcency list does not matter
				}
			}
		}
		/**
		* Implements Kosaraju's algorithm performing a DFS twice on a predefined graph
		* to list all strongly connected nodes/components
		* @return array containing every strongly connected component (node descriptors)
		*/
		public ArrayList<ArrayList<Integer>> getSCC() {
			ArrayList<Colour> col = MyCITS2200Project.this.colour; //ALL NODES MARKED AS NOT VISITED
			Stack<Integer> dfsStack = new Stack<>();
			LinkedList<Integer> ll;
			Iterator<Integer> it;
			int node, x;
			//int size = MyCITS2200Project.this.maxvd;

			//NON-RECURSIVE DFS IMPLEMENTATION
			dfsStack.push(0); //starting at vertex descriptor 0
			while(dfsStack.peek() != null) {
				node = dfsStack.pop();
				if(col.get(node) == Colour.WHITE) { //Colour.WHITE symbolises not visited
					stack.push(node); //push node to stack
					col.set(node, Colour.BLACK); //Colour.BLACK symblises visited
					ll = MyCITS2200Project.this.edgeList.get(node); //GET ADJACENCY LIST FOR GRAPH
					if(ll == null) continue; //no connected nodes
					it = ll.listIterator();
					while(it.hasNext()) { //ADD ALL CONNECTED NODES TO DFS STACK
						x = it.next(); //CONNECTED NODE USE ITERATOR????
						if(col.get(x) == Colour.WHITE) { //NOT VISITED
							dfsStack.push(x);
						}
					}
				}
			}

			//transpose graph
			this.transpose();

			int numscc = 0; //SET NUMBER OF STRONGLY CONNECTED COMPONENTS TO ZERO
			//CALLS RECURSIVE DFS IMPLEMENTATION
			while(this.stack.peek() != null) {
				node = this.stack.pop();
				if(this.colour.get(node) == Colour.WHITE) { //NOT VISITED
					this.scc.add(new ArrayList<>()); //NEW STRONGLY CONNECTED COMPONENT
					this.dfs(node, numscc); //RECURSIVE DFS IMPLEMENTATION
					numscc++;
				}
			}

			return this.scc;
		}
	}

/*************************************************************************/

}
