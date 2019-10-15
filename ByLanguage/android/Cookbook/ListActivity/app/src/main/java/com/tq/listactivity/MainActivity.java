package com.tq.listactivity;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

        private static final String[] Choices = new String[]{
                "Open Website Example",
                "Open Contacts",
                "Open Phone Dialer Example",
                "Search Google Example",
                "Start Voice Command"
        };

        private final String searchTerms = "superman";

        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setListAdapter(new ArrayAdapter<String>(this,
                                                        android.R.layout.simple_list_item_1,
                                                        Choices));
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                getListView().setTextFilterEnabled(true);
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View view, int position, long x) {
                                        
                                }
                        });
        }

}
