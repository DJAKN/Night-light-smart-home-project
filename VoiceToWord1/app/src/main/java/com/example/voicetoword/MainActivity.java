package com.example.voicetoword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.coderqi.publicutil.voice.VoiceToWord;

public class MainActivity extends Activity implements OnClickListener{
	Button but = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		but = (Button) findViewById(R.id.button1);
		but.setOnClickListener(this);
		but = (Button) findViewById(R.id.button2);
		but.setOnClickListener(this);
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
			//ÌýÐ´°´Å¥
			case R.id.button1:
				VoiceToWord voice = new VoiceToWord(MainActivity.this,"534e3fe2");
				voice.GetWordFromVoice();
				break;
				case R.id.button2:
					Intent intent = new Intent(MainActivity.this, Main2Activity.class);
					startActivity(intent);
					MainActivity.this.finish();
					break;
			}
	}
}
