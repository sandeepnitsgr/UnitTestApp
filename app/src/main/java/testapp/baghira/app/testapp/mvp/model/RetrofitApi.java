package testapp.baghira.app.testapp.mvp.model;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitApi {

    @GET
    Observable<String> requestNumberValidation(@Query("text") String text);

}