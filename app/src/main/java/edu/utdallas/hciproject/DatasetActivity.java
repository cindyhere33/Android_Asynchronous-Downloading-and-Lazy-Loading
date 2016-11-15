package edu.utdallas.hciproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Sindhura on 11/15/2016.
 */

public class DatasetActivity extends Activity {

    static ProgressDialog dialog;
    ListView lvData;
    DatasetAdapter adapter;
    List<String[]> dataLines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataset);
        lvData = (ListView) findViewById(R.id.lvDataset);
        showLoader();
        if (getIntent().hasExtra("TOPIC")) {
            String topic = getIntent().getStringExtra("TOPIC");
            getTopic(topic);
        }
        adapter = new DatasetAdapter(DatasetActivity.this, R.layout.row, dataLines);
        lvData.setAdapter(adapter);
    }

    private void getTopic(String topic) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getUrl(topic), topicResponseHandler);
    }

    AsyncHttpResponseHandler topicResponseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String response = new String(responseBody);
            Log.d(getClass().getName(), response);
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
                if (dialog != null && dialog.isShowing()) {
                    dismissDialog();
                    Toast.makeText(DatasetActivity.this, "Please check your connection to the internet", Toast.LENGTH_SHORT).show();
                }
            }
        }, 5000);
        try {
            dialog.show();
        } catch (Exception e) {
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

    private void parseResponse(String response) {
        String[] lines = response.split("\n");
        dataLines.clear();
        for (String line : lines) {
            String[] data = line.split(",");
            dataLines.add(data);
        }
        adapter.setDataset(dataLines);
        adapter.notifyDataSetChanged();

    }

}
