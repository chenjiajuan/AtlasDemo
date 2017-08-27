package tvtaobao.com.atlasdome;

import android.content.Context;
import android.taobao.atlas.runtime.AtlasPreLauncher;
import android.util.Log;

/**
 * Created by chenjiajuan on 17/8/26.
 */

public class AtlasdemoPreLaunch implements AtlasPreLauncher {
    @Override
    public void initBeforeAtlas(Context context) {
        Log.e("prelaunch", "prelaunch invokded");

    }
}
