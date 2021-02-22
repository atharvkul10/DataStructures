
public class DLL<T> {

	DLLNode<T> front;
	int size;
	
	DLL(){
		front = null;
		size = 0;
	}
	
	public void addToFront(T newData) {
		
		DLLNode<T> newNode = new DLLNode<T>(newData,null,front);
		
		if (front != null) {
		front.previous = newNode;
		}
		front = newNode;
		size++;
	}
	
	public void addAfter(T target, T newData) {
		DLLNode<T> ptr = front;
		while (ptr != null) {
			if(ptr.data.equals(target)) {
				// found target, insert newNode
				DLLNode<T> newNode = new DLLNode<T>(newData, ptr, ptr.next);
				ptr.next = newNode;
				if(newNode.next != null) {
					newNode.next.previous = newNode;
				}
			}
		}
	}
	public void traverse() {
		DLLNode<T> ptr = front;
		while(ptr != null) {
			System.out.print(ptr.data + " -> ");
			ptr = ptr.next;
		}
		System.out.println("\\");
	}

}
