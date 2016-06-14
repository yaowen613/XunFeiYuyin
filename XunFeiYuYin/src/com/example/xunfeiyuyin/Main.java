package com.example.xunfeiyuyin;

import java.io.InputStream;
import java.util.ArrayList;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SpeechListener;
import com.iflytek.speech.SpeechUser;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

	// ���
	private Button button = null;
	private TextView result = null;
	private Toast mToast = null;

	// ����ʶ��
	private final String APP_ID = "575f79fa";
	private final static String KEY_GRAMMAR_ID = "grammar_id";
	private RecognizerDialog recognizerDialog = null;
	private String grammarText = null;
	private String grammarID = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.main);

		button = (Button) super.findViewById(R.id.button_start);
		result = (TextView) super.findViewById(R.id.editText);

		// ��ʼ��ʶ��
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		mToast.setMargin(0f, 0.2f);
		recognizerDialog = new RecognizerDialog(this, "appid=" + APP_ID);
		SpeechUser.getUser().login(this, null, null, "appid=" + APP_ID, loginListener);

		// ��ȡ������﷨ID
		SharedPreferences preference = this.getSharedPreferences("test", MODE_PRIVATE);
		grammarID = preference.getString(KEY_GRAMMAR_ID, null);
		grammarText = readTestFile();

		this.button.setOnClickListener(new Btn());

	}

	private class Btn implements OnClickListener {

		@Override
		public void onClick(View v) {

			//MainActivity.this.voice.setImageResource(R.drawable.voicelight);
			recognizerDialog.setListener(mRecoListener);
			recognizerDialog.setEngine(null, "grammar_type=test", grammarText);
			recognizerDialog.show();
		}
	}

	// ����ʶ���û���¼������
	private SpeechListener loginListener = new SpeechListener() {

		@Override
		public void onData(byte[] arg0) {
		}

		@Override
		public void onEvent(int arg0, Bundle arg1) {
		}

		@Override
		public void onEnd(SpeechError arg0) {
			// TODO Auto-generated method stub
			if (arg0 != null) {
				mToast.setText("��¼ʧ��");
				mToast.show();
			} else {
				mToast.setText("��¼�ɹ�");
				mToast.show();
			}
		}
	};

	// ��ȡ����ʶ���﷨
	private String readTestFile() {
		int len = 0;
		byte[] buf = null;
		String grammar = "";
		try {
			InputStream in = getAssets().open("gm_continuous_digit.test");
			len = in.available();
			buf = new byte[len];
			in.read(buf, 0, len);
			grammar = new String(buf, "gb2312");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return grammar;

	}

	// ʶ�����ص�
	private RecognizerDialogListener mRecoListener = new RecognizerDialogListener() {

		@Override
		public void onEnd(SpeechError error) {
			//MainActivity.this.voice.setImageResource(R.drawable.voice);
		}

		@Override
		public void onResults(ArrayList<RecognizerResult> results, boolean isLast) {
			// TODO Auto-generated method stub
			String text = "";
			text = results.get(0).text;
			mToast.setText("ʶ����Ϊ��" + text);
			mToast.show();
			result.setText("ʶ����Ϊ��" + text);
		}

	};

}
