
public class GenericNode <T> {

	T data; // data can be of any data type
	GenericNode<T> next; // link to the next GenericNode in the LL
	
	GenericNode (T data, GenericNode<T> next){
		this.data = data;
		this.next = next;
	}
}
