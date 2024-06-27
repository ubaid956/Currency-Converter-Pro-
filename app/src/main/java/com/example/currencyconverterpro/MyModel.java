package com.example.currencyconverterpro;

public class MyModel {
    public MyModel(String c_name, int c_img) {
        this.c_name = c_name;
        this.c_img = c_img;
    }

    public  MyModel(){

    }
    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public int getC_img() {
        return c_img;
    }

    public void setC_img(int c_img) {
        this.c_img = c_img;
    }

    String c_name = null;
    int c_img = 0;

}
