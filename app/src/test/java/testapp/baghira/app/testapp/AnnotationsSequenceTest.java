package testapp.baghira.app.testapp;

import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class AnnotationsSequenceTest {
    private ArrayList testList;
    String s = new String("sandeep");
    String str = "sandeep";

    @BeforeClass
    public static void excecuteOnlyOnceAtStart() {
        System.out.println("@BeforeClass: excecuteOnlyOnceAtStart");
    }

    @Before
    public void executedForEachTestAtStart() {
        testList = new ArrayList();
        System.out.println("@Before: executedForEachTest");
    }

    @Test
    public void failTest(){
        if(testList == null){
            fail("testlist is  null");
        }
    }
    @Test
    public void nullTest(){
        //assertNull(testList);
        assertNotNull(testList);
        System.out.println("@Test: NullTest");
    }

    @Test
    public void sameTest(){
        //assertSame(s, str);
        assertNotSame(s, str);
        System.out.println("@Test: SameTest");
    }

    @Test
    public void emptyTest() {
        //assertTrue("not an empty list",!testList.isEmpty());
        assertTrue(testList.isEmpty());
       // assertFalse(!testList.isEmpty());
        System.out.println("@Test: EmptyTest");

    }

    @Test
    public void oneItemTest() {
        testList.add("oneItem");
        Util util = new Util();
        assertEquals(new Util(), util);
        System.out.println("@Test: OneItemTest");
    }

    @Test(expected = Exception.class)
    public void testIndexOutOfBoundsException() {
        List<String> emptyList= null;// = new ArrayList();
        //emptyList.add("sandeep");
        emptyList.get(0);
        System.out.println("@Test(expected = IndexOutOfBoundsException.class): testIndexOutOfBoundsException");
    }

    @Test(timeout = 200)
    public void testTimeout(){
        for(long i = 0; i < 1000000000; i++);
        System.out.println("@Test(timeout = 10): testTimeout");
    }
    @After
    public void executedForEachTestInEnd(){
        System.out.println("@After: executedForEachTestInEnd");
    }

    @AfterClass
    public static void excecuteOnlyOnceInEnd(){
        System.out.println("@AfterClass: excecuteOnlyOnceInEnd");
    }
}
