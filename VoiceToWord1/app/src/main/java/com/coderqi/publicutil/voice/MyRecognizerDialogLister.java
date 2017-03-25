package com.coderqi.publicutil.voice;

import android.content.Context;
import android.widget.Toast;

import com.example.voicetoword.MainActivity;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.ByteArrayOutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 识别回调监听器
 */
public class MyRecognizerDialogLister implements RecognizerDialogListener{
	private Context context;
	public MyRecognizerDialogLister(Context context)
	{
		this.context = context;
	}
	//自定义的结果回调函数，成功执行第一个方法，失败执行第二个方法
	@Override
	public void onResult(RecognizerResult results, boolean isLast){
		try {
			// TODO Auto-generated method stub
			String text = JsonParser.parseIatResult(results.getResultString());
			System.out.println(text);
			if (text.equals("开灯")) {
				text = "检测到指令，电灯已开启";
				SendCmd("http://10.128.187.87:8123/?cmd=%7B%22api_id%22:1004,%22command%22:%22send_code%22,%22mac%22:%22b4:43:0d:38:3e:83%22,%22data%22:%22d73834001020270a0f210f210f220f21270a0e220f220e220f220f22260b0e22260b0e22260b0e220e220e230e220e22260b0e00016b102100000000%22%7D");
				String urlStr = "";
				URL url = new URL(urlStr);
				URLConnection URLconnection = url.openConnection();
				HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
				int responseCode = httpConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
				} else {
				}
			} else if (text.equals("关灯")) {
				text = "检测到指令，电灯已关闭";
				SendCmd("http://10.128.187.87:8123/?cmd=%7B%22api_id%22:1004,%22command%22:%22send_code%22,%22mac%22:%22b4:43:0d:38:3e:83%22,%22data%22:%22d72a3400260b0f22260b0e22260a0e230e22260b0e220e230d220e00016b10201021270a0f220f220e220f22260b0e220e220f220e220e2200000000%22%7D");
			}
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
		}
		catch (IOException e1){}
	}
	/**
	 * 识别回调错误.
	 */
	@Override
	public void onError(SpeechError error) {
		// TODO Auto-generated method stub
		int errorCoder = error.getErrorCode();
		switch (errorCoder) {
		case 10118:
			System.out.println("user don't speak anything");
			break;
		case 10204:
			System.out.println("can't connect to internet");
			break;
		default:
			break;
		}
	}
	private void SendCmd(final String Cmd)
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

					StringBuffer sb=new StringBuffer();
					while((line=br.readLine())!=null){
						sb.append(line);
					}
					Log.i("Network", "true");
					System.out.println(sb.toString());
				} catch (Exception e)
				{
					Log.e("Network","false");
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
}
