import java.util.NoSuchElementException;


public class GenericLL <T> {

	GenericNode <T> front;
	int size;
	
	GenericLL () {
		front = null;
		size = 0;
	}
	
	public int getSize() {
		return size;
	}
	
	public void addToFront (T newItem) {
		front = new GenericNode<T>(newItem, front);
		size += 1;
	}
	
	public void traverse () {
		for ( GenericNode<T> ptr = front; ptr != null; ptr = ptr.next) {
			System.out.print(ptr.data + " -> ");
		}
		System.out.println("\\");
	}
	
	public GenericNode<T> deleteFront(){
		if(front == null) {
			// LL is empty shouldnt be allowed to delete (ERROR)
			throw new NoSuchElementException("Deleting from empty list");
		}
		else {
			GenericNode<T> tmp = front; // save the reference to the first node
			front = front.next; // remove the first node
			size --;
			return tmp; // return back to the caller the deleted node;
		}
	}
}
