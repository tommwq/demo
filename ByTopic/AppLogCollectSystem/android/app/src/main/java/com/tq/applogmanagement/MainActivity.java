package com.tq.applogmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.tq.applogmanagement.LogCollectServiceProtos.LogLevel;
import com.tq.applogmanagement.LogCollectServiceProtos.LogReport;
import com.tq.applogmanagement.LogCollectServiceProtos.LogReportResult;
import com.tq.applogmanagement.LogCollectServiceProtos.MethodParameter;

public class MainActivity extends AppCompatActivity {
    private final LocalLogger logger = new LocalLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MyTask(MainActivity.this).execute();
                }
            });

    }
}
