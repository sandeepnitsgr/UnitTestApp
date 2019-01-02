package testapp.baghira.app.testapp.mvp.model;


import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Model {

    private final RetrofitApi api;
    private final Scheduler subscribeOn;
    private final Scheduler observeOn;

    public Model() {
        api = new MockRetrofitApi();
        subscribeOn = Schedulers.io();
        observeOn = AndroidSchedulers.mainThread();
    }

    // non public constructor for Unit testing :
    public Model(RetrofitApi api, Scheduler testScheduler) {
        this.api = api;
        this.subscribeOn = testScheduler;
        this.observeOn = testScheduler;
    }


    public Observable<String> requestApi(String text) {
        return api.requestNumberValidation(text)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
    }
}