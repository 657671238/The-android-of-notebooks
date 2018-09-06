package com.example.my_notebooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class dats_Adapter extends ArrayAdapter<dats> {
    private int resourceId; // 添加一个成员
    public dats_Adapter(Context context, int resource, List<dats> objects) {
        super(context, resource, objects);  //3 个参数: 上下文、ListView 子项布局id 、数据
        resourceId=resource; // 添加语句
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        dats fruit = getItem(position); // 获取当前选中的Fruit实例，将其内容填充到各个位置
//填充ListView 布局
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView head = (TextView) view.findViewById(R.id.textView2);
        TextView main = (TextView) view.findViewById(R.id.textView3);
        TextView time = (TextView) view.findViewById(R.id.textView4);

        head.setText(fruit.getName());
        main.setText(fruit.getImageId());
        time.setText(fruit.getTime());

        return view;
    }
}
