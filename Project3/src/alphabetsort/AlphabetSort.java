package alphabetsort;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author 
 */
public class AlphabetSort {
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		String alphabet = in.next();
		AlphabetSort sort = new AlphabetSort(alphabet);
		while(in.hasNext()){
			sort.insert(in.next(), sort.getRoot());
		}
		System.out.println(sort.printAll());
		
	}
	
	public String printAll(){
		return printAll(this.getRoot(), "", this.getAlphabet(), new StringBuilder());
	}
	
	private String getAlphabet() {
		return alphabet;
	}

	private String printAll(Node node, String str, String alphabet, StringBuilder sb) {
		if(node.exists)
			sb.append(str + "\n");
		
		for(int i = 0; i < alphabet.length(); i++){
			if(node.links[i] != null){
				printAll(node.links[i], str + alphabet.charAt(i), alphabet, sb);
			}
		}
		
		return sb.toString().trim();	
	}

	private String alphabet;
	private Node root;
	private char[] map = new char[128];
	
	public Node getRoot(){
		return root;
	}
	
	public char[] getMap(){
		return map;
	}
	
	
	
	public AlphabetSort(String alphabet){
		
		if(alphabet == null || alphabet.equals(""))
			throw new IllegalArgumentException("Must provide an alphabet");
		
		this.alphabet = alphabet;
		
		char nchar = (char)255;
		Arrays.fill(map, nchar);
		for(int i = 0; i < alphabet.length(); i++){
			if(map[alphabet.charAt(i)] != nchar)
				throw new IllegalArgumentException("Cannot repeat letters in alphabet");
			
			map[alphabet.charAt(i)] = (char)i;
		}
		
		root = new Node();
	}
// FIND not defined for AlphabetSort
//	
//    public boolean find(String s, boolean isFullWord) {
//    	Node pointer = root;
//    	for(int i = 0; i < s.length(); i++){
//    		char index = map[s.charAt(i)];
//    		
//    		if(index == 255)
//    			throw new IllegalArgumentException("Cannot insert word with letters not appearing in alphabet");
//    		
//    		Node next = pointer.links[index];
//    		if(next == null){
//    			return false;
//    		}
//    		pointer = next;
//    	}
//    	if(isFullWord)
//    		return pointer.exists;
//    	else 
//    		return true;
//    }

	public void insert(String s){
		insert(s, root);
	}
	
    private boolean insert(String s, Node node) {
    	
    	if(s == null || s.equals(""))
    		throw new IllegalArgumentException("Cannot insert null or empty string into a trie...");
    	
    	char index = map[s.charAt(0)];
    	if(index == 255)
    		return false;
    	
    	Node next = node.links[index];
    	
		if(next == null)
			next = new Node();

    	if(s.length() == 1){
    		next.exists = true;
    		node.links[index] = next;
    		return true;
    	}
		
		if(insert(s.substring(1), next)){
			node.links[index] = next;
			return true;
		} else {
			return false;
		}
	
    }
    
    @Override
    public String toString(){
    	return root.toString();
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
        	
        	for(int i = 0; i < alphabet.length(); i++){   
        		
        		char index = alphabet.charAt(i);
        		
        		if(links[map[index]] != null){
        			sb.append(index + " " + links[map[index]].toString());
        		}
        	}
        	
        	return sb.toString();
        }
    }
}


