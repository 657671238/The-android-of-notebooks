package com.example.my_notebooks;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Details_Activity extends Activity {
     private LinearLayout focus;
     private TextView head_tv;
    private TextView back;
     private EditText main_ed;
     private Button bt_rf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

        Intent intent= getIntent();  //获取传递过来的intent
        Bundle bundle=intent.getExtras(); //取出intent中的bundle
        final String head=bundle.getString("head");
        String main=bundle.getString("main");
        final String id=bundle.getString("id");
        final String tab=bundle.getString("tab");
        focus=(LinearLayout)findViewById(R.id.focus3);
        head_tv=(TextView)findViewById(R.id.head);
        main_ed=(EditText)findViewById(R.id.main_et);
        back=(TextView)findViewById(R.id.textView10);
        bt_rf=(Button)findViewById(R.id.button4);
        head_tv.setText(head);
        main_ed.setText(main);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });

        bt_rf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null,1);
                SQLiteDatabase db=helper.getWritableDatabase();
                String sql="Update deatails set text=? where id=? and subject=? and title=?";
                db.execSQL(sql, new Object[ ] {main_ed.getText().toString(),id,tab, head } );
                db.close();
                Toast.makeText(getApplicationContext(), " 修改成功 ",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, null);
                finish();
            }
        });

    }
    protected void onResume() {

// TODO Auto-generated method stub

        super.onResume();

        focus.setFocusable(true);

        focus.setFocusableInTouchMode(true);

        focus.requestFocus();

    }
}
