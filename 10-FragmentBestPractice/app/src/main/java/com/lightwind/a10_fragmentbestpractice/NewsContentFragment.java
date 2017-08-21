package com.lightwind.a10_fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 功能：新闻详情页面
 * 作者：刘洋
 * 时间：2017/8/21
 */

public class NewsContentFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.news_content_frag, container, false);
        return mView;
    }

    public void refresh(String newsTitle, String newsContent) {
        View visibilityLayout = mView.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = (TextView) mView.findViewById(R.id.news_title);
        TextView newsContentText = (TextView) mView.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);
        newsContentText.setText(newsContent);
    }
}
