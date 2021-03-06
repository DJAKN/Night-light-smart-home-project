package com.example.voicetoword;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class Main4Activity extends Activity implements OnClickListener{

    private SensorManager sm;
    private Sensor ligthSensor;
    private StringBuffer sb;
    private StringBuffer sb2;
    private StringBuffer sb3;
    StringBuffer sb0=new StringBuffer();
    StringBuffer sb00=new StringBuffer();
    private TextView tvValue;
    private TextView tvValue2;
    private TextView tvValue3;
    private TextView tvValue8;
    private TextView tvValue9;
    private TextView tvValue10;
    private int flag=-1;
    Button but = null;
    float t=0;
    float max=0;
    float min=10000;
    float sum=0;
    float ave=0;
    float n=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_main4);
        but = (Button) findViewById(R.id.button2);
        but.setOnClickListener(this);
        but = (Button) findViewById(R.id.button5);
        but.setOnClickListener(this);
        sb = new StringBuffer();
        tvValue = (TextView) findViewById(R.id.button1);
        tvValue2 = (TextView) findViewById(R.id.button3);
        tvValue3 = (TextView) findViewById(R.id.button4);
        tvValue8 = (TextView) findViewById(R.id.button8);
        tvValue9 = (TextView) findViewById(R.id.button9);
        tvValue10 = (TextView) findViewById(R.id.button10);
        //获取SensorManager对象
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取Sensor对象
        ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        sm.registerListener(new MySensorListener(), ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public class MySensorListener implements SensorEventListener {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        public void onSensorChanged(SensorEvent event) {
            float lux = event.values[0];
            sb = new StringBuffer();
            sb.append("光照强度" + "\n" + lux + " lx");

            tvValue.setText(sb.toString());
            try {
                if (lux<10) {
                    flag=1;
                    //SendCmd("http://10.128.187.87:8123/?cmd=%7B%22api_id%22:1004,%22command%22:%22send_code%22,%22mac%22:%22b4:43:0d:38:3e:83%22,%22data%22:%22d73834001020270a0f210f210f220f21270a0e220f220e220f220f22260b0e22260b0e22260b0e220e220e230e220e22260b0e00016b102100000000%22%7D");
                    String urlStr = "";
                    URL url = new URL(urlStr);
                    URLConnection URLconnection = url.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                    } else {
                    }
                } else if (lux>100) {
                    flag=0;
                    //SendCmd("http://10.128.187.87:8123/?cmd=%7B%22api_id%22:1004,%22command%22:%22send_code%22,%22mac%22:%22b4:43:0d:38:3e:83%22,%22data%22:%22d72a3400260b0f22260b0e22260a0e230e22260b0e220e230d220e00016b10201021270a0f220f220e220f22260b0e220e220f220e220e2200000000%22%7D");
                }
            }
            catch (IOException e1){}
            sb2 = new StringBuffer();
            StringBuffer s = SendCmd("http://10.128.187.87:8000/c?get=temperature");
            String tmp = s.toString();
            if(tmp.indexOf(".") != -1)
            {
                sb2.append("温度" + "\n" + tmp.substring(0, tmp.indexOf(".")+2) + "℃");
                t = Float.parseFloat(tmp.substring(0, tmp.indexOf(".") + 2));
                if(t<min)
                    min=t;
                if(t>max)
                    max=t;
                sum+=t;
                n+=1;
                ave=sum/n;
            }
            else
            {
                sb2.append("温度" + "\n" + tmp + "℃");
            }
            tvValue2.setText(sb2.toString());
            sb2 = new StringBuffer();
            if(max==0)
                sb2.append("max准备");
            else
                sb2.append("max"+max);
            tvValue8.setText(sb2.toString());
            sb2 = new StringBuffer();
            if(min==10000)
                sb2.append("min准备");
            else
                sb2.append("min"+min);
            tvValue9.setText(sb2.toString());
            sb2 = new StringBuffer();
            if(ave==0)
                sb2.append("ave准备");
            else
                sb2.append("ave"+ave);
            tvValue10.setText(sb2.toString());
            sb3 = new StringBuffer();
            s = SendCmd2("http://10.128.187.87:8000/c?get=humidity");
            tmp = s.toString();
            if(tmp.indexOf(".") != -1)
            {
                sb3.append("湿度" + "\n" + tmp.substring(0, tmp.indexOf(".")+2) + "%");
            }
            else
            {
                sb3.append("湿度" + "\n" + tmp + "%");
            }
            tvValue3.setText(sb3.toString());
        }

    }
    private StringBuffer SendCmd(final String Cmd)
    {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                String urlOpen = Cmd;
                try{
                    URL url;
                    HttpURLConnection conn;
                    InputStream is;
                    url=new URL(urlOpen);
                    conn=(HttpURLConnection) url.openConnection();
                    conn.connect();

                    is=conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));

                    String line=null;
                    sb0=new StringBuffer();
                    while((line=br.readLine())!=null){
                        sb0.append(line);
                    }
                    Log.i("Network", "true");
                    System.out.println(sb0.toString());
                } catch (Exception e)
                {
                    Log.e("Network","false");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return sb0;
    }
    private StringBuffer SendCmd2(final String Cmd)
    {

        Thread thread;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                String urlOpen = Cmd;
                try{
                    URL url;
                    HttpURLConnection conn;
                    InputStream is;
                    url=new URL(urlOpen);
                    conn=(HttpURLConnection) url.openConnection();
                    conn.connect();

                    is=conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));

                    String line=null;
                    sb00=new StringBuffer();
                    while((line=br.readLine())!=null){
                        sb00.append(line);
                    }
                    Log.i("Network", "true");
                    System.out.println(sb00.toString());
                } catch (Exception e)
                {
                    Log.e("Network","false");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return sb00;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(Main4Activity.this, MainActivity.class);
                startActivity(intent);
                Main4Activity.this.finish();
                break;
            case R.id.button5:
                intent = new Intent(Main4Activity.this, Main2Activity.class);
                startActivity(intent);
                Main4Activity.this.finish();
                break;
        }
    }
}
