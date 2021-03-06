package mutexthreads.io.examples;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.Serializable;

import icarus.io.router.annotation.Extra;
import icarus.io.router.annotation.Conditions;
import icarus.io.router.annotation.Route;
import icarus.io.router.api.Journey;
import icarus.io.router.intercepts.RouteConditionInterceptor;
import icarus.io.router.intercepts.RouteInterceptor;
import mutexthreads.io.examples.activities.MainActivity;
import mutexthreads.io.examples.activities.WebActivity;

/**
 * Created by chris on 10/21/16.
 */
public class Router {

    public static Instance navigateTo;

    public static final int REQUEST_VIEW = 0x0002;

    public static final String AUTH = "NeedsAuth";
    public static final String FALLBACK = "Fallback";

    public static final String IDS = "ids";

    public static final String githubPage = "https://github.com/icarus-sullivan/Journey";

    public static void create( Context app ) {
        navigateTo = new Journey.Builder( app )
                .addInterceptors(new RouteInterceptor() {
                    @Override
                    public boolean onRoute(Intent intent) {
                        Log.d("====>", "component: " + intent.getComponent());
                        return true;
                    }
                })
                .create( Instance.class );
    }

    public interface Instance {

        @Conditions({AUTH, FALLBACK})
        @Route(Activity = MainActivity.class)
        void MainActivity( RouteInterceptor interceptor );

        @Route(Activity = MainActivity.class)
        void MainActivityWithExtras(@Extra(IDS) int[] ids );

        @Route(Activity = MainActivity.class)
        void MainActivitySerializable(@Extra("Serialize") Serializable sv );

        @Conditions({AUTH})
        @Route( Activity = WebActivity.class, Url = githubPage )
        void GoToGitPage( RouteConditionInterceptor interceptor );

        @Route( Action = Intent.ACTION_GET_CONTENT, RequestCode = REQUEST_VIEW )
        void GetContent(AppCompatActivity callingAct, Uri uri );

        @Route( Action = Intent.ACTION_VIEW )
        void ViewInBrowser(Uri uri );

    }

}
