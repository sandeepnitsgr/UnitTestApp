package testapp.baghira.app.testapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
 
@RunWith(MockitoJUnitRunner.class)
public class MockSpy {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private List<String> mockList;
 @Mock
 Util util;
    @Spy
    private ArrayList<String> spyList;// = new ArrayList();


    @Before
    public void initMocks(){
        //MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testMockList() {

        //assertEquals(1, util.param);

        mockList.add("test");
        //assertNull(mockList.get(0));   /// returns null here
        assertEquals("test", mockList.get(0));
        //assertNotEquals("test",mockList.get(0));
    }
 
    @Test
    public void testSpyList() {
        //spy object will call the real method when not stub
        spyList.add("test");
        assertEquals("test", spyList.get(0));
    }
 
    @Test
    public void testMockWithStub() {
        String expected = "Mock 100";
        //mockList.add("20");
        //assertEquals("20",mockList.get(0));

        //when(mockList.get(100)).thenReturn(anyString());
        when(mockList.get(anyInt())).thenReturn(expected);
        assertEquals(expected, mockList.get(100));

    }
 
    @Test
    public void testSpyWithStub() {
        String expected = "Spy 100";
//        spyList.add("20");
//        assertEquals("20",spyList.get(0));

        doReturn(expected).when(spyList).get(100);
        assertEquals(expected, spyList.get(100));

    }




    @Test
    public void testLinkedListSpyWrong() {
        List<String> list = new LinkedList<>();
        List<String> spy = spy(list);
        spy.add("foos");
        // this does not work as real method is called so spy.get(0)
        // throws IndexOutOfBoundsException (list is still empty)
        when(spy.get(0)).thenReturn("foo");

        assertEquals("foo", spy.get(0));
    }

    @Test
    public void testLinkedListSpyCorrect() {
        // Lets mock a LinkedList
        List<String> list = new LinkedList<>();
        List<String> spy = spy(list);

        // You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);

        assertEquals("foo", spy.get(0));
    }
}