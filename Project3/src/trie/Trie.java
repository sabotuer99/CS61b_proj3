package trie;

/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author 
 */
public class Trie {
	private Node root;
	
	public Trie(){
		root = new Node();
	}
	
    public boolean find(String s, boolean isFullWord) {
    	Node pointer = root;
    	for(int i = 0; i < s.length(); i++){
    		Node next = pointer.links[s.charAt(i)];
    		if(next == null){
    			return false;
    		}
    		pointer = next;
    	}
    	if(isFullWord)
    		return pointer.exists;
    	else 
    		return true;
    }

    public void insert(String s) {
    	Node pointer = root;
    	for(int i = 0; i < s.length(); i++){
    		Node next = pointer.links[s.charAt(i)];
    		if(next == null){
    			pointer.links[s.charAt(i)] = new Node();
    			next = pointer.links[s.charAt(i)];
    		}
    		pointer = next;
    	}
    	pointer.exists = true;
    }
    
    @Override
    public String toString(){
    	return root.toString();
    }

}

class Node{
	
	boolean exists;
	Node[] links;
	
	public Node(){
		links = new Node[128];
		exists = false;
	}
	
    @Override
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	
    	for(int i = 0; i < 128; i++){
    		if(links[i] != null){
    			sb.append((char)i + " " + links[i].toString());
    		}
    	}
    	
    	return sb.toString();
    }
}
