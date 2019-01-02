package testapp.baghira.app.testapp;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class WhenThenReturnExample {
    @Test
    public void test1()  {
        //  arrange
        WhenThenReturnExampleClass test = mock(WhenThenReturnExampleClass.class);

        // act
        when(test.getUniqueId()).thenReturn(43);

        // assert
        assertEquals(test.getUniqueId(), 43);
    }


    // this test demonstrates the return of multiple values
    @Test
    public void testMoreThanOneReturnValue()  {
        Iterator<String> i= mock(Iterator.class);
        when(i.next()).thenReturn("Mockito").thenReturn("rocks");
        String result= i.next()+" "+i.next();
        //assert
        assertEquals("Mockito rocks", result);
    }

    // this test demonstrates how to return values based on the input
    @Test
    public void testReturnValueDependentOnMethodParameter()  {
        //arrange
        Comparable<String> c= mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        //act and assert
        assertEquals(1, c.compareTo("Mockito"));
    }

// this test demonstrates how to return values independent of the input value

    @Test
    public void testReturnValueInDependentOnMethodParameter()  {
        Comparable<Integer> c= mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        //assert
        assertEquals(-1, c.compareTo(9));
    }

// return a value based on the type of the provide parameter

    @Test
    public void testReturnValueInDependentOnMethodParameterType()  {
        Comparable<String> c= mock(Comparable.class);
        when(c.compareTo(isA(String.class))).thenReturn(0);
        //assert
        assertEquals(0, c.compareTo("1"));
    }





    @Test
    public void testVerify()  {
        // arrange
        WhenThenReturnExampleClass test = Mockito.spy(WhenThenReturnExampleClass.class);
        //when(test.getUniqueId()).thenReturn(43);


        // act
        // call method testing on the mock with parameter 12
        //test.testing(12);
        test.getUniqueId();
        //test.getUniqueId();


        //assert
        // now check if method testing was called with the parameter 12
        //verify(test).testing(ArgumentMatchers.eq(12));

        //verify(test, times(2)).getUniqueId();

        verify(test, never()).testing(2);

        //verify(test, atLeastOnce()).someMethod("called at least once");

        //verify(test, atLeast(2)).someMethod("called at least twice");

        //verify(test, times(5)).someMethod("called five times");

        //verify(test, atMost(3)).someMethod("called at most 3 times");

        // This let's you check that no other methods where called on this object.

        // You call it after you have verified the expected method calls.

        //verifyNoMoreInteractions(test);
    }
}
