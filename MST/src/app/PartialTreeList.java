package app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Arc;
import structures.Graph;
import structures.PartialTree;
import structures.Vertex;
import structures.MinHeap;


//Last eedited by Atharv Kulkarni May 3, 4:38PM


/**
 * Stores partial trees in a circular linked list
 * 
 */
public class PartialTreeList implements Iterable<PartialTree> {

	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;

		/**
		 * Next node in linked list
		 */
		public Node next;

		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;

	/**
	 * Number of nodes in the CLL
	 */
	private int size;

	/**
	 * Initializes this list to empty
	 */
	public PartialTreeList() {
		rear = null;
		size = 0;
	}

	/**
	 * Adds a new tree to the end of the list
	 * 
	 * @param tree Tree to be added to the end of the list
	 */
	public void append(PartialTree tree) {
		Node ptr = new Node(tree);
		if (rear == null) {
			ptr.next = ptr;
		} else {
			ptr.next = rear.next;
			rear.next = ptr;
		}
		rear = ptr;
		size++;
	}

	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {

		PartialTreeList List = new PartialTreeList();
		for(int i = 0; i < graph.vertices.length; i++) {
			Vertex v1 = graph.vertices[i];
			Vertex.Neighbor neighbor = v1.neighbors;
			PartialTree tempTree = new PartialTree(v1);
			while(neighbor != null) {
				Vertex v2 = neighbor.vertex;
				Arc tempArc = new Arc(v1,v2,neighbor.weight);
				tempTree.getArcs().insert(tempArc);
				neighbor = neighbor.next;
			}
			System.out.println(tempTree.toString());
			List.append(tempTree);
		}
		return List;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * for that graph
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<Arc> execute(PartialTreeList ptlist) {

		ArrayList<Arc> arc = new ArrayList<>();
		while(ptlist.size() > 1) {
			PartialTree partialTreeX = ptlist.remove();
			MinHeap<Arc> priorityQueueX = partialTreeX.getArcs();
			Arc tempArc = priorityQueueX.deleteMin();
			while(tempArc != null) {
				Vertex v1 = tempArc.getv1();
				Vertex v2 = tempArc.getv2();
				System.out.println("v1: " + v1.toString() + " v2: " + v2.toString());
//				int weight = tempArc.getWeight();
				PartialTree partialTreeY = ptlist.removeTreeContaining(v2);
				if(partialTreeY != null) {
					partialTreeX.merge(partialTreeY);
					arc.add(tempArc);
					ptlist.append(partialTreeX);
					break;
				}
			}
			tempArc = priorityQueueX.deleteMin();
		}
//		System.out.println(ptlist.remove().toString());
		return arc;
	}

	/**
	 * Removes the tree that is at the front of the list.
	 * 
	 * @return The tree that is removed from the front
	 * @throws NoSuchElementException If the list is empty
	 */
	public PartialTree remove() 
			throws NoSuchElementException {

		if (rear == null) {
			throw new NoSuchElementException("list is empty");
		}
		PartialTree ret = rear.next.tree;
		if (rear.next == rear) {
			rear = null;
		} else {
			rear.next = rear.next.next;
		}
		size--;
		return ret;

	}

	/**
	 * Removes the tree in this list that contains a given vertex.
	 * 
	 * @param vertex Vertex whose tree is to be removed
	 * @return The tree that is removed
	 * @throws NoSuchElementException If there is no matching tree
	 */
	public PartialTree removeTreeContaining(Vertex vertex) 
		    throws NoSuchElementException {
		    		/* COMPLETE THIS METHOD */
		    		PartialTree temp= null; 
		    		
		    		if (rear== null) {
		    			throw new NoSuchElementException("List is Empty"); 
		    		}
		    		
		    		Node pntr=rear;
		    		
		    		do {
		    			PartialTree tree= pntr.tree;
		    			if (checkVertex(tree, vertex)) {
		    				temp = tree; 
		    				Node trav = pntr;
		    				while(trav.next != pntr) {
		    					trav = trav.next;
		    				}
		    				if (pntr.next == pntr && pntr == trav) {
		    					rear = null;
		    				} else if(pntr.next == trav) {
		    					if(pntr == rear) {
		    						rear = rear.next;
		    					}
		    					pntr.next.next = pntr.next;
		    				}else {
		    					if(pntr == rear) {
		    						rear = trav;
		    					}
		    					trav.next = pntr.next;
		    				}
		    				pntr.next = null;
		    				size--;
		    				return temp; 
		    			}
		    			pntr=pntr.next; 
		    		}
		    		while ( pntr != rear);
		    		throw new NoSuchElementException("List is Empty");
		    		}
		     
		    
		    private void removeNode(Node temp1) {
				Node first;
				first= temp1;
				
				while(!(first.next==temp1)) {
					first= first.next; 
				}
				Node nextNode= temp1.next; 
				if ( nextNode == temp1 && first==temp1) {
					rear= null;
					size--; 
				} else if (nextNode== first){
					if(temp1==rear) {
						rear=rear.next; 
					}
					temp1.next.next=temp1.next; 
					size--; 	
				} else {
					if (temp1 == rear) {
						rear= first; 
					}
					first.next=nextNode; 
					size--; 
				}
				temp1.next=null; 
			}

			private boolean checkVertex(PartialTree tree, Vertex v) {
				Vertex parentTree= v;
				while (parentTree.parent!= parentTree) {
					parentTree= parentTree.parent; 
				}
				return parentTree == tree.getRoot(); 
			}

	/**
	 * Gives the number of trees in this list
	 * 
	 * @return Number of trees
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns an Iterator that can be used to step through the trees in this list.
	 * The iterator does NOT support remove.
	 * 
	 * @return Iterator for this list
	 */
	public Iterator<PartialTree> iterator() {
		return new PartialTreeListIterator(this);
	}

	private class PartialTreeListIterator implements Iterator<PartialTree> {

		private PartialTreeList.Node ptr;
		private int rest;

		public PartialTreeListIterator(PartialTreeList target) {
			rest = target.size;
			ptr = rest > 0 ? target.rear.next : null;
		}

		public PartialTree next() 
				throws NoSuchElementException {
			if (rest <= 0) {
				throw new NoSuchElementException();
			}
			PartialTree ret = ptr.tree;
			ptr = ptr.next;
			rest--;
			return ret;
		}

		public boolean hasNext() {
			return rest != 0;
		}

		public void remove() 
				throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

	}
}


