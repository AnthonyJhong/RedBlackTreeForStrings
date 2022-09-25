package cs146S19.jhong.project4;

/**
 * Starter Code by Professor Potika
 * This will imitate methods of a RBT for strings
 * @author antho
 *
 * @param <Key>
 */

public class RedBlackTree<Key extends Comparable<Key>> {	
	
		private static Node<String> root;

		public static class Node<Key extends Comparable<Key>> { //changed to static 
			
			  Key key;  	//the value that the node holds	  
			  Node<String> parent; //parent of the node
			  Node<String> leftChild; //leftChild of node
			  Node<String> rightChild; //rightChild of node
			  boolean isRed; //lets us know if the color is red
			  int color; //1 = black //// 0 = red
			  
			  public Node(Key data){
				  this.key = data;
				  leftChild = null;
				  rightChild = null;
				  parent = null;
				  color = 0;
				  isRed = true;
			  }	
			  
			  /**
			   *The result is a negative integer if this String object lexicographically precedes the argument 
			   *string. The result is a positive integer if this String object lexicographically follows the argument string.
			   *The result is zero if the strings are equal; compareTo returns 0 exactly when the equals(Object) method would return true.
			   */
			  public int compareTo(Node<Key> n){ 	//this < that  <0
			 		return key.compareTo(n.key);  	//this > that  >0
			  }
			  public String getKey() {
				  return (String)key;
			  }
			  public boolean isLeaf(){
				  if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
				  if (this.equals(root)) return false;
				  if (this.leftChild == null && this.rightChild == null){
					  return true;
				  }
				  return false;
			  }
		}
		
		public RedBlackTree() {
			root = null;
		}

		 public boolean isLeaf(Node<String> n){
			  if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
			  if (n.equals(root)) return false;
			  if (n.leftChild == null && n.rightChild == null){
				  return true;
			  }
			  return false;
		  }
		
		public interface Visitor<Key extends Comparable<Key>> {
			/**
			This method is called at each node.
			@param n the visited node
			*/
			void visit(Node<Key> n);  
		}
		
		public void visit(Node<Key> n){
			System.out.println(n.key);
		}
		
		public void printTree(){  //preorder: visit, go left, go right
			Node<String> currentNode = root;
			printTree(currentNode);
		}
		
		public void printTree(Node<String> node){
			System.out.print(node.key);
			if (node.isLeaf())
				return;
			if(node.leftChild == null) {
				printTree(node.rightChild);
				return;
			}
			if(node.rightChild == null) {
				printTree(node.leftChild);
				return;
			}
			
			printTree(node.leftChild);
			printTree(node.rightChild);
		}
		
		// place a new node in the RB tree with data the parameter and color it red. 
		public void addNode(String data){  	//this < that  <0.  this > that  >0
		 Node<String> adding = new Node<String>(data); //the node that will be added to the tree
			if(root == null) { //if root doesnt exist
				root = adding; 
				root.color = 1;
				root.isRed = false;
				return;
			}
			
			Node<String> currentViewing = root; //the node that we are currently viewing
			
			while(currentViewing.isLeaf() == false) {
				if(adding.compareTo(currentViewing) < 0) { //if val of adding is lesss that current
					if(currentViewing.leftChild == null) {//break if left child is null
						break;
					}
					currentViewing = currentViewing.leftChild; //move current to leftChild
				}
				
				else if(adding.compareTo(currentViewing) > 0) {
					if(currentViewing.rightChild == null) {//break if right child is null
						break;
					}
					currentViewing = currentViewing.rightChild; //move current to rightChild
				}
			}
			
			if(adding.compareTo(currentViewing) < 0) { //if adding vas less than current 
				currentViewing.leftChild = adding; // adding is the leftChild of current
				adding.parent = currentViewing;
			}
			else {
				currentViewing.rightChild = adding; // adding is the rightChild of current
				adding.parent = currentViewing;
			}
				
			fixTree(adding); //fix the tree for each added node
		}	
		
		//calls the addNode method
		public void insert(String data){
			addNode(data);	
		}
		
