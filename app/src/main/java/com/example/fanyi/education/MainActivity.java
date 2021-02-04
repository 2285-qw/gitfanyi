package com.example.fanyi.education;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eulav.education.R;
import com.example.fanyi.network.NetworkStatus;
import com.example.fanyi.util.ReplaceABC;
import com.example.fanyi.util.UrlString;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private TextView edTextMword;
	private Button btnFanyi;
	private TextView tvTextAfter;
	private ImageView ivZt;

	private String mUrl;
	private String mWord;
	private String textAfter;

	/**
	 */
	private void findViews() {
		edTextMword = (TextView) findViewById(R.id.ed_text_mword);
		btnFanyi = (Button) findViewById(R.id.btn_fanyi);
		tvTextAfter = (TextView) findViewById(R.id.tv_text_english);
		ivZt = (ImageView) findViewById(R.id.iv_zt);

		ivZt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, VersionActivity.class);
				startActivity(i);
			}
		});

		btnFanyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mWord = edTextMword.getText().toString().trim();
				if (mWord.equals(null) || mWord.equals("")) {
					Toast.makeText(getApplication(), "������Ҫ���������", 0).show();
				} else {
					if (NetworkStatus.isNetworkAvailable(MainActivity.this)) {
//						Toast.makeText(getApplicationContext(), "��ǰ�������",
//								Toast.LENGTH_LONG).show();
						String s1;
						String s2;
						s1 = ReplaceABC.mReplace(mWord, " ", "%20");
						try {
							s2 = URLEncoder.encode(s1, "utf-8");
							mUrl = UrlString.mUrlNOChangeForward + s2
									+ UrlString.mUrlNOChangeBehind;
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						fanYi(mUrl);
					} else {
						Toast.makeText(getApplicationContext(), "��ǰ���粻���ã�",
								Toast.LENGTH_LONG).show();
					}

				}
			}

		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fanyi);

		if (NetworkStatus.isNetworkAvailable(this)) {
			Toast.makeText(getApplicationContext(), "��ǰ�������", Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(getApplicationContext(), "��ǰ���粻���ã�",
					Toast.LENGTH_LONG).show();
		}
		findViews();

	}

	private void fanYi(final String str) {

		new AsyncTask<String, Void, String>() {
			@Override
			protected String doInBackground(String... params) {
				try {
					URL url = new URL(params[0]);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					InputStream is = connection.getInputStream();
					InputStreamReader isr = new InputStreamReader(is, "utf-8");
					BufferedReader bf = new BufferedReader(isr);
					String line;
					StringBuffer sb = new StringBuffer();
					while ((line = bf.readLine()) != null) {
						System.out.println(line);
						sb.append(line);
					}
					bf.close();
					isr.close();
					is.close();
					JSONObject jsonObject = new JSONObject(sb.toString());
					JSONArray trans_result = jsonObject
							.getJSONArray("trans_result");
					StringBuffer afterText = new StringBuffer();
					for (int i = 0; i < trans_result.length(); i++) {
						JSONObject jo = trans_result.optJSONObject(i);
						afterText.append(jo.getString("dst"));
					}
					textAfter = afterText.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return params[0];
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				tvTextAfter.setText(textAfter);
//				super.onPostExecute(result);
			}
		}.execute(str);
		
	}

}
