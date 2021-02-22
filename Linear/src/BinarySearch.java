
public class BinarySearch {

	public static int iterativeBinarySearch (int[] A, int target) {
		
		int l = 0;
		int r = A.length - 1;
		
		while(l <= r) {
			int m = (l + r)/2;
			if(A[m] == target) {
				// found it
				return m;
			} else if (A[m] < target) {
				// target is in the upper half
				l = m + 1;
			} else {
				//target is in the lower half
				r = m -1;
			}
		}
		//target not found
		return -1;
	}
	
	public static int recursiveBinarySearch(int [] A, int target, int l, int r) {
		if (l > r) {
			//target not found
			return -1;
		} 
		int m = (l + r)/2;
		if(A[m] == target) {
			// found target
			return m;
		} else if(A[m] < target) {
			// target is in the upper half
			return recursiveBinarySearch(A, target, m + 1, r);
		} else {
			// target is in the lower half
			return recursiveBinarySearch(A, target, l, m - 1);
		}
	}
	
	public static void main(String[] args) {
		int[] array = {22, 25, 36, 38, 42, 55, 66, 98};
		System.out.println(iterativeBinarySearch(array, 120));
		System.out.println(iterativeBinarySearch(array, 36));
		System.out.println(recursiveBinarySearch(array, 120, 0, array.length - 1));
		System.out.println(recursiveBinarySearch(array, 36, 0, array.length - 1));
	}
}
