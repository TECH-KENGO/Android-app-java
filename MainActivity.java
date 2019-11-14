package com.example.app012;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    myDBH helper;
    private SoundPool mSoundPool;
    private int mSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new myDBH(this);
        showDB();

        //ボタンクリック時の処理
        Button btn01 = (Button)findViewById(R.id.button);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RegActivity.class);
                startActivity(in);
            }
        });
        Button btn02 = (Button)findViewById(R.id.button2);
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("sample01", null, null);
                showDB();
                playFromSoundPool();
            }
        });
    }

    public void showDB() {
//        TextView txt01 = (TextView) findViewById(R.id.textView2);
        String widtxt = "";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("sample01", new String[]{"title", "content"}, null, null, null, null, null);
        boolean d = c.moveToFirst();
        String output = "";


        //*-- 追加ここから
        LinearLayout layout01 = (LinearLayout)findViewById(R.id.layout01);
        //全ての子ビューを削除
        layout01.removeAllViews();
        boolean flg = false;
        //*-- 追加ここまで

        while (d) {
            widtxt += c.getString(0) + ":" + c.getString(1) + "\n";
            //*-- 変更ここから
//            output += c.getString(0) + ":" + c.getString(1) + "\n";
            output = c.getString(0) + "\n" + c.getString(1);
            //*-- 変更ここまで

            //*-- 追加ここから
            TextView tmp = new TextView(this);
            tmp.setText(output);
            //フラグを使用して色を交互に変更
            if(flg) {
                tmp.setBackgroundColor(Color.parseColor("#fffacd"));
                flg = false;
            }else{
                tmp.setBackgroundColor(Color.parseColor("#98fb98"));
                flg = true;
            }
            tmp.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            layout01.addView(tmp);
            //*-- 追加ここまで

            d = c.moveToNext();
        }

        c.close();
        wid012.updateText(MainActivity.this, widtxt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDB();
        //音声データを読み込む
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        mSoundId = mSoundPool.load(getApplicationContext(),R.raw.bom,0);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //リリース
        mSoundPool.release();
    }
    private void playFromSoundPool(){
        //再生
        mSoundPool.play(mSoundId,1.0F,1.0F,0,0,1.0F);
    }

}
