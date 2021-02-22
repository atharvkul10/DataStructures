
public class CLL<T> {

	GenericNode<T> tail; // reference to the last element of the CLL
	int size;
	
	CLL(){
		tail = null;
		size = 0;
	}
	public void addToFront (T newItem) {
		// create new node
		GenericNode<T> newNode = new GenericNode<T>(newItem, null);
		
		// insert at the front of the CLL
		if (tail == null) {
			//list is empty
			newNode.next = newNode;
			tail = newNode;
		} else {
			newNode.next = tail.next; // new node will point to the current front node
			tail.next = newNode; // last node will point to the new node
		}
		size ++;
		
	}
	
	public void deleteFront() {
		if(tail == tail.next || tail == null) {
			tail = null;
			size = 0;
		} else {
			tail.next = tail.next.next; // tail will point to the second item in the CLL
			size --;
		}
	}
	
	public void traverse() {
		
		if (tail == null) {
			System.out.println("CLL is empty");
			return;
		}
		GenericNode<T> ptr = tail.next; // start at the first node
		
		do {
			System.out.print(ptr.data + " -> ");
			ptr = ptr.next;
		} while(ptr != tail.next);
	}
}
