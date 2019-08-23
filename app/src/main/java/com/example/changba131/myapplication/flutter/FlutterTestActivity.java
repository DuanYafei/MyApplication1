package com.example.changba131.myapplication.flutter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;

import com.example.changba131.myapplication.R;

public class FlutterTestActivity extends AppCompatActivity {


  public static void show(Context context) {
    Intent intent = new Intent(context, FlutterTestActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flutter_test);

//    getSupportFragmentManager().beginTransaction()
//        .replace(R.id.container,new AppCompatDialogFragment())
//        .commitAllowingStateLoss();

  }

}
