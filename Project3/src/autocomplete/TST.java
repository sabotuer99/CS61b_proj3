package autocomplete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TST {

	private final char EMPTY = 9999; 
	private Node root;
	
	public TST(){
		root = new Node();
	}
	
	public void insert(String s, double w){
		insert(s, w, root, s);
	}
	
	private void insert(String s, double w, Node n, String word){
		//get first character
		char first = s.charAt(0);
		
		//if node val is empty, put first char there 
		if(n.val == EMPTY){
			n.val = first;
		}
		
		//if maxSubWeight < weight, make max = weight
		if(n.maxSubWeight < w){
			n.maxSubWeight = w;
		}		

		if(first == n.val){
			if(s.length() > 1){
				//if node val matches first character and s.length > 1, 
				//insert tail of s in middle
				if(n.middle == null)
					n.middle = new Node();
				insert(s.substring(1), w, n.middle, word);
			} else {
				//if s is length==1, this is the last character
				//if val = first  and weight is > 0, then this word has been inserted
				//already, throw exception
				//otherwise, set val = char and weight = weight
				if(n.weight > 0)
					throw new IllegalArgumentException("Cannot insert duplicate string");
				n.weight = w;
				n.word = word;
			}
		} else if(first < n.val){
			//if node val is greater than first character, 
			//get left node
			//if left is null, make it a new node
			//insert whole string into left
			if(n.left == null)
				n.left = new Node();
			insert(s, w, n.left, word);			
		} else {
			//otherwise do the whole process for right
			if(n.right == null)
				n.right = new Node();
			insert(s, w, n.right, word);
		}
	}
	
	public double find(String s){
		Node n = findNode(s, root);
		if(n == null)
			return 0;
		return n.weight;
	}

	private Node findNode(String s, Node n){
		//get first character
		char first = s.charAt(0);
		
		if(first == n.val){
			if(s.length() > 1){
				//if node val matches first character and s.length > 1, 
				//keep looking down middle
				return findNode(s.substring(1), n.middle);
			} else {
				//if s is length==1, this is the last character
				//return the weight
				return n;
			}
		} else if(first < n.val){
			//if node val is greater than first character, 
			//get left node
			//if left is null, string is not in trie, return 0
			//otherwise, look in left for whole string
			if(n.left == null)
				return null;
			return findNode(s, n.left);			
		} else {
			//otherwise do the whole process for right
			if(n.right == null)
				return null;
			return findNode(s, n.right);
		}
	}
	
	public List<String> findMatches(String prefix, int k){
		
		if(k <= 0)
			throw new IllegalArgumentException("Cannot find non-positive number of matches");
		
		
		PriorityQueue<Node> bestAnswer = new PriorityQueue<Node>(10, new MinNodeComparator());
		PriorityQueue<Node> pq = new PriorityQueue<Node>(10, new MaxNodeComparator());
		List<String> answers = new ArrayList<String>();
		
		//find prefix root node
		Node x = findNode(prefix, root);
		
		if(x == null)
			return null;
		
		//if middle is null, this is the only match, just return it
		if(x.middle == null){
			if(x.word == null)
				return null;
			else{
				answers.add(x.word);
				return answers;
			}
		}
		
		double bestWeight = x.middle.maxSubWeight;
		boolean bestFound = false;
		pq.add(x.middle);
		
		while(pq.size() > 0){
			Node current = pq.poll();
			
			if(current.weight > 0){				
				bestAnswer.add(current);
				
				//only keep the k best
				while(bestAnswer.size() > k)
					bestAnswer.poll();
			}
				
			
			if(current.weight == bestWeight)
				bestFound = true;
			
			enqueueChildren(current, pq);
			
			//check if we can end search early...
			if(bestFound 
					&& bestAnswer.size() >= k 
					&& pq.size() > 0 
					&& bestAnswer.peek().weight > pq.peek().maxSubWeight){
				//System.out.println("Completed search early!");
				break;
			}
				
		}
		
		Node[] nodes = new Node[bestAnswer.size()];
		for(int i = 0; i < nodes.length; i++)
			nodes[i] = bestAnswer.poll();	
		
		for(int i = 0; i < nodes.length && i < k; i++){
			int index = nodes.length - 1 - i;
			answers.add(nodes[index].word);
		}
		
		return answers;
	}
	
	private void enqueueChildren(Node x, PriorityQueue<Node> pq) {
		if(x.left != null)
			pq.add(x.left);
		if(x.middle != null)
			pq.add(x.middle);
		if(x.right != null)
			pq.add(x.right);
	}

	public class MaxNodeComparator implements Comparator<Node>{
        public int compare(Node e1, Node e2)
        {
            return (int)(e2.maxSubWeight - e1.maxSubWeight);
        }
    }
	
	public class MinNodeComparator implements Comparator<Node>{
        public int compare(Node e1, Node e2)
        {
            return (int)(e1.weight - e2.weight);
        }
    }
	
	public class Node{
		public Node right;
		public Node left;
		public Node middle;
		public char val = EMPTY;
		public double weight;
		public double maxSubWeight;	
		public String word;
		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append(val + " ");
			if(middle != null)
				sb.append(middle.toString());
			if(left != null)
				sb.append(left.toString());
			if(right != null)
				sb.append(right.toString());
			return sb.toString();
		}
	}
	
	@Override
	public String toString(){
		return root.toString().trim();
	}
	
}
