package testapp.baghira.app.testapp.mvp.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rx.schedulers.TestScheduler;
import testapp.baghira.app.testapp.mvp.model.MockRetrofitApi;
import testapp.baghira.app.testapp.mvp.model.Model;

public class PresenterTest {
    int result;

    @Before
    public void setup(){
        result = 0;
    }
    @Test
    public void onButtonClickWithEvenNumberThenUpdateTextViewWithNumber() {

        // arrange

        TestScheduler testScheduler = new TestScheduler();
        Model model = new Model(new MockRetrofitApi(), testScheduler);

        Presenter presenter = new Presenter(new View() {
            public void updateTextView(String text) {
                result = Integer.valueOf(text);
            }
        }, model);

        // act
        presenter.onButtonClicked("2");
        testScheduler.triggerActions();// execute all pending RX operations

        // assert
        Assert.assertEquals(2, result);

    }

    @Test
    public void onButtonClickWithOddNumberThenDoNotUpdateTextView() {

        TestScheduler testScheduler = new TestScheduler();
        Model model = new Model(new MockRetrofitApi(), testScheduler);

        Presenter presenter = new Presenter(new View() {
            public void updateTextView(String text) {
                result = Integer.valueOf(text);
            }
        }, model);

        presenter.onButtonClicked("1");
        testScheduler.triggerActions();

        Assert.assertEquals(0, result);

    }
}