import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		int maxRunValue = 1;
		int tempRunValue;
		if (str.equals("")) return 0;
		
		char cur_char, next_char; 
		for (int i=0; i<str.length(); i++) {
			cur_char = str.charAt(i);
			tempRunValue=0;
			for (int j=i; j<str.length(); j++) {
				next_char = str.charAt(j);
				if (cur_char == next_char){
					tempRunValue++;
				}else{
					break;
				}
			}
			if (maxRunValue < tempRunValue) maxRunValue = tempRunValue;
		}
		return maxRunValue; // YOUR CODE HERE
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
	//	StringBuffer my_str = new StringBuffer("");
		String my_str = "";
		int char_to_digit = 0;
		for (int i=0; i<str.length(); i++) {
			char_to_digit = str.charAt(i) - 48;
			if ( ('0'<=str.charAt(i)) && ('9'>=str.charAt(i)) && (i < (str.length() - 1)) ) {
//				System.out.println("Char " + str.charAt(i) + "Digit " + char_to_digit);
				for(int j=0; j<char_to_digit;j++){
//					my_str.append(str.charAt(i+1));
					my_str += (str.charAt(i+1));
				}
			}else if(('0'<=str.charAt(i)) && ('9'>=str.charAt(i))) {	
			}else{
//				my_str.append(str.charAt(i));
				my_str += (str.charAt(i));
			}
		}

//		System.out.println("String " + my_str);
//		String output_str = new toString(my_str);
		
		return my_str; // YOUR CODE HERE
		
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		Set<String> my_set_a = new HashSet<String>();
		Set<String> my_set_b = new HashSet<String>();
		if (len==0) return false;
		for (int i=0; i<=a.length()-len;i++){
			my_set_a.add(a.substring(i,i+len-1));
		}
		for (int i=0; i<=b.length()-len;i++){
			my_set_b.add(b.substring(i,i+len-1));
		}
		my_set_b.retainAll(my_set_a);
		if (my_set_b.size()>0) return true;
		return false; // YOUR CODE HERE
	}
	
	
	
	
}
