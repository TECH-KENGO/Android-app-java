package com.example.app012;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends Activity {
    myDBH helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        helper = new myDBH(this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        final EditText txt01 = (EditText)findViewById(R.id.editText);
        final EditText txt02 = (EditText)findViewById(R.id.editText2);
        Button btn21 = (Button)findViewById(R.id.button3);
        btn21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f01 = txt01.getText().toString();
                String f02 = txt02.getText().toString();
                if(f01.equals("") || f02.equals("")){
                    Toast.makeText(RegActivity.this, R.string.error01, Toast.LENGTH_SHORT).show();
                }else{
                    ContentValues cv = new ContentValues();
                    cv.put("title", f01);
                    cv.put("content", f02);
                    db.insert("sample01", null, cv);
                    finish();
                }
            }
        });
    }
}

