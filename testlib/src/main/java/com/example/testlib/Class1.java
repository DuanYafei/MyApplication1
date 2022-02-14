package com.example.testlib;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

/**
 * Created by dyf on 18/1/25.
 */

public class Class1 {
  private String str;

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  public ArrayList<Fragment> foo(ArrayList<?> list) {
    return (ArrayList<Fragment>) list;
  }

}
