package com.monk.aynctask;

import android.os.AsyncTask;

/**
 * @author monk
 * @date 2019-01-21
 */
public class DownloadAsynctask extends AsyncTask<String,Integer,Boolean> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(String... strings) {
        int count = strings.length;

        publishProgress(count);
        for (; ; ) {
            if (isCancelled()) {
              break;
            }
        }

        return Boolean.valueOf(strings[0]);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

    }
}
