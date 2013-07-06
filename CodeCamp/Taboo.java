
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	private List<T> rules;
	public Taboo(List<T> rules) {
		this.rules = rules;
//		System.out.println(this.rules);
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		 Set<T> noFollowSet = new HashSet<T>(); 
		 Iterator<T> Ir1 = rules.iterator();
		 T elem_1;
		 int addFlag = 0;
		 while(Ir1.hasNext()){
			 elem_1 = Ir1.next();
			 if(addFlag==1){
				 noFollowSet.add(elem_1);
				 addFlag=0;
			 }
			 if(elem_1.equals(elem)) addFlag = 1;
//			 System.out.println("Ir1 " + Ir1.hasNext() + " Ir2 " + Ir2.hasNext() + " elem1 " + elem_1);
		 }
		 
		 return noFollowSet; // YOUR CODE HERE
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		ListIterator<T> Ir_list = list.listIterator();
//		List<T> my_list = new ArrayList<T>();
		T elem_list_prev, elem_list_cur = null;
		int initFlag = 0;
		while(Ir_list.hasNext()){
			if(initFlag == 1){
				elem_list_prev = elem_list_cur;
				elem_list_cur = Ir_list.next();			
				while(check_violation(elem_list_prev,elem_list_cur)){
//					System.out.println("1. Prev " + elem_list_prev + " Cur " + elem_list_cur);
					Ir_list.remove();
					if (Ir_list.hasNext()){
						elem_list_cur = Ir_list.next();
//						System.out.println("2. Prev " + elem_list_prev + " Cur " + elem_list_cur);
					}else{
	//					list = my_list;
//						System.out.println("Prev " + elem_list_prev + " Cur " + elem_list_cur + " list " + list);
						return;
					}
				}
//				my_list.add(elem_list_cur);
			}else{
				initFlag = 1;
				elem_list_cur = Ir_list.next();
//				my_list.add(elem_list_cur);
			}
		}
//		list = my_list;
		return;
	}
	
	
	private boolean check_violation(T elem_prev, T elem_cur){
		Iterator<T> Ir_checkrule = this.rules.iterator();
		T elem_rule_prev = null, elem_rule_cur = null;
		int initFlag = 0;
		while(Ir_checkrule.hasNext()){
			if(initFlag == 1){
				elem_rule_prev = elem_rule_cur;		
				elem_rule_cur = Ir_checkrule.next();
				if((elem_prev.equals(elem_rule_prev)) && (elem_cur.equals(elem_rule_cur))) return true;
			}else{
				initFlag = 1;
				elem_rule_cur = Ir_checkrule.next();			
			}
		}
		return false;
	}
}
