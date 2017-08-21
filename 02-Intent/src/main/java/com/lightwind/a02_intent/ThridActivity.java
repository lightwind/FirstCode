package com.lightwind.a02_intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn3)
    public void onViewClicked() {
    }
}
