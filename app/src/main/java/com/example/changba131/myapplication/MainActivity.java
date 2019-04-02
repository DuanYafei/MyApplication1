package com.example.changba131.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.changba131.myapplication.widget.KtvChallengeProgressBar;

public class MainActivity extends Activity {
    private KtvChallengeProgressBar mProgressBar;
    private EditText mEditText;
    private TextView mOkBtn;

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
    }
}
