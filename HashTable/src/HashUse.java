import java.util.HashMap;

class Person{
	String firstName, lastName;
	String email;
	
	public Person(String fN, String lN, String email) {
		this.firstName = fN;
		this.lastName = lN;
		this.email  = email;
	}
	
	public boolean equals(Object o) {
		if(o==null || !(o instanceof Person)) {
			return false;
		}
		Person other = (Person)o;
		return other.email.equals(email);
	}
	public String toString() {
		return"[" + firstName + "," + lastName + "] - " + email;
	}
}

public class HashUse {

	
	public static void main(String[] args) {
		//declaring and allocating a hash table
		HashMap<String,Person> contacts = new HashMap<String,Person>(100, 2);
		
		Person p1 = new Person("Ana Paula", "Centeno", "anapaula@cs.rutgers.edu");
		Person p2 = new Person("Sesh", "Venugopal", "venugopa@cs.rutgers.edu");
		
		//insert into hash table
		contacts.put("anapaula@cs.rutgers.edu", p1);
		contacts.put("venugopa@cs.rutgers.edu", p2);
		
		//retrieve from the hash table
		System.out.println(contacts.get("venugopa@cs.rutgers.edu"));
		
		System.out.println("Iterating over keys");
		
	}
}
