package testapp.baghira.app.testapp;

import android.content.Context;
import android.content.Intent;

import testapp.baghira.app.testapp.mvp.view.MainActivity;

public class Util {

    public int param;
    public Util(){
        this.param = 1;
        System.out.println("constructor");
    }

    public int getParam() {
        return param;
    }

    public static Intent createQuery(Context context, String query, String value) {
        // Reuse MainActivity for simplification
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("QUERY", query);
        i.putExtra("VALUE", value);
        return i;
    }

    @Override
    public String toString() {
        return "sandeep";
    }

//    @Override
//    public boolean equals(Object obj) {
//        return true;
//    }
}