package testapp.baghira.app.testapp.mvp.model;

import rx.Observable;

public class MockRetrofitApi implements RetrofitApi {

    @Override
    public Observable<String> requestNumberValidation(String text) {
        if (!text.isEmpty() && Integer.valueOf(text) % 2 == 0) {
            return Observable.just(text);
        } else {
            return Observable.error(new UnsupportedOperationException());
        }
    }
}