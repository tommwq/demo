package com.tq.passingdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

        private TextView label;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                label = (TextView) findViewById(R.id.label);
                Button button = (Button) findViewById(R.id.button);
                button.setOnClickListener((view) -> {
                                Intent intent = new Intent(this, SecondActivity.class);
                                intent.putExtra(SecondActivity.FIRST_PARAM, 1);
                                intent.putExtra(SecondActivity.SECOND_PARAM, 2);
                                startActivityForResult(intent, SecondActivity.SUM);
                        });
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent payload) {

                if (requestCode == SecondActivity.SUM) {
                        if (resultCode == RESULT_OK) {
                                int result = payload.getIntExtra(SecondActivity.RESULT, 0);
                                label.setText("Result: " + result);
                        }
                }
        }
}
