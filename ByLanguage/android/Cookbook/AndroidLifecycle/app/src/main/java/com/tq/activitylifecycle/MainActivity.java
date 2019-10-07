package com.tq.activitylifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                toast("onCreate");
        }

        private void toast(String message) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onStart() {
                toast("onStart");
                super.onStart();
        }

        @Override
        protected void onResume() {
                toast("onResume");
                super.onResume();
        }

        @Override
        protected void onRestart() {
                toast("onRestart");
                super.onRestart();
        }

        @Override
        protected void onPause() {
                toast("onPause");
                super.onPause();
        }

        @Override
        protected void onStop() {
                toast("onStop");
                super.onStop();
        }

        @Override
        protected void onDestroy() {
                toast("onDestroy");
                super.onDestroy();
        }

}
