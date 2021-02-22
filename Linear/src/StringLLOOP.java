
public class StringLLOOP {
	
	private StringNode front; // reference to the first node of the LL
	private int size; // keep track of the number of the nodes in the LL
	
	StringLLOOP (){
		front = null;
		size = 0;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public StringNode getFront() {
		return this.front;
	}
	
	public void addToFront (String newItem) {
		StringNode node = new StringNode(newItem, front);
		front = node;
		size++;
	}
	
	public boolean search (String target) {
		for(StringNode ptr = front; ptr != null; ptr = ptr.next) {
			if(ptr.data.contentEquals(target)) {
				return true;
			}
		}
		return false;
	}
}