		/*
		 * Looks to find if the string is a key for any of the nodes in the RBT
		 */
		public Node<String> lookup(String k){
			Node<String> currentViewing = root; //the current node we are looking at
			
			while(currentViewing!=null) {
				
				if(currentViewing.key.compareTo(k) == 0) 
					return currentViewing;
				
				if(k.compareTo(currentViewing.key) < 0) 
					currentViewing = currentViewing.leftChild; // if the key is smaller than current go left
				
				else if(k.compareTo(currentViewing.key) > 0)
					currentViewing = currentViewing.rightChild; //if key is larger than current go right
			}
			return null; 
		}
	 	
		
		public Node<String> getSibling(Node<String> n){
			if(n.equals(root))
				return null; //the root does not have any siblings
			
			else {
				Node<String> theParent = n.parent;
				if(n.compareTo(theParent) < 0)
					return theParent.rightChild;
				else 
					return theParent.leftChild;
			}
		}
		
		public Node<String> getAunt(Node<String> n){
			
			Node<String> theParent = n.parent; //parent of the current node
			
			if(n.equals(root) || theParent.equals(root))
				return null; //root has no aunt
			
			else {
				return getSibling(theParent); //gets the siblings of the parent
			}
		}
		
		//returns the grandparent of the ndoe
		public Node<String> getGrandparent(Node<String> n){
			return n.parent.parent;
		}
		
		/*
		 * rotates teh specified node to the left
		 */
		public void rotateLeft(Node<String> n){
			Node<String> currentRightChild = n.rightChild; //the right child of the node
			n.rightChild = (currentRightChild.leftChild);
			
			if(currentRightChild.leftChild != null)
				currentRightChild.leftChild.parent = n;
			
			currentRightChild.parent = n.parent;
			
			if(n.parent == null)
				root = currentRightChild;
			else if(n.parent.leftChild==n) 
				n.parent.leftChild = (currentRightChild);
			else
				n.parent.rightChild = (currentRightChild);
			
			currentRightChild.leftChild = n;
			n.parent = currentRightChild;
			
		}
		
		public void rotateRight(Node<String> n){
			
			Node<String> currentLeftChild = n.leftChild;
			n.leftChild = (currentLeftChild.rightChild);
			
			if(currentLeftChild.rightChild != null)
				currentLeftChild.rightChild.parent = (n);
			
			currentLeftChild.parent = (n.parent);
			
			if(n.parent == null) 
				root = currentLeftChild;
			else if(n.parent.rightChild == n)  
				n.parent.rightChild = (currentLeftChild);
			else 
				n.parent.leftChild = (currentLeftChild);
			
			currentLeftChild.rightChild = (n);
			n.parent = (currentLeftChild);
			
		}
		
		public void fixTree(Node<String> current) {
			
			if(current.equals(root)) {
				current.isRed = false;
				current.color = 1;
				return;
			}

			if(!current.parent.isRed) {
				return;
			}
			else {
				if(getAunt(current) == null || !getAunt(current).isRed) {
					if(getGrandparent(current).leftChild == current.parent && current.parent.rightChild == current) {  
						rotateLeft(current.parent);
						fixTree(current.leftChild);
					}
					else if(getGrandparent(current).rightChild == current.parent && current.parent.leftChild == current) {
						rotateRight(current.parent);
						fixTree(current.rightChild);
					}
					else if(getGrandparent(current).leftChild==current.parent && current.parent.leftChild == current) {
						getGrandparent(current).color = 0;
						getGrandparent(current).isRed = true;
						current.parent.isRed = false;
						current.parent.color = 1;
						rotateRight(getGrandparent(current));
					}
					else if(getGrandparent(current).rightChild==(current.parent) && current.parent.rightChild == (current)) {
						getGrandparent(current).color = 0;
						getGrandparent(current).isRed = true;
						current.parent.isRed = false;
						current.parent.color = 1;
						rotateLeft(getGrandparent(current));
					}
				}
				else if(getAunt(current).isRed) {
					current.parent.color = 1;
					current.parent.isRed = false;
					getAunt(current).color = 1;
					getAunt(current).isRed = false;
					getGrandparent(current).isRed = true;
					getGrandparent(current).color = 0;
					fixTree(getGrandparent(current));
				}
			}
		}
		public void preOrderVisit(Visitor<String> v) {
		   	preOrderVisit(root, v);
		}
		 
		 
		private static void preOrderVisit(Node<String> n, Visitor<String> v) {
		  	if (n == null) 
		  		return;
		  	
		  	v.visit(n);
		  	preOrderVisit(n.leftChild, v);
		  	preOrderVisit(n.rightChild, v);
		}	
	
}
