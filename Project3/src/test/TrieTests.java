package test;

import static org.junit.Assert.*;

import org.junit.Test;

import trie.Trie;

public class TrieTests {
	
	@Test
	public void ctor_sanityCheck(){
		Trie sut = new Trie();
		assertTrue(true);
	}
	
	@Test
	public void insert_sanityCheck(){
		Trie sut = new Trie();
		sut.insert("test");
		assertEquals("t e s t ", sut.toString());
	}
	
	@Test
	public void insert_twoWords(){
		//Arrange
		Trie sut = new Trie();
		
		//Act
		sut.insert("test");
		sut.insert("testicle");
		
		//Assert
		assertEquals("t e s t i c l e ", sut.toString());
	}
	
	@Test
	public void insert_twoWordsNoPrefix(){
		//Arrange
		Trie sut = new Trie();
		
		//Act
		sut.insert("test");
		sut.insert("cat");
		
		//Assert
		assertEquals("c a t t e s t ", sut.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void insert_nullStringThrowsException(){
		//Arrange
		Trie sut = new Trie();
		
		//Act
		sut.insert(null);
		
		//Assert
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void insert_emptyStringThrowsException(){
		//Arrange
		Trie sut = new Trie();
		
		//Act
		sut.insert("");
		
		//Assert
	}
	
	@Test
	public void find_singleWordFullWord(){
		//Arrange
		Trie sut = new Trie();
		sut.insert("cat");
		
		//Act
		boolean result = sut.find("cat", true);
		
		//Assert
		assertTrue(result);
	}
	
	@Test
	public void find_singleWordPrefix(){
		//Arrange
		Trie sut = new Trie();
		sut.insert("catfart");
		
		//Act
		boolean result = sut.find("cat", false);
		
		//Assert
		assertTrue(result);
	}
	
	@Test
	public void find_PrefixWhenFullWordRequired(){
		//Arrange
		Trie sut = new Trie();
		sut.insert("catfart");
		
		//Act
		boolean result = sut.find("cat", true);
		
		//Assert
		assertFalse(result);
	}
	
	@Test
	public void testsFromSpec(){
		Trie sut = new Trie();
		sut.insert("hello");
		sut.insert("hey");
		sut.insert("goodbye");
		assertTrue(sut.find("hell", false));
		assertTrue(sut.find("hello", true));
		assertTrue(sut.find("good", false));
		assertFalse(sut.find("bye", false));
		assertFalse(sut.find("heyy", false));
		assertFalse(sut.find("hell", true));
	}
	
}
