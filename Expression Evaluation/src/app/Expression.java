package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

// last edited Mar 8, 1:41pm

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	delims = "\t*+-/()[]0123456789";
    	expr = expr.replace(" ","");
    	boolean flag = true;
    	//System.out.println(expr);
    	StringTokenizer token = new StringTokenizer(expr, delims, true);
    	StringTokenizer token2 = new StringTokenizer(expr, delims, true);
    	token2.nextToken();
    	while(token2.hasMoreTokens()) {
    		String pntr = token.nextToken();
    		//System.out.println(pntr);
    		String pntr2 = token2.nextToken();
    		//System.out.println(pntr2);
	    	if (pntr2.equals("[")) {
	    		for(int i = 0; i < arrays.size(); i++) {
	    			if (pntr.contentEquals(arrays.get(i).name)) {
	    				flag = false;
	    			}
	    		}
	    		if(flag) {
	    			arrays.add(new Array(pntr));
	    		}
	    		flag = true;
	    		continue;
	    	} else if(delims.contains(pntr)){
	    		continue;
	    	} else {
	    		for(int i = 0; i < vars.size(); i++) {
	    			if (pntr.contentEquals(vars.get(i).name)) {
	    				flag = false;
	    			}
	    		}
	    		if(flag) {
	    			vars.add(new Variable(pntr));
	    		}
	    		flag = true;
	    		continue;
	    	}
    	}
    	String pntr = token.nextToken();
    	if(delims.contains(pntr)){
    	} else {
    		vars.add(new Variable(pntr));
    	}
    	System.out.println(vars.toString());
		System.out.println(arrays.toString());
    	return;    			
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
        
    private static String solveSimp(String expr, ArrayList<Variable> vars) {
    	delims = "\t*+-/()[]";
    	String numbers = "0123456789";
    	if(expr.length() == 0) {
    		return expr;
    	}
    	if(expr.indexOf("*") < 0 && expr.indexOf("/") < 0 && expr.indexOf("-") < 0 && expr.indexOf("+") < 0) {
    		return expr;
    	}
    	int operIndex;
    	int oneIndex;
    	int twoIndex;
    	float x = 0;
    	float y = 0;
    	boolean operMulti = true;
    	
    	if(expr.indexOf("*") > 0 || expr.indexOf("/") > 0) {
    		if(expr.indexOf("*") > 0) {
    			if(expr.indexOf("/") > 0) {
    				if(expr.indexOf("*") < expr.indexOf("/")) {
    					operIndex = expr.indexOf("*");
    				} else {
    					operIndex = expr.indexOf("/");
            			operMulti = false;
    				}
    			} else {
    				operIndex = expr.indexOf("*");
    			}
    		}
    		else {
    			operIndex = expr.indexOf("/");
    			operMulti = false;
    		}
    		oneIndex = operIndex - 1;
    		twoIndex = operIndex + 1;
    		while(oneIndex > 0) {
    			System.out.println("OneIndex: " + expr.charAt(oneIndex));
    			System.out.println("OneIndex-1: " + expr.charAt(oneIndex-1));
    			if((expr.charAt(oneIndex - 1) != '*' && expr.charAt(oneIndex - 1) != '+' && expr.charAt(oneIndex - 1) != '/' && expr.charAt(oneIndex - 1) != '-')) {
    				oneIndex --;
    			} else {
    				break;
    			}	
    		}
    		while(twoIndex != expr.length() - 1) {
    			if((expr.charAt(twoIndex + 1) != '*' && expr.charAt(twoIndex + 1) != '+' && expr.charAt(twoIndex + 1) != '/' && expr.charAt(twoIndex + 1) != '-')) {
    				twoIndex ++;
    			} else {
    				break;
    			}	
    		}
    		if(numbers.contains(Character.toString(expr.charAt(oneIndex))) || expr.charAt(oneIndex) == '!') {
    			
    			System.out.println("x:   " + expr.substring(oneIndex,operIndex));
    			if(expr.charAt(oneIndex) == '!') {
    				System.out.println("PreDelete:   " + expr);
    				expr = expr.substring(0,oneIndex) + expr.substring(oneIndex + 1);
    				System.out.println("PostDelete:   " + expr);
    				operIndex--;
    				twoIndex--;
    				x = -1 *Float.parseFloat(expr.substring(oneIndex,operIndex));
    			}else {
    				x = Float.parseFloat(expr.substring(oneIndex,operIndex));
    			}
    		} else {
    			for(int i = 0; i < vars.size(); i++) {
    				if(vars.get(i).name.equals(expr.substring(oneIndex,operIndex))) {
    					
    					System.out.println("x:   " + Float.toString(vars.get(i).value));
    					
    					x = vars.get(i).value;
    					break;
    				}
    			}
    		}
    		if(numbers.contains(expr.substring(twoIndex,twoIndex+1))|| expr.charAt(operIndex + 1) == '!') {
    			
    			System.out.println("y:   " + expr.substring(operIndex +1,twoIndex + 1));
    			if(expr.charAt(operIndex + 1) == '!') {
    				expr = expr.substring(0,operIndex + 1) + expr.substring(operIndex + 2);
    				twoIndex--;
    				y = -1 * Float.parseFloat(expr.substring(operIndex +1,twoIndex + 1));
    			}else {
    				y = Float.parseFloat(expr.substring(operIndex +1,twoIndex + 1));
    			}
    			System.out.println("y:   " + expr.substring(operIndex +1,twoIndex + 1));
    		} else {
    			for(int i = 0; i < vars.size(); i++) {
    				if(vars.get(i).name.equals(expr.substring(operIndex +1,twoIndex + 1))) {
    					System.out.println("y:   " + Float.toString(vars.get(i).value));
    					
    					y = vars.get(i).value;
    					break;
    				}
    			}
    		}
    		if (operMulti) {
    			if(twoIndex == expr.length() -1) {
    				expr = expr.substring(0,oneIndex) + Float.toString(x*y);
    			} else {
    				expr = expr.substring(0,oneIndex) + Float.toString(x*y) + expr.substring(twoIndex + 1);
    			}
    		} else {
    			if(twoIndex == expr.length() -1) {
    				expr = expr.substring(0,oneIndex) + Float.toString(x/y);
    			} else {
    				expr = expr.substring(0,oneIndex) + Float.toString(x/y) + expr.substring(twoIndex + 1);
    			}
    		}
    		return solveSimp(expr, vars);
    	} 
    	
    	operMulti = true;
    	if(expr.indexOf("+") > 0 || expr.indexOf("-") > 0) {
    		if(expr.indexOf("+") > 0) {
    			if(expr.indexOf("-") > 0) {
    				if(expr.indexOf("+") < expr.indexOf("-")) {
    					operIndex = expr.indexOf("+");
    				} else {
    					operIndex = expr.indexOf("-");
            			operMulti = false;
    				}
    			} else {
    				operIndex = expr.indexOf("+");
    			}
    		}
    		else {
    			operIndex = expr.indexOf("-");
    			operMulti = false;
    		}
    		oneIndex = operIndex - 1;
    		twoIndex = operIndex + 1;
    		while(oneIndex > 0) {
    			System.out.println("OneIndex: " + expr.charAt(oneIndex));
    			System.out.println("OneIndex-1: " + expr.charAt(oneIndex-1));
    			if((expr.charAt(oneIndex - 1) != '*' && expr.charAt(oneIndex - 1) != '+' && expr.charAt(oneIndex - 1) != '/' && expr.charAt(oneIndex - 1) != '-')) {
    				oneIndex --;
    			} else {
    				break;
    			}	
    		}
    		while(twoIndex != expr.length() - 1) {
    			if((expr.charAt(twoIndex + 1) != '*' && expr.charAt(twoIndex + 1) != '+' && expr.charAt(twoIndex + 1) != '/' && expr.charAt(twoIndex + 1) != '-')) {
    				twoIndex ++;
    			} else {
    				break;
    			}	
    		}
    		if(numbers.contains(Character.toString(expr.charAt(oneIndex))) || expr.charAt(oneIndex) == '!') {
    			
    			System.out.println("x:   " + expr.substring(oneIndex,operIndex));
    			if(expr.charAt(oneIndex) == '!') {
    				System.out.println("PreDelete:   " + expr);
    				expr = expr.substring(0,oneIndex) + expr.substring(oneIndex + 1);
    				System.out.println("PostDelete:   " + expr);
    				operIndex--;
    				twoIndex--;
    				x = -1 *Float.parseFloat(expr.substring(oneIndex,operIndex));
    			}else {
    				x = Float.parseFloat(expr.substring(oneIndex,operIndex));
    			}
    		} else {
    			for(int i = 0; i < vars.size(); i++) {
    				if(vars.get(i).name.equals(expr.substring(oneIndex,operIndex))) {
    					
    					System.out.println("x:   " + Float.toString(vars.get(i).value));
    					
    					x = vars.get(i).value;
    					break;
    				}
    			}
    		}
    		if(numbers.contains(expr.substring(twoIndex,twoIndex+1))|| expr.charAt(operIndex + 1) == '!') {
    			
    			System.out.println("y:   " + expr.substring(operIndex +1,twoIndex + 1));
    			if(expr.charAt(operIndex + 1) == '!') {
    				expr = expr.substring(0,operIndex + 1) + expr.substring(operIndex + 2);
    				twoIndex--;
    				y = -1 * Float.parseFloat(expr.substring(operIndex +1,twoIndex + 1));
    			}else {
    				y = Float.parseFloat(expr.substring(operIndex +1,twoIndex + 1));
    			}
    			System.out.println("y:   " + expr.substring(operIndex +1,twoIndex + 1));
    		} else {
    			for(int i = 0; i < vars.size(); i++) {
    				if(vars.get(i).name.equals(expr.substring(operIndex +1,twoIndex + 1))) {
    					System.out.println("y:   " + Float.toString(vars.get(i).value));
    					
    					y = vars.get(i).value;
    					break;
    				}
    			}
    		}
    		if (operMulti) {
    			if(twoIndex == expr.length() -1) {
    				expr = expr.substring(0,oneIndex) + Float.toString(x+y);
    			} else {
    				expr = expr.substring(0,oneIndex) + Float.toString(x+y) + expr.substring(twoIndex + 1);
    			}
    		} else {
    			if(twoIndex == expr.length() -1) {
    				expr = expr.substring(0,oneIndex) + Float.toString(x-y);
    			} else {
    				expr = expr.substring(0,oneIndex) + Float.toString(x-y) + expr.substring(twoIndex + 1);
    			}
    		}
    		return solveSimp(expr, vars);
    	} 
    	return expr;
    }
    
    
    private static String convertNeg(String expr) {
    	int negIndex = expr.indexOf("-");
    	if (negIndex < 0) {
    		return expr;
    	}
    	if (negIndex == 0) {
    		return "!" + convertNeg(expr.substring(1));
    	}
    	if (expr.charAt(negIndex - 1) == '(' || expr.charAt(negIndex - 1) == ')' || expr.charAt(negIndex - 1) == '[' || expr.charAt(negIndex - 1) == ']' || 
    			expr.charAt(negIndex - 1) == '+' || expr.charAt(negIndex - 1) == '*' || expr.charAt(negIndex - 1) == '-' || expr.charAt(negIndex - 1) == '/') {
    		return expr.substring(0,negIndex) + '!' + convertNeg(expr.substring(negIndex + 1));
    	}else {
    		return expr.substring(0,negIndex+1) + convertNeg(expr.substring(negIndex+1));
    	}
    }
   
    
    
    
    public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	expr = expr.replace(" ", "");
    	
    	System.out.println("PreNeg:   " + expr);
    	expr = convertNeg(expr);
    	System.out.println("PostNeg:   " + expr);
    	
    	while(expr.indexOf("(") >= 0 || expr.indexOf("[") >= 0) {
    		int openIndex;
        	int closedIndex;
        	boolean paren = true;
	    	if (expr.lastIndexOf("(") > expr.lastIndexOf("[")) {
	    		openIndex = expr.lastIndexOf("(");
	    		closedIndex = expr.indexOf(")", openIndex);
	    	}else {
	    		openIndex = expr.lastIndexOf("[");
	    		closedIndex = expr.indexOf("]", openIndex);
	    		paren = false;
	    	}
		    if (paren) {
		    	System.out.println("PreSimp:   " + expr);
		    	if(closedIndex == expr.length()-1) {
		    		expr = expr.substring(0,openIndex) + solveSimp(expr.substring(openIndex + 1, closedIndex),vars);
		    	}else {
		    		expr = expr.substring(0,openIndex) + solveSimp(expr.substring(openIndex + 1, closedIndex),vars) + expr.substring(closedIndex + 1);
		    	}
		    	System.out.println("PostSimp:   " + expr);
		    } else {	
		    	System.out.println("PreSimp:   " + expr);
		    	expr = expr.substring(0,openIndex+1) + solveSimp(expr.substring(openIndex + 1, closedIndex),vars) + expr.substring(closedIndex);
		    	System.out.println("PostSimp:   " + expr);
		    	openIndex = expr.lastIndexOf("[");
	    		closedIndex = expr.indexOf("]", openIndex);
		    	int startArr = openIndex - 1;
		    	while(startArr > 0) {
//	    			System.out.println("startArr: " + expr.charAt(startArr));
//	    			System.out.println("startArr-1: " + expr.charAt(startArr-1));
	    			if((expr.charAt(startArr - 1) != '*' && expr.charAt(startArr - 1) != '+' && expr.charAt(startArr - 1) != '/' && expr.charAt(startArr - 1) != '-' 
	    					&& expr.charAt(startArr - 1) != '(' && expr.charAt(startArr - 1) != ')' && expr.charAt(startArr - 1) != '[' && expr.charAt(startArr - 1) != ']')) {
	    				startArr --;
	    			} else {
	    				break;
	    			}	
	    		}
//		    	System.out.println("Array Name:  " + expr.substring(startArr, openIndex));
		    	int[] x = new int[0];
		    	for(int i = 0; i < arrays.size(); i++) {
    				if(arrays.get(i).name.equals(expr.substring(startArr,openIndex))) {
    					x = arrays.get(i).values;
    					break;
    				}
    			}
		    	String temp = expr.substring(openIndex + 1, closedIndex);
		    	
		    	System.out.println("y:    " + temp);
		    	int y = (int) Float.parseFloat(temp);
		    	if(closedIndex == expr.length()-1) {
		    		expr = expr.substring(0,startArr) + Float.toString(x[y]);
		    	}else {
		    		expr = expr.substring(0,startArr) + Float.toString(x[y]) + expr.substring(closedIndex + 1);
		    	}
		    }
    	}
    	
	    
    	
    	
    	expr = solveSimp(expr, vars);
    	
    	return Float.parseFloat(expr);
    }
}
//(varx + vary*arrayZ[(varx+arrayA[33/(varx+vary)])])/5

