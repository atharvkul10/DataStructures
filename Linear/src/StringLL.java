
public class StringLL {

		public static StringNode addToFront (StringNode F, String newItem) {
			StringNode node = new StringNode (newItem, F);
			return node; // returns the address of the newly created node
		}
		public static void traverse (StringNode F) {
			for (StringNode ptr = F; ptr != null; ptr = ptr.next) {
				System.out.print(ptr.data + " -> ");
			}
			System.out.println("\\");
		}
		
		public static boolean search (StringNode F, String target) {
			for (StringNode ptr = F; ptr != null; ptr = ptr.next) {
				if(ptr.data.contentEquals(target)) {
					return true;
				}
			}
			return false;
		}
		public static void main(String[] args) {
			StringNode Front = null;
			Front = addToFront(Front, "apple");
			Front = addToFront(Front, "pear");
			Front = addToFront(Front, "oranges");
			traverse(Front);
			System.out.println(search(Front, "pear"));
			System.out.println(search(Front, "strawberry"));
		}
}
