
public class GenericLLDriver {

	public static void main(String[] args) {
		
		GenericLL<Integer> numbers = new GenericLL<Integer>();
		System.out.println(numbers.getSize());
		numbers.addToFront(1);
		numbers.addToFront(2);
		numbers.addToFront(3);
		numbers.traverse();
		System.out.println(numbers.getSize());
		
		GenericLL<String> animals = new GenericLL<String>();
		animals.deleteFront();
		animals.addToFront("cat");
		animals.deleteFront();
		System.out.println(animals.getSize());
	}
}
