package com.example.my_notebooks;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertActivity extends Activity {
    Button btin;
    TextView tv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent= getIntent();  //获取传递过来的intent
        Bundle bundle=intent.getExtras(); //取出intent中的bundle
        final String id=bundle.getString("id"); //取出key对应的value
        final String tab=bundle.getString("tab"); //取出key对应的value

        btin=(Button)findViewById(R.id.button);
        tv_back=(TextView)findViewById(R.id.textView7);

        btin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                EditText et1=(EditText)findViewById(R.id.editText1);
                EditText et2=(EditText)findViewById(R.id.editText2);
                if(et1.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "标题不能为空！！！ ",
                            Toast.LENGTH_SHORT).show();

                }
                else{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
                Date date = new Date(System.currentTimeMillis());

                dats person=new dats(et1.getText().toString(), et2.getText().toString(),simpleDateFormat.format(date));
                insert(person,id,tab); //代码后页
                setResult(RESULT_OK, null);
                finish();
            }}
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });


    }
    public void insert(dats person,String id,String tab){
        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql="INSERT INTO deatails VALUES (?,?, ?, ?,?,?)";
        db.execSQL(sql, new Object[ ] {id,tab, person.getName(), person.getImageId(),person.getTime(),person.getTime() } );
        db.close();
        Toast.makeText(getApplicationContext(), " 记录添加成功 ",
                Toast.LENGTH_SHORT).show();
    }
}
