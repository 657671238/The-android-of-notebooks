package com.example.my_notebooks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

public class MainActivity extends Activity {
    private Button login;
    private TextView index;
    private EditText idcard;
    private EditText passd;
    private Switch is_chose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        login=(Button)findViewById(R.id.login);
        index=(TextView)findViewById(R.id.index);

        idcard=(EditText)findViewById(R.id.id_card);
        passd=(EditText)findViewById(R.id.password);

        is_chose=(Switch)findViewById(R.id.switch2);

        SharedPreferences sp = getSharedPreferences("MyInfo",Context.MODE_PRIVATE);
        idcard.setText( sp.getString("username", "") );
        passd.setText( sp.getString("password", "") );




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper helper=new DBHelper(getApplicationContext(),"test.db",null,1);
                SQLiteDatabase db=helper.getWritableDatabase();
                Cursor cursor=db.rawQuery("SELECT * FROM users where id=?",new String[]{idcard.getText().toString()});
                if(cursor.getCount()==0) { db.close();  Toast.makeText(getApplicationContext(), " 不存在该用户 ",
                        Toast.LENGTH_SHORT).show(); }
                else{
                    cursor.moveToNext();
                    String pass = cursor.getString(cursor.getColumnIndex("password") );
                    cursor.close();
                    db.close();
                    if(passd.getText().toString().equals(pass)){

                        if(is_chose.isChecked()) {
                            SharedPreferences sp = getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("username", idcard.getText().toString());
                            editor.putString("password", passd.getText().toString());
                            editor.commit();
                        }


                        Toast.makeText(getApplicationContext(), " 登陆成功 ",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, notes.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("id",idcard.getText().toString()); //给Bundle添加key-value值对
                        intent.putExtras(bundle); // 为intent 设置bundle
                        startActivity(intent);


                    }
                    else{
                        Toast.makeText(getApplicationContext(), " 密码错误 ",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,Index.class);
                startActivity(it);

            }
        });
    }

}
