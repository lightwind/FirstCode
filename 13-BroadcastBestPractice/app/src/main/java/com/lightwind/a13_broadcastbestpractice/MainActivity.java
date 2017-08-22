package com.lightwind.a13_broadcastbestpractice;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.force_offline)
    public void onViewClicked() {
        Intent intent = new Intent("com.lightwind.a13_broadcastbestpractice.FORCE_OFFLINE");
        sendBroadcast(intent);
    }
}
