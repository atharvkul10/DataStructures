package structures;

import java.util.*;


//Last Modified 3/29 v2

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	
//	private static TagNode buildNode(TagNode root, Scanner sc) {
//		if(sc.hasNext()== false) {
//			return null;
//		}
//		
//		String pntr = sc.next();
//		String temp = "";
//		TagNode pntrNode = root;
//		
//		while(pntr.contains(">") == false) {
//			temp += pntr;
//			pntr = sc.next();
//		}
//		
//		if(root == null) {
//			root = new TagNode(pntr,null,null);
//			pnt
//		}
//		
//	}
	
	
	public void build() {
		
		
		if(sc.hasNext()== false) {
			return;
		}
		
		Stack<TagNode> nodeStack = new Stack();
		root = null;
		String pntr;
		TagNode pntrNode = root;
		boolean isTag = true;
		boolean justClosed = true;
		
		while(sc.hasNext()) {
			pntr = sc.next();
			String type = "normal";
			
			if(pntr.indexOf(">") >= 0) {
				pntr = pntr.replace("<", "");
				pntr = pntr.replace(">", "");
				if(pntr.indexOf("/") >= 0 ) {
					pntr = pntr.replace("/", "");
					type = "closed";
				} else {
					type = "open";
				}
			}
			
			if(root == null) {
				root = new TagNode(pntr, null, null);
				nodeStack.push(root);
				pntrNode = root;
				isTag = true;
				continue;
				
			}
			
			if(pntrNode.firstChild == null && isTag) {
				if(type == "normal") {
					pntrNode.firstChild = new TagNode(pntr, null, null);
					pntrNode = pntrNode.firstChild;
					isTag = false;
					justClosed = false;
				} else if(type == "closed"){
					pntrNode = nodeStack.peek();
					nodeStack.pop();
					justClosed = false;
					continue;
				} else {
					pntrNode.firstChild = new TagNode(pntr, null, null);
					nodeStack.push(pntrNode.firstChild);
					pntrNode = pntrNode.firstChild;
					isTag = true;
					justClosed = false;
				}
				continue;
			}
			
			if(pntrNode.sibling == null) {
				if(type == "normal") {
					if(isTag == false && justClosed == false) {
						pntrNode.tag = pntrNode.tag + " " + pntr;
						isTag = false;
						justClosed = false;
					}else {
						pntrNode.sibling = new TagNode(pntr, null, null);
						pntrNode = pntrNode.sibling;
						isTag = false;
						justClosed = false;
					}
				} else if(type == "closed"){
					pntrNode = nodeStack.peek();
					nodeStack.pop();
					justClosed = true;
					continue;
				} else {
					pntrNode.sibling = new TagNode(pntr, null, null);
					nodeStack.push(pntrNode.sibling);
					pntrNode = pntrNode.sibling;
					isTag = true;
					justClosed = false;
				}
				continue;
			}
		}
	}
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		if(root == null) {
			return;
		}
		TagNode pntr = root;
		Stack<TagNode> stack = new Stack();
		boolean isTag = false;
		do {
			if(pntr == null) {
				pntr = stack.pop();
				if(isTag) {
					pntr = pntr.firstChild;
				}else {
					pntr = pntr.sibling;
				}
				continue;
			}
			if(pntr.tag.equals("html")||pntr.tag.equals("body")||pntr.tag.equals("p")||pntr.tag.equals("em")||pntr.tag.equals("b")||
					pntr.tag.equals("table")||pntr.tag.equals("tr")||pntr.tag.equals("td")||pntr.tag.equals("ol")||
					pntr.tag.equals("ul")|| pntr.tag.equals("li")) {
				isTag = true;
				stack.push(pntr);
			}else {
				isTag = false;
			}
			if(pntr.tag.equals(oldTag)) {
				pntr.tag = newTag;
				pntr = pntr.firstChild;
				continue;
			}
			if(isTag == true) {
				pntr = pntr.firstChild;
			}
			if(isTag == false) {
				pntr = pntr.sibling;
			}
		} while (stack.isEmpty() == false);
		return;
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
		if(root == null) {
			return;
		}
		TagNode pntr = root;
		Stack<TagNode> stack = new Stack();
		boolean isTag = false;
		TagNode pntrTab = null;
		TagNode pntrTab2 = null;
		for(int i = 0; i < row; i++) {
			do {
				if(pntr == null) {
					pntr = stack.pop();
					if(isTag) {
						pntr = pntr.firstChild;
					}else {
						pntr = pntr.sibling;
					}
					continue;
				}
				if(pntr.tag.equals("html")||pntr.tag.equals("body")||pntr.tag.equals("p")||pntr.tag.equals("em")||pntr.tag.equals("b")||
						pntr.tag.equals("table")||pntr.tag.equals("tr")||pntr.tag.equals("td")||pntr.tag.equals("ol")||
						pntr.tag.equals("ul")|| pntr.tag.equals("li")) {
					isTag = true;
					stack.push(pntr);
				}else {
					isTag = false;
				}
				if(pntr.tag.equals("tr")) {
					pntrTab = pntr;
					pntr = pntr.firstChild;
					break;
				}
				if(isTag == true) {
					pntr = pntr.firstChild;
				}
				if(isTag == false) {
					pntr = pntr.sibling;
				}
			} while (stack.isEmpty() == false);
			if(pntrTab.equals(pntrTab2)) {
				return;
			}
			pntrTab2 = pntrTab;
		}
		if(pntrTab == null) {
			return;
		}
		Stack<TagNode> tempStack = new Stack();
		isTag = false;
		pntr = pntrTab;
		do {
			if(pntr == null) {
				pntr = tempStack.pop();
				if(isTag) {
					pntr = pntr.firstChild;
				}else {
					pntr = pntr.sibling;
				}
				continue;
			}
			if(pntr.tag.equals("html")||pntr.tag.equals("body")||pntr.tag.equals("p")||pntr.tag.equals("em")||pntr.tag.equals("b")||
					pntr.tag.equals("table")||pntr.tag.equals("tr")||pntr.tag.equals("td")||pntr.tag.equals("ol")||
					pntr.tag.equals("ul")|| pntr.tag.equals("li")) {
				isTag = true;
				tempStack.push(pntr);
			}else {
				isTag = false;
			}
			if(pntr.tag.equals("td")) {
				TagNode temp = new TagNode("b",pntr.firstChild,pntr.firstChild.sibling);
				pntr.firstChild = temp;
				pntr = pntr.firstChild;
				continue;
			}
			if(isTag == true) {
				pntr = pntr.firstChild;
			}
			if(isTag == false) {
				pntr = pntr.sibling;
			}
		} while (tempStack.isEmpty() == false);
		return;
	}
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/
		if(tag.equals("b")||tag.equals("p")||tag.equals("em")) {
			notList(root,tag);
		}
		if(tag.equals("ol")||tag.equals("ul")) {
			list(root,tag);
		}
	}
	
	private void notList(TagNode root, String tag) {
		if (root == null) {
			return;
		}
		int counter = 0;
		boolean flag = true;
		if(flag) {
			notList(root.firstChild, tag);
		}
		if(root.sibling != null) {
			if(root.sibling.tag.equals(tag)) {
				TagNode pntr = root.sibling.firstChild;
				while(pntr.sibling != null) {
					pntr= pntr.sibling;
				}
				TagNode sib = root.sibling;
				pntr.sibling = sib.sibling; 
				root.sibling= sib.firstChild; 
			}
		}
		if(flag) {
			flag = false;
		}
		if(!flag) {
			counter++;
		}
		if(root.firstChild != null) {
			if(root.firstChild.tag.equals(tag)) {
				TagNode pntr=root.firstChild.firstChild; 
				while (pntr.sibling != null) {
					pntr = pntr.sibling; 
				}
				TagNode chi = root.firstChild;
				pntr.sibling= chi.sibling; 
				root.firstChild= chi.firstChild; 
			}
		}
		if(flag) {
			list(root.sibling, tag); 
		}else {
			counter++;
			list(root.sibling, tag);
		}
	}
	
	
	private void list(TagNode root, String tag) {
		if (root == null) {
			return; 
		}
		int counter = 0;
		boolean flag = true;
		if(flag) {
			list(root.firstChild, tag);
		}
		if(root.sibling != null) {
			if(root.sibling.tag.equals(tag)) {
				TagNode pntr= root.sibling.firstChild; 
				while(pntr.sibling !=null) {
					pntr.tag="p"; 
					pntr= pntr.sibling;
				}
				TagNode sib = root.sibling;
				pntr.tag= "p";
				pntr.sibling = sib.sibling; 
				root.sibling= sib.firstChild;
			} 
			if(flag) {
				flag = false;
			}
			if(!flag) {
				counter++;
			}
		}
		if(root.firstChild !=null) {
			if(root.firstChild.tag.equals(tag)) {
				TagNode pntr=root.firstChild.firstChild; 
				while (pntr.sibling !=null) {
					pntr.tag = "p";
					pntr = pntr.sibling; 
				}
				TagNode chi = root.firstChild;
				pntr.tag= "p";
				pntr.sibling= chi.sibling; 
				root.firstChild= chi.firstChild;
			} 
		}
		if(flag) {
			list(root.sibling, tag); 
		}else {
			counter++;
			list(root.sibling, tag);
		}
	}
	
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
		if(root == null) {
			return;
		}
		root = add(root, word, tag);
		TagNode pntr = root;
		TagNode pntr2 = null;
		Stack<TagNode> stack = new Stack();
		boolean isTag = false;
		do {
			if(pntr == null) {
				if(isTag) {
					pntr2 = pntr;
					pntr = pntr.firstChild;
				}else {
					pntr2 = pntr;
				}
				continue;
			}
			if(pntr.tag.equals("html")||pntr.tag.equals("body")||pntr.tag.equals("p")||pntr.tag.equals("em")||pntr.tag.equals("b")||
					pntr.tag.equals("table")||pntr.tag.equals("tr")||pntr.tag.equals("td")||pntr.tag.equals("ol")||
					pntr.tag.equals("ul")|| pntr.tag.equals("li")) {
				isTag = true;
				stack.push(pntr);
			}else {
				isTag = false;
			}
			if(pntr.tag.contains(word)) {
				TagNode pntr3 = new TagNode(tag,pntr,pntr.sibling);

				if(isTag) {
					pntr3.firstChild = pntr;
				}else {
					pntr3.sibling = pntr;
				}
				pntr3.sibling = null;
				break;
			}
			if(isTag == true) {
				pntr2 = pntr;
				pntr = pntr.firstChild;
			}
			if(isTag == false) {
				pntr2 = pntr;
				pntr = pntr.sibling;
			}
		} while (stack.isEmpty() == false);
		return;
	}
	
	
	private TagNode add(TagNode root, String word, String tag) {	
		if (root.sibling == null) { 
			return null;
		}
		root.sibling = add(root.sibling, word, tag);
		if (root.firstChild == null) {
			return null;
		}
		if (root.firstChild!= null) {
			root.firstChild = add(root.firstChild, word, tag);
		}

	
		if (root.tag.contains(word)) {
			TagNode temp = new TagNode(tag, root, root.sibling);
			root.sibling = null;
			return temp;
		}
	
		return root;
			
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|----");
			} else {
				System.out.print("     ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
