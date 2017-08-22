package com.lightwind.a14_filepersistencetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String edit = mEditText.getText().toString();
        save(edit);
    }

    /**
     * 保存数据的方法
     */
    private void save(String edit) {
        try {
            FileOutputStream outputStream = openFileOutput("data", MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(edit);
            writer.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String load() {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream inputStream = openFileInput("data");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
