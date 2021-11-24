package com.example.nonusenum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class VersionCodeActivity extends AppCompatActivity {
    Integer[] versionCodeArr;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_code);

        versionCodeArr = new Integer[]{
                Build.VERSION_CODES.KITKAT_WATCH, Build.VERSION_CODES.LOLLIPOP,
                Build.VERSION_CODES.HONEYCOMB, Build.VERSION_CODES.N};

        textView = findViewById(R.id.console);

        BackGroundTask task = new BackGroundTask();
        task.execute(versionCodeArr);
    }

    class BackGroundTask extends AsyncTask<Integer[], Integer, Void> {
        @Override
        protected Void doInBackground(Integer[]... integers) {
            for (Integer i:versionCodeArr) {
                try {
                    Thread.sleep(Build.VERSION_CODES.CUR_DEVELOPMENT /
                            Build.VERSION_CODES.GINGERBREAD);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView.setText(textView.getText() + Integer
                    .toString(values[Build.VERSION_CODES.BASE - Build.VERSION_CODES.BASE]));
        }
    }

}