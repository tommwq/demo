package com.tq.applogmanagement;

import java.util.UUID;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import com.tq.applogmanagement.agent.LogAgent;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.lang.ref.WeakReference;
import com.tq.applogmanagement.agent.LogAgent;
import com.tq.applogmanagement.Logger;
import com.tq.applogmanagement.*;

import android.os.Environment;
import java.io.File;

public class MyTask extends AsyncTask<Void,Void,Void> {
  private final WeakReference<Activity> activity;
  private static final Logger logger = SimpleLogger.instance();

  public MyTask(Activity aActivity) {
    activity = new WeakReference<Activity>(aActivity);
  }

  @Override
  protected Void doInBackground(Void... ignored) {
    try {
      String host = "172.24.20.112";
      host = "192.168.1.103";
      int port = 50051;

      DeviceAndAppConfig info = new DeviceAndAppConfig()
        .setAppVersion("0.1.0")
        .setModuleVersion("client", "0.1.0")
        .setDeviceId(UUID.randomUUID().toString());

      String fileName = new File(Environment.getExternalStorageDirectory(), "a.blk").getAbsolutePath();
      Log.e("AndroidRuntime", fileName);
      
      StorageConfig config = new StorageConfig()
        .setFileName(fileName)
        .setBlockSize(4096)
        .setBlockCount(8);
    
      logger.open(config, info);

      LogAgent agent = new LogAgent(host, 50051);
      agent.start();

      // record user defined message
      logger.log("hello", info, config);

      // record method and variables
      logger.trace();
      int x = 1;
      logger.print(x);
    
      // record exception
      Throwable error = new Throwable("Test");
      logger.error(error);

      // record user defined message
      logger.log("bye");

      agent.shutdown();
      logger.close();

    } catch (Exception e) {
      Log.e("AndroidRuntime", e.getMessage(), e);
    }
    return null;
  }

  @Override
  protected void onPostExecute(Void ignored) {
  }
}


