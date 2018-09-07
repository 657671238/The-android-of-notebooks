package com.example.my_notebooks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>{
    List<String> list;//存放数据
    Context context;
    String number;

    public ItemAdapter(List<String> list, Context context,String number) {
        this.list = list;
        this.list.add("+");
        this.context = context;
        this.number=number;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
        return holder;
    }

    //在这里可以获得每个子项里面的控件的实例，比如这里的TextView,子项本身的实例是itemView，
// 在这里对获取对象进行操作
    //holder.itemView是子项视图的实例，holder.textView是子项内控件的实例
    //position是点击位置
    @Override
    public void onBindViewHolder(MyViewHolder holder,final  int position) {
        //设置textView显示内容为list里的对应项
        holder.textView.setText(list.get(position));
        //子项的点击事件监听
        holder.itemView.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position!=list.size()-1) {
                    Toast.makeText(context, "点击子项" + position, Toast.LENGTH_SHORT).show();
                   // Intent intent=new Intent(context,son_item.class);
                    Intent mIntent=new Intent(context, son_item.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("id", number); //给Bundle添加key-value值对
                    bundle.putString("tab",list.get(position));
                    mIntent.putExtras(bundle); // 为intent 设置bundle


                    context.startActivity(mIntent);

                }

                else{
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.alertdialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setView(promptsView);
                    final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                    final Button bt1 = (Button) promptsView.findViewById(R.id.button1);
                    final Button bt2 = (Button) promptsView.findViewById(R.id.button2);


                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DBHelper helper = new DBHelper(context, "test.db", null, 1);
                            SQLiteDatabase db = helper.getWritableDatabase();
                            String sql = "INSERT INTO subjects VALUES (NULL, ?, ?)";
                            db.execSQL(sql, new Object[]{number, userInput.getText().toString()});
                            db.close();
                            Toast.makeText(context, " 记录添加成功 ",
                                    Toast.LENGTH_SHORT).show();

                            alertDialog.dismiss();
                            addItem(position, userInput.getText().toString());



                        }
                    });

                }
            }
        });
        holder.itemView.findViewById(R.id.textView).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(position!=list.size()-1){


                Toast.makeText(context, "长按子项"+position, Toast.LENGTH_SHORT).show();
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog_del, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                final Button bt1 = (Button) promptsView.findViewById(R.id.button1);
                final Button bt2 = (Button) promptsView.findViewById(R.id.button2);


                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper helper = new DBHelper(context, "test.db", null, 1);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        db.execSQL( "Delete from subjects where number=? and subject=?", new Object[ ]{number,list.get(position)});
                        db.execSQL( "Delete from deatails where id=? and subject=?", new Object[ ]{number,list.get(position)});

                        db.close();



                        alertDialog.dismiss();


                        removeItem(position);
                    }
                });}
                return false;
            }
        });
    }

    //要显示的子项数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    //这里定义的是子项的类，不要在这里直接对获取对象进行操作
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    /*之下的方法都是为了方便操作，并不是必须的*/

    //在指定位置插入，原位置的向后移动一格
    public boolean addItem(int position, String msg) {
        if (position < list.size() && position >= 0) {
            list.add(position, msg);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    //去除指定位置的子项
    public boolean removeItem(int position) {
        if (position < list.size() && position >= 0) {
            list.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    //清空显示数据
    public void clearAll() {
        list.clear();
        notifyDataSetChanged();
    }
}
