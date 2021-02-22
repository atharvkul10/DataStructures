/*
 * Node class to be used as a unit in the Linked List
 */
public class Node {

	// fields of the class
	int data; // data part of the node
	Node next; // a reference to the next node in the linked list
	
	// Constructor
	public Node (int data, Node next) {
		this.data = data;
		this.next = next;
	}
}