package com.example.my_notebooks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

public class Index extends Activity {
    Button index;
    EditText tv_id;
    EditText tv_code;
    EditText tv_code_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_index);

        index=(Button)findViewById(R.id.index_1);
        tv_id=(EditText)findViewById(R.id.id_1);
        tv_code=(EditText)findViewById(R.id.code_1);
        tv_code_1=(EditText)findViewById(R.id.code_2);

        tv_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    if(tv_id.length()<8){
                        tv_id.setText("");
                        tv_id.setHint("学号应长度大于8！");
                        tv_id.setHintTextColor(android.graphics.Color.RED);

                    }
//                    else{
//                        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,1);
//                        SQLiteDatabase db=helper.getWritableDatabase();
//                        Cursor cursor=db.rawQuery("SELECT * FROM users where id=?",new String[]{tv_id.getText().toString()});
//
//                       //Index.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//
//                        if(cursor.getCount()!=0) {   Toast.makeText(getApplicationContext(), " 该用户已存在！ ",
//                            Toast.LENGTH_SHORT).show();
//                        }
//                        db.close();
//                    }

                }
            }
        });

        index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,1);
                SQLiteDatabase db=helper.getWritableDatabase();
                Cursor cursor=db.rawQuery("SELECT * FROM users where id=?",new String[]{tv_id.getText().toString()});

                //Index.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                if(cursor.getCount()!=0) {   Toast.makeText(getApplicationContext(), " 该用户已存在！ ",
                        Toast.LENGTH_SHORT).show();  db.close();
                }
                else{
                    if(tv_id.length()<8){
                        Toast.makeText(getApplicationContext(), " 请输入合法学号密码！ ",
                                Toast.LENGTH_SHORT).show();


                    }
                else {if(tv_code.getText().toString().equals(tv_code_1.getText().toString())){
                    String sql="INSERT INTO users VALUES (?, ?)";
                    db.execSQL(sql, new Object[ ] { new BigInteger(tv_id.getText().toString()), tv_code.getText().toString() } );
                    db.close();
                    Toast.makeText(getApplicationContext(), " 注册成功 ",
                            Toast.LENGTH_SHORT).show();


                    Intent back=new Intent(Index.this,MainActivity.class);
                    startActivity(back);

                }
                else{
                    Toast.makeText(getApplicationContext(),"密码输入错误,重新输入",Toast.LENGTH_SHORT).show();
                    tv_code.setText("");
                    tv_code_1.setText("");

                } }}
            }
        });
    }
}
