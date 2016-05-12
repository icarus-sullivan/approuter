package icarus.io.router.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import icarus.io.router.router.AppRouter;

/**
 * Created by chrissullivan on 5/10/16.
 */
public class RoutableActivity extends AppCompatActivity {

    public enum Meta {
        FRAGMENT,
        WEB,
        DEFAULT
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // get name from route, or use class name
        if( getIntent() != null && getIntent().getStringExtra( AppRouter.TITLE ) != null ) {
            setTitle( getIntent().getStringExtra( AppRouter.TITLE ) );
        } else {
            setTitle(getClass().getSimpleName());
        }
        super.onCreate(savedInstanceState);
    }

    public String[] getExtras() {
        return getIntent().getStringArrayExtra( AppRouter.EXTRAS );
    }

    public String getRouterData( String name ) {
        return getIntent().getStringExtra( name );
    }

    public Object getMetaRoute( Meta meta ) {
        String metaRoute = getRouterData( AppRouter.META_ROUTE );
        switch( meta ) {
            case FRAGMENT:
                if( metaRoute != null ) {
                    try {
                        Class<?> c = Class.forName( metaRoute );
                        Fragment extraFragment = (Fragment) c.newInstance();
                        Bundle bundle = new Bundle();
                        bundle.putStringArray( AppRouter.EXTRAS, getExtras() );
                        extraFragment.setArguments( bundle );
                        return extraFragment;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        return metaRoute;
    }

}