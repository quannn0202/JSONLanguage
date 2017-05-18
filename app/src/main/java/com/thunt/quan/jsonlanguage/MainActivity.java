package com.thunt.quan.jsonlanguage;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageButton img_vn,img_anh;
    TextView tv_nd;
    String noidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new JSONLanguage().execute("http://khoapham.vn/KhoaPhamTraining/json/tien/demo3.json");
        AnhXa();

    }

    private void AnhXa() {
        img_vn = (ImageButton) findViewById(R.id.img_vn);
        img_anh = (ImageButton) findViewById(R.id.img_anh);
        tv_nd = (TextView) findViewById(R.id.tv_nd);
    }

    private class JSONLanguage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            noidung = s;
            showNoidung("vn");
          img_anh.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  showNoidung("en");
              }
          });
            img_vn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNoidung("vn");
                }
            });


            super.onPostExecute(s);
        }
    }

    private void showNoidung(String lang) {
        JSONObject object = null;
        try {
            object = new JSONObject(noidung);
            JSONObject objectLang = object.getJSONObject("language");
            JSONObject objectVN = objectLang.getJSONObject(lang);
            String name    = objectVN.getString("name");
            String address = objectVN.getString("address");
            String course1 = objectVN.getString("course1");
            String course2 = objectVN.getString("course2");
            String course3 = objectVN.getString("course3");
            tv_nd.setText(name + "\n" + address + "\n" + course1 + "\n" + course2 + "\n" + course3);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
