package edu.utdallas.hciproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvIntel = (TextView) findViewById(R.id.tvIntel);
        TextView tvMicrosoft = (TextView) findViewById(R.id.tvMicrosoft);
        tvIntel.setOnClickListener(intelClickListener);
        tvMicrosoft.setOnClickListener(microsoftClickListener);
    }

    View.OnClickListener intelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, DatasetActivity.class);
            intent.putExtra("TOPIC", "INTEL");
            startActivity(intent);
        }
    };

    View.OnClickListener microsoftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, DatasetActivity.class);
            intent.putExtra("TOPIC", "MICROSOFT");
            startActivity(intent);
        }
    };
}
