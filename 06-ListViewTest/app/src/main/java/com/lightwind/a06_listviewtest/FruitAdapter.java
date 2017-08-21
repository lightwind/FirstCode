package com.lightwind.a06_listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义适配器
 * Created by lightwind on 2017/8/17.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId;

    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 获取当前项的Fruit实例
        Fruit fruit = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) convertView.findViewById(R.id.fruit_image);
            viewHolder.fruitName = (TextView) convertView.findViewById(R.id.fruit_name);
            // 将ViewHolder储存在convertView中
            convertView.setTag(viewHolder);
        } else {
            // 重新获取ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return convertView;
    }

    private class ViewHolder {
        private ImageView fruitImage;
        private TextView fruitName;
    }
}
