package tvtaobao.com.atlasdome;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.taobao.atlas.dex.util.FileUtils;
import com.taobao.atlas.update.AtlasUpdater;
import com.taobao.atlas.update.exception.MergeException;
import com.taobao.atlas.update.model.UpdateInfo;

import org.osgi.framework.BundleException;

import java.io.File;

public class MainActivity extends Activity {
    private static  final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void remote(View view){
        Log.e(TAG,"remote");
        Intent intent=new Intent();
        intent.setClassName(view.getContext(),"tvtaobao.com.remotebundle.RemoteActivity");
        startActivity(intent);

    }

    public void local(View view){
        Log.e(TAG,"local");
        Intent intent=new Intent();
        intent.setClassName(view.getContext(),"tvtaobao.com.homebundle.HomeActivity");
        startActivity(intent);

    }

    public void update(View view){
        Log.e(TAG,"update");

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                update();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e(TAG,"更新完成请重启");
            }
        }.execute();

    }

    private void update() {
        File updateInfo=new File(getExternalCacheDir(),"update.json");
        Log.e(TAG,"updateInfo = "+getExternalCacheDir());
        if (!updateInfo.exists()){
            Log.e(TAG,"更新信息不存在，请先执行buildTpatch.sh");
            return;
        }
        String jsonStr=new String(FileUtils.readFile(updateInfo));
        UpdateInfo info= JSON.parseObject(jsonStr,UpdateInfo.class);
        File patchFile= new File(getExternalCacheDir(),"patch-"+info.updateVersion+"@"+info.baseVersion+".tpatch");
        Log.e(TAG,"patchFile = "+patchFile);
        try {
            AtlasUpdater.update(info,patchFile);
        } catch (MergeException e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
        } catch (BundleException e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
        }
    }
}
