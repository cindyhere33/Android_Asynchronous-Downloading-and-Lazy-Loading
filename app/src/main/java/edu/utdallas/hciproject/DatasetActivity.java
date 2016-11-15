package edu.utdallas.hciproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Sindhura on 11/15/2016.
 */

public class DatasetActivity extends Activity {

    static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataset);
        showLoader();
        if (getIntent().hasExtra("TOPIC")) {
            String topic = getIntent().getStringExtra("TOPIC");
            getTopic(topic);
        }

    }

    private void getTopic(String topic) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getUrl(topic), topicResponseHandler);
    }

    AsyncHttpResponseHandler topicResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String response = new String(responseBody);
            dismissDialog();
            parseResponse(response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            dismissDialog();
            Toast.makeText(DatasetActivity.this, "Sorry there was an error", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        }
    };


    private void showLoader() {
        if (dialog == null)
            dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissDialog();
                Toast.makeText(DatasetActivity.this, "Please check your connection to the internet", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
        try{
            dialog.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String getUrl(String topic) {
        if (topic.contains("INTEL")) return "http://utdallas.edu/~John.Cole/2016Spring/INTC.txt";
        else return "http://utdallas.edu/~John.Cole/2016Spring/MSFT.txt";
    }

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseResponse(String response){
        String[] lines  = response.split("\n");
        for(String line: lines){
            String[] data = line.split(",");


        }
    }

}
