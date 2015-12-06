package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

import autocomplete.TST;

public class TSTTests {
	
	public static final double EPSILON = 1e-15;
	
	@Test
	public void ctor_sanityCheck(){
		TST sut = new TST();
		assertTrue(true);
	}
	
	@Test
	public void insert_OneShortWord(){
		//Arrange
		TST sut = new TST();
		
		//Act
		sut.insert("cat", 10.0);
		
		//Assert
		assertEquals("c a t", sut.toString());
	}
	
	@Test
	public void insert_TwoShortWordsNoPrefix(){
		//Arrange
		TST sut = new TST();
		
		//Act
		sut.insert("cat", 10.0);
		sut.insert("dog", 20.0);
		
		//Assert
		assertEquals("c a t d o g", sut.toString());
	}
	
	@Test
	public void insert_ThreeShortWordsNoPrefix_leftThenRight(){
		//Arrange
		TST sut = new TST();
		
		//Act
		sut.insert("cat", 10.0);
		sut.insert("dog", 20.0);
		sut.insert("bee", 30.0);
		
		//Assert
		assertEquals("c a t b e e d o g", sut.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void insert_DuplicateStrings_ThrowsException(){
		//Arrange
		TST sut = new TST();
		
		//Act
		sut.insert("cat", 10.0);
		sut.insert("cat", 20.0);
	}
	
	@Test
	public void find_StringInTrie_returnsWeight(){
		//Arrange
		TST sut = new TST();		
		sut.insert("cat", 10.0);
		sut.insert("dog", 20.0);
		sut.insert("bee", 30.0);
		
		//Act
		double result = sut.find("bee");
		
		//Assert
		assertEquals(30.0, result, EPSILON);
	}
	
	@Test
	public void find_StringnOTInTrie_returnsZero(){
		//Arrange
		TST sut = new TST();		
		sut.insert("cat", 10.0);
		sut.insert("dog", 20.0);
		sut.insert("bee", 30.0);
		
		//Act
		double result = sut.find("bug");
		
		//Assert
		assertEquals(0.0, result, EPSILON);
	}
	
	@Test
	public void maxComp_worksCorrectelyWithPriorityQueue(){
		//Arrange
		TST.Node node1 = new TST().new Node();
		TST.Node node2 = new TST().new Node();
		TST.MaxNodeComparator sut = new TST().new MaxNodeComparator();
		PriorityQueue<TST.Node> q = new PriorityQueue<TST.Node>(2, sut);		
		node1.maxSubWeight = 10.0;
		node2.maxSubWeight = 20.0;		
		q.add(node1);
		q.add(node2);	
		
		//ACT
		double result = q.peek().maxSubWeight;
		
		//ASSERT
		assertEquals(20.0, result, EPSILON);
	}
	
	@Test
	public void findMatches_exampleFromSpec(){
		//Arrange
		TST sut = new TST();		
		sut.insert("smog", 5.0);
		sut.insert("buck", 10.0);
		sut.insert("sad", 12.0);
		sut.insert("spite", 20.0);
		sut.insert("spit", 15.0);
		sut.insert("spy", 7.0);
		
		//Act
		List<String> result = sut.findMatches("s", 3);
		
		//Assert
		assertEquals(3, result.size());
		assertEquals("spite", result.get(0));
		assertEquals("spit", result.get(1));
		assertEquals("sad", result.get(2));
	}
	
	@Test
	public void findMatches_FindBestMatch(){
		//Arrange
		TST sut = new TST();		
		sut.insert("smog", 5.0);
		sut.insert("buck", 10.0);
		sut.insert("sad", 12.0);
		sut.insert("spite", 20.0);
		sut.insert("spit", 15.0);
		sut.insert("spy", 7.0);
		
		//Act
		List<String> result = sut.findMatches("s", 1);
		
		//Assert
		assertEquals(1, result.size());
		assertEquals("spite", result.get(0));
	}
	
	@Test
	public void findMatches_FindAllAvailableLargeK(){
		//Arrange
		TST sut = new TST();		
		sut.insert("smog", 5.0);
		sut.insert("buck", 10.0);
		sut.insert("sad", 12.0);
		sut.insert("spite", 20.0);
		sut.insert("spit", 15.0);
		sut.insert("spy", 7.0);
		
		//Act
		List<String> result = sut.findMatches("s", 7);
		
		//Assert
		assertEquals(5, result.size());
		assertEquals("spite", result.get(0));
		assertEquals("spit", result.get(1));
		assertEquals("sad", result.get(2));
		assertEquals("spy", result.get(3));
		assertEquals("smog", result.get(4));
	}
	
	@Test
	public void findMatches_FindBestMatch2(){
		//Arrange
		TST sut = new TST();		
		sut.insert("smog", 5.0);
		sut.insert("buck", 10.0);
		sut.insert("sad", 12.0);
		sut.insert("spite", 20.0);
		sut.insert("spit", 15.0);
		sut.insert("spy", 7.0);
		
		//Act
		List<String> result = sut.findMatches("b", 1);
		
		//Assert
		assertEquals(1, result.size());
		assertEquals("buck", result.get(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void findMatches_FindZero_ThrowsException(){
		//Arrange
		TST sut = new TST();		
		sut.insert("smog", 5.0);
		sut.insert("buck", 10.0);
		sut.insert("sad", 12.0);
		sut.insert("spite", 20.0);
		sut.insert("spit", 15.0);
		sut.insert("spy", 7.0);
		
		//Act
		List<String> result = sut.findMatches("b", 0);
	}
	
}
