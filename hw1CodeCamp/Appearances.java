import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		int a_counter = 0;
		int b_counter = 0;
		int same_counter = 0;

		// Local Deep copy of a,b
		Collection<T> la, lb;
		la = new ArrayList<T>(a);
		lb = new ArrayList<T>(b);

		
		// Iterators and objects for temp storage
		Iterator<T> ir_a = la.iterator();
		Iterator<T> ir_b = lb.iterator();
		Iterator<T> ir_c = la.iterator(); 
		T element, element_a, element_b;
		
		// Looping through elements of la; 
		while (ir_c.hasNext()){
			a_counter = 0;
			b_counter = 0;
			element = ir_c.next();
//			System.out.println("P ir_c = " + element);
			
			ir_a = la.iterator();
			while (ir_a.hasNext()){
				element_a = ir_a.next();
//				System.out.println("P ir_a = " + element_a);
				if (element.equals(element_a)){
					a_counter++;
				}
			}
			
			ir_b = lb.iterator();
			while (ir_b.hasNext()){
				element_b = ir_b.next();
//				System.out.println("P ir_b = " + element_b);
				if ((element).equals(element_b)){
					b_counter++;
					ir_b.remove();
				}
			}
			
//			System.out.println("a_counter = " + a_counter + " b_counter = " + b_counter);
			ir_c.remove();
			if (a_counter == b_counter){
				same_counter++;
			}
			
		}
		return same_counter; // YOUR CODE HERE
	}

	
}
