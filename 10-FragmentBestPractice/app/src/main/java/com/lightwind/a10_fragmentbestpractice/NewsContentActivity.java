package com.lightwind.a10_fragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        Intent intent = getIntent();
        // 获取传入的新闻标题
        String newsTitle = intent.getStringExtra("news_title");
        // 获取传入的新闻内容
        String newsContent = intent.getStringExtra("news_content");
        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager
                ().findFragmentById(R.id.news_content_fragment);
        // 刷新NewsContentFragment界面
        newsContentFragment.refresh(newsTitle, newsContent);
    }
}
