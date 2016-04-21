package com.example.asus.myclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsynTaskActivity extends AppCompatActivity {
        private ProgressBar progressBar;
    private TextView mTextView;
    private MyAsynctask myAsynctask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyn_task);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        mTextView=(TextView)findViewById(R.id.asy_text);
        myAsynctask=new MyAsynctask();
        myAsynctask.execute();
    }
    class MyAsynctask extends AsyncTask<String,Integer,String>{
        int i=0;
        int l=0;
        @Override
        protected String doInBackground(String... params) {
            while(i<100){
                i++;
                Log.i(Thread.currentThread().getName(),i+"");
                publishProgress(i,l);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "下载完成";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(Thread.currentThread().getName(),"准备工作");

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mTextView.setText("正在下载"+values[1]+"%");
            progressBar.setProgress(values[0]);
        }
    }
}
