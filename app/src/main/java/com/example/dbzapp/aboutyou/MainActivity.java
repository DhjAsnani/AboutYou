package com.example.dbzapp.aboutyou;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
ProgressBar progressBar;
    TextView t ;
     String em;
    EditText emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progBar);
        emailText = (EditText) findViewById(R.id.emailText);
        t = (TextView) findViewById(R.id.resView);
         em = emailText.getText().toString();
        Log.v("goon",em);

    }
    public void doit(View view)
    {
        Toast.makeText(this,"go "+em,Toast.LENGTH_LONG).show();
                (new RetrieveFeed()).execute();
    }
    class RetrieveFeed extends AsyncTask<Void,Void,String>{
        private Exception exception;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s == null){
                s = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", s);
            t.setText(s);
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... params) {
            String email = em;
            try{
                URL url = new URL("https://api.fullcontact.com/v2/person.json?"+"email="+emailText.getText().toString()+"&apiKey=b0ce8b1f82500ce2");
                Log.v("goon",url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                try{
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine())!=null){
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();;
                    return stringBuilder.toString();
                }finally {
                    urlConnection.disconnect();
                }
            }
            catch (Exception e)
            {
                Log.e("ERR",e.getMessage(),e);
            }
            return null;
        }
    }
}
