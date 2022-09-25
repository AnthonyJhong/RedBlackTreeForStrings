package cs146S19.jhong.project4;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

/**
 * Tester for the RedBlackTree and RBTDictionary classes
 * @author anthonyjhong
 */

public class RBTTester {
	RedBlackTree<String> rbt;
	
	public void setUp() {
		rbt = new RedBlackTree<String>();
	}
	
	/*
	 * This will create a RBTDictionary and will print the words that are
	 * found in a poem that are also in the dictionary
	 */
	@Test
	public void spellCheckTest() throws IOException {
		setUp();
		System.out.println("For this test the program will read in Poem.txt \n"
				+ "and print each of the words that are found in the dictionary \n" 
				+"following the line convension of the poem! \n" + 
				"(Blank Lines ginify that no words in that line were found)\n");
		RBTDictionary dictionary = new RBTDictionary(rbt); //initialize the dictionary
		dictionary.readWordsFromURL();
		System.out.println();
		dictionary.readFileToSpellCheck("Poem.txt");
	}
	
	/*
	 * Test that I created check my answer using the Red Black Tree Visualizer 
	 */
	@Test
	public void myTest() {
		setUp();
		rbt.insert("k");
		rbt.insert("w");
		rbt.insert("q");
		rbt.insert("r");
		rbt.insert("c");
		rbt.insert("a");
		rbt.insert("l");
		rbt.insert("n");
		rbt.insert("m");
		rbt.insert("y");
		rbt.insert("e");
		rbt.insert("v");
		assertEquals("lcakeqnmwrvy", makeString(rbt));
		
		rbt.insert("b");
		rbt.insert("x");
		rbt.insert("f");
		rbt.insert("o");
		rbt.insert("s");
		assertEquals("lcabfekqnmowsrvyx", makeString(rbt));
	}
	
	@Test
	public void myTest2() {
		setUp();
		rbt.insert("l");
		rbt.insert("q");
		rbt.insert("p");
		rbt.insert("a");
		rbt.insert("d");
		rbt.insert("r");
		rbt.insert("g");
		rbt.insert("m");
		rbt.insert("z");
		rbt.insert("y");
		rbt.insert("b");
		rbt.insert("n");
		assertEquals("pdablgmnrqzy", makeString(rbt));
		String str = "Color: 1, Key:p Parent: \n"+
					 "Color: 1, Key:d Parent: p\n"+
					 "Color: 1, Key:a Parent: d\n"+
					 "Color: 0, Key:b Parent: a\n"+
					 "Color: 0, Key:l Parent: d\n"+
					 "Color: 1, Key:g Parent: l\n"+
					 "Color: 1, Key:m Parent: l\n"+
					 "Color: 0, Key:n Parent: m\n"+
					 "Color: 1, Key:r Parent: p\n"+
					 "Color: 1, Key:q Parent: r\n"+
					 "Color: 1, Key:z Parent: r\n"+
					 "Color: 0, Key:y Parent: z\n";
		assertEquals(str, makeStringDetails(rbt));
	}

	@Test
    //Test the Red Black Tree
	public void test() {
		setUp();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
    }
    
    //add tester for spell checker
    
    public static String makeString(RedBlackTree<String> t)
    {
       class MyVisitor implements RedBlackTree.Visitor<String> {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    public static String makeStringDetails(RedBlackTree<String> t) {
    	{
    	       class MyVisitor implements RedBlackTree.Visitor<String> {
    	          String result = "";
    	          public void visit(RedBlackTree.Node n)
    	          {
    	        	  if(!(n.key).equals("") && n.parent!=null)
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+ n.parent.key +"\n";
    	        	  else if(!(n.key).equals("") && n.parent==null)
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: " +"\n";
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       return v.result;
    	 }
    }
 }
  
