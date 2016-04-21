package com.example.asus.myclient;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mButton,mButton1,mButton2,mButton3;
    private TextView mTextView;
    private  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private MyThread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myThread=new MyThread();
        myThread.start();
        mButton=(Button)findViewById(R.id.but1);
        mButton1=(Button)findViewById(R.id.but2);
        mButton2=(Button)findViewById(R.id.but3);
        mButton3=(Button)findViewById(R.id.but4);
        mTextView=(TextView)findViewById(R.id.text1);
        mButton.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.but1:
                new Thread(){
                    @Override
                    public void run() {
                        while(true){
                            long date=System.currentTimeMillis();

                            String time=format.format(date);
//                   Message msg=new Message();//每次创建一个对象
                            Message msg=mHandler.obtainMessage();//使用已有的Message队列
                            msg.obj=time;
                            msg.what=1;
                            mHandler.sendMessage(msg);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
                break;
            case R.id.but2:

                new Thread(){
                    @Override
                    public void run() {
                        while (true) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("正在运行的线程",Thread.currentThread().getName());

                                    String time=format.format(new Date());
                                    mTextView.setText(time);

                                }
                            }, 1000);//启动线程延迟的时间
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

                break;
            case R.id.but3:
                Message msg=myThread.getHandler().obtainMessage();
                msg.obj=89;
                myThread.getHandler().sendMessage(msg);
                break;
            case R.id.but4:
                Intent intent=new Intent(MainActivity.this,AsynTaskActivity.class);
                startActivity(intent);
                break;

        }


    }
    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                String time = (String) msg.obj;
                mTextView.setText(time);
                    break;
            }
        }
    };
       class MyThread extends Thread{
           Handler handler=null;
           public Handler getHandler(){
               return handler;
           }
           @Override
           public void run() {
               Looper.prepare();//创建一个消息队列
               handler=new Handler(){
                   @Override
                   public void handleMessage(Message msg) {
                               Log.d("子线程",msg.obj+"");
                   }
               };
               Looper.loop();
           }
       }
}
