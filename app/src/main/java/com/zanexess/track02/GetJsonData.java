package com.zanexess.track02;


import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetJsonData extends GetRawData {
    final static String URL = "http://mobevo.ext.terrhq.ru/shr/j/ru/technology.js";
    private String LOG_TAG = GetJsonData.class.getSimpleName();
    private List<TechnologyData.Technology> mObjects;
    private Uri mDestinationUri;

    public GetJsonData() {
        super(null);
        createAndUpdateUri();
        mObjects = new ArrayList<>();
    }

    public List<TechnologyData.Technology> getMObjects() {
        return mObjects;
    }

    public void execute() {
        super.setMRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Build URI = " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());
    }

    public boolean createAndUpdateUri() {
        mDestinationUri = Uri.parse(URL).buildUpon().build();
        return mDestinationUri != null;
    }

    public void processResult() {
        if (getMDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading raw file");
            return;
        }
        final String TECHNOLOGY = "technology";
        final String ID = "id";
        final String URL_PICTURE = "picture";
        final String TITLE = "title";
        final String INFO = "info";
        try {
            JSONObject jsonData = new JSONObject(getMData());
            JSONObject a = jsonData.getJSONObject(TECHNOLOGY);
            for (int i = 0; i < a.names().length(); i++) {
                JSONObject jsonObject = a.getJSONObject(a.names().get(i).toString());
                String id = jsonObject.getString(ID);
                String url = jsonObject.getString(URL_PICTURE);
                String title = jsonObject.getString(TITLE);
                String info = jsonObject.optString(INFO);
                TechnologyData.Technology technology = new TechnologyData.Technology(id, url, title, info);
                this.mObjects.add(technology);
            }
        } catch (JSONException json) {
            json.printStackTrace();
            Log.e(LOG_TAG, "Error processing Json data");
        }
    }

    public class DownloadJsonData extends DownloadRawData {
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] par = {mDestinationUri.toString()};
            return super.doInBackground(par);
        }
    }
}