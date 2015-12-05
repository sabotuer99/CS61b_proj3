package test;

import static org.junit.Assert.*;

import org.junit.Test;

import alphabetsort.AlphabetSort;

public class AlphabetSortTests {

	@Test
	public void ctor_sanityCheck(){
		AlphabetSort sut = new AlphabetSort("abcdefg");
		assertTrue(true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void ctor_nullStringThrowsException(){
		AlphabetSort sut = new AlphabetSort(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void ctor_emptyStringThrowsException(){
		AlphabetSort sut = new AlphabetSort("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void ctor_dupCharactersInStringThrowsException(){
		AlphabetSort sut = new AlphabetSort("aaabbbccc");
	}
	
	@Test
	public void insert_wordHasCharsNotInAlphabet_ignored(){
		AlphabetSort sut = new AlphabetSort("abcd");
		sut.insert("cat");
		assertEquals("", sut.toString());
	}
	
	@Test
	public void insert_oneWord(){
		//Arrange
		AlphabetSort sut = new AlphabetSort("agdbecfhijklmnopqrsty");
		
		//Act
		sut.insert("goodbye");
		sut.insert("goodday");		
		
		//Assert
		assertEquals("g o o d d a y b y e ", sut.toString());	
	}
	
	@Test
	public void printall_Spec(){
		//Arrange
		AlphabetSort sut = new AlphabetSort("agdbecfhijklmnopqrsty");
		sut.insert("hello");
		sut.insert("goodbye");
		sut.insert("goodday");	
		sut.insert("death");	
		
		//Act
		String result = sut.printAll();
			
		//Assert
		assertEquals("goodday\ngoodbye\ndeath\nhello", result);	
	}
	
	@Test
	public void printall_AnotherTest(){
		//Arrange
		AlphabetSort sut = new AlphabetSort("zyxwvutsrqponmlkjihgfedcba");
		sut.insert("anteater");
		sut.insert("antspray");
		sut.insert("zoolander");	
		sut.insert("biological");	
		sut.insert("biomedical");	
		sut.insert("cat");	
		sut.insert("dog");	
		
		//Act
		String result = sut.printAll();
			
		//Assert
		assertEquals("zoolander\ndog\ncat\nbiomedical\nbiological\nantspray\nanteater", result);	
	}
}
