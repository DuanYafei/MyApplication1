package com.example.changba131.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.changba131.myapplication.widget.AutoVerticalScrollTextView;
import com.example.changba131.myapplication.widget.KtvChallengeProgressBar;

public class MainActivity extends Activity {
    private KtvChallengeProgressBar mProgressBar;
    private EditText mEditText;
    private TextView mOkBtn;

    AutoVerticalScrollTextView mScrolltextView;

    String[] texts = new String[]{"136576ajhsdh", "2asdadadsasda", "3asdasdasd", "4dsdfdfdfd", "5fgfgfgfgfgfg",
            "6cvcvcvcvcvanmsndm", "7kjkjkjkjkjkjk", "8lolouiuiuiuiui"};
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressbar);
        mEditText = findViewById(R.id.edit_text);
        mOkBtn = findViewById(R.id.btn_ok);
        init();
    }

    private void init() {
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mProgressBar.setProgress(Integer.valueOf(mEditText.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_pk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setMode(KtvChallengeProgressBar.MODE_PK);
            }
        });

        findViewById(R.id.btn_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setMode(KtvChallengeProgressBar.MODE_NORMAL);
            }
        });


        mScrolltextView = findViewById(R.id.text_view);
        mScrolltextView.setText("初始");
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                mScrolltextView.setText(texts[index++ % texts.length]);
                handler.postDelayed(this, 5000);
            }
        });

    }
}
