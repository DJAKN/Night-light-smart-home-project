package com.coderqi.publicutil.voice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import com.iflytek.cloud.speech.DataDownloader;
import com.iflytek.cloud.speech.DataUploader;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class VoiceToWord extends Activity{
	private Context context;
	private Toast mToast;
	//ʶ�𴰿�
	private RecognizerDialog iatDialog;
	//ʶ�����
	private SpeechRecognizer iatRecognizer;
	//���棬���浱ǰ�������������һ������Ӧ�ó���ʹ��.
	private SharedPreferences mSharedPreferences;
	private RecognizerDialogListener recognizerDialogListener = null;
	
	public VoiceToWord(Context context,String APP_ID) {
		// TODO Auto-generated constructor stub
		//�û���¼
		this.context = context;
		SpeechUser.getUser().login(context, null, null
						, "appid=" + APP_ID, listener);
		//��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
		iatDialog =new RecognizerDialog(context);
		mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
		//��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
		iatDialog =new RecognizerDialog(context);
		//��ʼ���������.
		mSharedPreferences = context.getSharedPreferences(context.getPackageName(),MODE_PRIVATE);
	}
	
	public VoiceToWord(Context context,String APP_ID,RecognizerDialogListener recognizerDialogListener)
	{
		this.context = context;
		SpeechUser.getUser().login(context, null, null
						, "appid=" + APP_ID, listener);
		//��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
		iatDialog =new RecognizerDialog(context);
		mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
		//��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
		iatDialog =new RecognizerDialog(context);
		//��ʼ���������.
		mSharedPreferences = context.getSharedPreferences(context.getPackageName(),MODE_PRIVATE);
		this.recognizerDialogListener = recognizerDialogListener;
	}
	
	public void GetWordFromVoice()
	{
		boolean isShowDialog = mSharedPreferences.getBoolean("ia_show",true);
		if (isShowDialog) {
			//��ʾ������дDialog.
			showIatDialog();
		} else {
			if(null == iatRecognizer) {
			iatRecognizer=SpeechRecognizer.createRecognizer(this);
			}
			if(iatRecognizer.isListening()) {
				iatRecognizer.stopListening();
//				((Button) findViewById(android.R.id.button1)).setEnabled(false);
			} else {
			}
		}
	}
	
	
	private void showTip(String str)
	{
		if(!TextUtils.isEmpty(str))
		{
			mToast.setText(str);
			mToast.show();
		}
	}
	/**
	 * ��ʾ��д�Ի���.
	 * @param
	 */
	public void showIatDialog()
	{
		if(null == iatDialog) {
		//��ʼ����дDialog	
		iatDialog =new RecognizerDialog(this);
		}

		//��ȡ�������
		String engine = mSharedPreferences.getString(
				"iat_engine",
				"iat");
				
		//���Grammar_ID����ֹʶ��������дʱGrammar_ID�ĸ���
		iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		//������дDialog������
		iatDialog.setParameter(SpeechConstant.DOMAIN, engine);
		//���ò����ʲ�����֧��8K��16K 
		String rate = mSharedPreferences.getString(
				"sf",
				"sf");
		if(rate.equals("rate8k"))
		{
			iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
		}
		else 
		{
			iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		}
		if(recognizerDialogListener == null)
		{
			getRecognizerDialogListener();
		}
		//��ʾ��д�Ի���
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
	}
	private void getRecognizerDialogListener()
	{
		/**
		 * ʶ��ص�������
		 */
		recognizerDialogListener=new MyRecognizerDialogLister(context);
	}
	
	/**
	 * �û���¼�ص�������.
	 */
	private SpeechListener listener = new SpeechListener()
	{

		@Override
		public void onData(byte[] arg0) {
		}

		@Override
		public void onCompleted(SpeechError error) {
			if(error != null) {
				System.out.println("user login success");
			}			
		}

		@Override
		public void onEvent(int arg0, Bundle arg1) {
		}		
	};
}
