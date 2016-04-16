package com.zanexess.track02;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

public class ProcessDataObjects extends GetJsonData {
    Activity activity;
    public ProcessDataObjects(Activity activity) {
        this.activity = activity;
    }

    List<TechnologyData.Technology> list;
    @Override
    public void execute() {
        ProcessData processData = new ProcessData();
        processData.execute();
    }

    public class ProcessData extends GetJsonData.DownloadJsonData {
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            list = getMObjects();
            TechnologyData.createInstance(list);

            Intent intent = new Intent(activity, MainActivity.class);
            activity.finish();
            activity.startActivity(intent);
        }
    }
}
