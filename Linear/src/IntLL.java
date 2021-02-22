
public class IntLL {
	
	/*
	 * Inserts a node at the front of the LL
	 * item is the data of the new node
	 * F is address of the front of the LL
	 */
	public static Node addToFront(int item, Node F) {
		Node n = new Node (item, F);
		return n;
	}
	/*
	 * Traverses the entire LL printing out each node's data part
	 */
	public static void traverse (Node F) {
		Node ptr = F; // create a hopping pointer
		while ( ptr != null ) {
			System.out.print(ptr.data + " -> "); // prints the pointer's data part
			ptr = ptr.next; // hop into the next node
		}
		System.out.println("\\");
	}
	public static Node deleteFront (Node F) {
		return F.next; // returns the address or reference to the second node
	}
	public static boolean search (int target, Node F) {
		Node ptr = F; // creates a hopping pointer that first points to the front of the LL
		while (ptr != null) {
			// ptr is pointing to an object of type Node
			if (ptr.data == target) {
				return true;
			}
			ptr = ptr.next; // hop into the next node
		}
		return false;
	}
	
	public static boolean addAfter (Node F, int target, int newItem)
	{
		for (Node ptr = F; ptr != null; ptr = ptr.next) {
			if (ptr.data == target) {
				// found target, insert newItem after target
				Node n = new Node(newItem, ptr.next); // creates new node which points to ptr.next
				ptr.next = n; // ptr.next is updated to point to the new node
				return true;
			}
		}
		return false;
	}
	
	public static Node delete (Node F, int target) {
		/* Traverse thee LL looking for target.
		 * Have to keep two pointers: a current and previous
		 */
		Node ptr = F;
		Node prev = null;
		
		while (ptr != null && ptr.data != target) {
			prev = ptr; // make previous point to where current is pointing to 
			ptr = ptr.next; // make current ahead, one hop
		}
		/*
		 * 2. Delete and return the front
		 */
		if( ptr == F) {
			// target is the front
			return F.next;
		}
		else {
			prev.next = ptr.next;
			return F;
		}
	}
	public static void main (String[] args) {
		
		Node Front = null;
		Front = addToFront(6, Front);
		Front = addToFront(5, Front);
		Front = addToFront(4, Front);
		Front = addToFront(3, Front);
		traverse(Front);
		Front = deleteFront(Front);
		traverse(Front);
		System.out.println(search(8, Front));
		System.out.println(search(5, Front));
		System.out.println(addAfter(Front,6,10));
		System.out.println(addAfter(Front,6,8));
		traverse(Front);
		Front = delete(Front, 6);
		traverse(Front);
	}
}