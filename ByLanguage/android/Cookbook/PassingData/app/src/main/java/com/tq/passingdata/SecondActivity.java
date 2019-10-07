package com.tq.passingdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class SecondActivity extends AppCompatActivity {
        
        public static final String FIRST_PARAM = "FIRST";
        public static final String SECOND_PARAM = "SECOND";
        public static final int SUM = 1;
        public static final String RESULT = "RESULT";
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_second);

                Intent intent = getIntent();
                int a = intent.getIntExtra(FIRST_PARAM, 0);
                int b = intent.getIntExtra(SECOND_PARAM, 0);
                final int result = a + b;

                findViewById(R.id.button).setOnClickListener((view) -> {
                                Intent payload = new Intent();
                                payload.putExtra(RESULT, result);
                                setResult(RESULT_OK, payload);
                                finish();
                        });
        }
}
