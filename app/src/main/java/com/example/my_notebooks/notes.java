package com.example.my_notebooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class notes extends Activity {


    private RecyclerView rv;
    final Context context = this;
    private SearchView search;
    private LinearLayout focus;
    private ListView lv_seach;
    private  ArrayAdapter<String> adapter_1;
    final List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent intent = getIntent();  //获取传递过来的intent
        Bundle bundle = intent.getExtras(); //取出intent中的bundle
        final String number = bundle.getString("id");

        lv_seach=(ListView)findViewById(R.id.list_seach);
        rv = (RecyclerView) findViewById(R.id.rv_1);
        focus=(LinearLayout)findViewById(R.id.focus);
        search=(SearchView)findViewById(R.id.search);



        rv.setLayoutManager((new GridLayoutManager(this, 3)));

        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM subjects where number=?", new String[]{number});
        while (cursor.moveToNext()) { //遍历结果集
            String name = cursor.getString(cursor.getColumnIndex("subject"));
            list.add(name);
        }
        db.close();
        ItemAdapter itemAdapter = new ItemAdapter(list, this,number);
        rv.setAdapter(itemAdapter);


        adapter_1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        lv_seach.setAdapter(adapter_1);
      //  lv_seach.setVisibility(View.VISIBLE);
        lv_seach.setTextFilterEnabled(true);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                lv_seach.setVisibility(View.VISIBLE);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                      lv_seach.setFilterText(newText);
                }else{
                    lv_seach.clearTextFilter();
                    lv_seach.setVisibility(View.GONE);
                }
                return false;
            }
        });

        lv_seach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mIntent=new Intent(context, son_item.class);
                Bundle bundle=new Bundle();
                bundle.putString("id", number); //给Bundle添加key-value值对
                bundle.putString("tab",((TextView)view).getText().toString());
                mIntent.putExtras(bundle); // 为intent 设置bundle
                startActivity(mIntent);

            }
        });



    }
    protected void onResume() {

// TODO Auto-generated method stub

        super.onResume();

        focus.setFocusable(true);

        focus.setFocusableInTouchMode(true);

        focus.requestFocus();
        adapter_1.notifyDataSetChanged();






    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lv_seach.setVisibility(View.GONE);

    }
}
