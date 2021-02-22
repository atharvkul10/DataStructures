
public class DLLNode<T> {

	DLLNode<T> previous; // refers to the previous node in the LL
	T data;
	DLLNode<T> next; // refers to the next node in the LL
	
	DLLNode(T data, DLLNode<T> previous, DLLNode<T> next){
		this.data = data;
		this.previous = previous;
		this.next = next;
	}
}
