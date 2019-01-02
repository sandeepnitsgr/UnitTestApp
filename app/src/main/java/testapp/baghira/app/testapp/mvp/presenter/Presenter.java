package testapp.baghira.app.testapp.mvp.presenter;

import java.util.function.Consumer;

import rx.Subscriber;
import rx.Subscription;
import testapp.baghira.app.testapp.mvp.model.Model;

public class Presenter {

    private final View view;
    private final Model model;
    private Subscription subscription;

    public Presenter(View view) {
        this(view, new Model());
    }

    // non public constructor for Unit testing :
    Presenter(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void onButtonClicked(String text) {
        if (subscription == null) {
            subscription = model.requestApi(text)
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            clear();
                        }

                        @Override
                        public void onNext(String s) {
                            onSuccess(s);
                        }
                    });
        }
    }

    private void onSuccess(String evenNumber) {
        //view.updateTextView("4");
        view.updateTextView(evenNumber);
        subscription = null;
    }

    public void clear() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
    }
}
