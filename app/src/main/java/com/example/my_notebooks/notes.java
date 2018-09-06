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
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class notes extends Activity {


    private RecyclerView rv;
    final Context context = this;
    private SearchView search;
    private LinearLayout focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent intent = getIntent();  //获取传递过来的intent
        Bundle bundle = intent.getExtras(); //取出intent中的bundle
        final String number = bundle.getString("id");

        rv = (RecyclerView) findViewById(R.id.rv_1);
        focus=(LinearLayout)findViewById(R.id.focus);
        search=(SearchView)findViewById(R.id.search);

        rv.setLayoutManager((new GridLayoutManager(this, 3)));
        List<String> list = new ArrayList<>();
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





    }
    protected void onResume() {

// TODO Auto-generated method stub

        super.onResume();

        focus.setFocusable(true);

        focus.setFocusableInTouchMode(true);

        focus.requestFocus();

    }



}
