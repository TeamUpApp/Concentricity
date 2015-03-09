package teamupapps.com.concentricity;

import android.app.Application;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by nrobatmeily on 9/03/2015.
 */
public class GameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("CODE.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }


}
