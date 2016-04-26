package com.zanexess.track02;

import android.app.Activity;
import android.content.Intent;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProcessDataObjects extends GetJsonData {
    Activity activity;
    String result;
    public ProcessDataObjects(Activity activity) {
        this.activity = activity;
    }

    List<TechnologyData.Technology> list;
    @Override
    public void execute() {
        ProcessData processData = new ProcessData();
        try {
            result = processData.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void executeFromExistedResult(String result) {
        GetJsonData jsonData = new GetJsonData();
        jsonData.setMData(result);
        jsonData.processResult();
        list = jsonData.getMObjects();
        TechnologyData.createInstance(list);
        // по завершению скачивания файла JSON и создания всех объектов открываем активити со списком
        Intent intent = new Intent(activity, MainActivity.class);
        activity.finish();
        activity.startActivity(intent);
    }

    public String getResult() {
        //Имитируем задержку для ожидания завершения AsyncTask
        while (result == null) {
            Thread timer = new Thread() {
                public void run() {
                    try {
                        int logoTimer = 0;
                        while (logoTimer < 100) {
                            sleep(100);
                            logoTimer = logoTimer + 100;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.start();
        }
        return result;
    }

    public class ProcessData extends GetJsonData.DownloadJsonData {
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            list = getMObjects();
            TechnologyData.createInstance(list);
            // по завершению скачивания файла JSON и создания всех объектов открываем активити со списком
            Intent intent = new Intent(activity, MainActivity.class);
            activity.finish();
            activity.startActivity(intent);
        }
    }
}
