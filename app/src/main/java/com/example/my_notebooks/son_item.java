package com.example.my_notebooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class son_item extends Activity {
    ImageButton bt3;
    ListView dets;
    LinearLayout focus;
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_son_item);
        Intent intent= getIntent();  //获取传递过来的intent
        Bundle bundle=intent.getExtras(); //取出intent中的bundle
        final String id=bundle.getString("id"); //取出key对应的value
        final String tab=bundle.getString("tab"); //取出key对应的value


        focus=(LinearLayout)findViewById(R.id.focus1);
        bt3=(ImageButton) findViewById(R.id.button3);
        dets=(ListView)findViewById(R.id.list_det);

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击子项" +id+tab, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(son_item.this,InsertActivity.class);

                Bundle bundle=new Bundle();
                bundle.putString("id", id); //给Bundle添加key-value值对
                bundle.putString("tab",tab);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);


            }
        });

        final ArrayList<dats> list = new ArrayList<dats>();
        DBHelper helper=new DBHelper(getApplicationContext(),"test.db",null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM deatails where id=? and subject=? ORDER BY order_text ASC",new String[]{id,tab});
        while (cursor.moveToNext()) { //遍历结果集
            String head = cursor.getString(cursor.getColumnIndex("title") );
            String main = cursor.getString(cursor.getColumnIndex("text") );
            String time = cursor.getString(cursor.getColumnIndex("time") );
            list.add(new dats(head,main,time));

        }
        cursor.close(); //关闭cursor
        db.close();
        dats_Adapter adapter = new dats_Adapter(
                son_item.this,
                R.layout.dats_layout,
                list );
        ListView li=(ListView)findViewById(R.id.list_det);
        li.setAdapter(adapter);

        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idid) {

                dats f=list.get(position);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                String now=simpleDateFormat.format(date);
                DBHelper helper=new DBHelper(getApplicationContext(),"test.db",null,1);
                SQLiteDatabase db=helper.getWritableDatabase();
                db.execSQL( "update  deatails set order_text=? where id=? and subject=? and title=?", new Object[ ]{now,id,tab, f.getName()});
                db.close();


                Toast.makeText(getApplicationContext(), "选中了："+f.getName(), Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(son_item.this,Details_Activity.class);

                Bundle bundle=new Bundle();
                bundle.putString("id", id);
                bundle.putString("tab",tab);
                bundle.putString("head", f.getName()); //给Bundle添加key-value值对
                bundle.putString("main",f.getImageId());
                bundle.putString("time",f.getTime());
                intent.putExtras(bundle);

                startActivityForResult(intent, 99);
            }
        });
        li.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long idid) {
                final dats f1=list.get(position);
                Toast.makeText(getApplicationContext(), "长按选中了："+f1.getName(), Toast.LENGTH_SHORT).show();

                final LayoutInflater li = LayoutInflater.from(context);
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
                        db.execSQL( "Delete from deatails where id=? and subject=? and title=?", new Object[ ]{id,tab, f1.getName()});
                        db.close();
                        alertDialog.dismiss();
                        onCreate(null);

                    }
                });
                return true;
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==RESULT_OK){
                onCreate(null);
            }}
        else if(requestCode==99)
            if(resultCode==RESULT_OK){
                    onCreate(null); }
    }
    protected void onResume() {

// TODO Auto-generated method stub

        super.onResume();

        focus.setFocusable(true);

        focus.setFocusableInTouchMode(true);

        focus.requestFocus();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onCreate(null);
    }
}
