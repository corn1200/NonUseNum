package com.example.nonusenum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OcrActivity extends AppCompatActivity {
    Bitmap image;
    private TessBaseAPI mTess;
    String dataPath = "";
    TextView OCRTextView;
    TextView exeOCRBtn;
    BackgroundTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        OCRTextView = findViewById(R.id.OCRTextView);
        exeOCRBtn = findViewById(R.id.executeOCRBtn);
        exeOCRBtn.setOnClickListener(v -> {
            task = new BackgroundTask();
            task.execute();
        });

        image = BitmapFactory.decodeResource(getResources(), R.drawable.sample);

        dataPath = getFilesDir() + "/tesseract/";

        checkFile(new File(dataPath + "tessdata/"));

        String lang = "eng";

        mTess = new TessBaseAPI();
        mTess.init(dataPath, lang);
    }

    private void copyFiles() {
        try {
            String filePath = dataPath + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outputStream = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile(File file) {
        if (!file.exists() && file.mkdirs()) {
            copyFiles();
        }

        if (file.exists()) {
            String dataFilePath = dataPath + "tessdata/eng.traineddata";
            File dataFile = new File(dataFilePath);
            if (!dataFile.exists()) {
                copyFiles();
            }
        }
    }

    class BackgroundTask extends AsyncTask<View, String, Void> {

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(OcrActivity.this, "완료됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            OCRTextView.setText(values[0]);
        }

        @Override
        protected Void doInBackground(View... views) {
            String OCRResult = null;
            mTess.setImage(image);
            OCRResult = mTess.getUTF8Text();

            publishProgress(OCRResult);
            return null;
        }
    }
}