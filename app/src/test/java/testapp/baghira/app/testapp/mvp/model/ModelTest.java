package testapp.baghira.app.testapp.mvp.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rx.Subscriber;
import rx.schedulers.TestScheduler;

public class ModelTest {
    boolean result;
    @Before
    public void setup(){
        result = false;
    }
    @Test
    public void requestApiWithEvenNumberThenReturnTheNumber() {

        // arrange


        TestScheduler testScheduler = new TestScheduler();
        Model model = new Model(new MockRetrofitApi(), testScheduler);

        // act
        model.requestApi("2").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                result = true;
            }
        });

        testScheduler.triggerActions(); // execute all pending RX operations

        // assert
        Assert.assertTrue(result);
    }



    @Test public void requestApiWithOddNumberThenReturnError() {

        // arrange

        result = false;
        TestScheduler testScheduler = new TestScheduler();
        Model model = new Model(new MockRetrofitApi(), testScheduler);

        // act
        model.requestApi("1").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                result = true;
            }

            @Override
            public void onNext(String s) {

            }
        });

        testScheduler.triggerActions(); // execute all pending RX operations

        // assert
        Assert.assertTrue(result);
    }

}