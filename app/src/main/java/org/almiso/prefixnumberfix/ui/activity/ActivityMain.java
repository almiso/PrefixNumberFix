package org.almiso.prefixnumberfix.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.almiso.prefixnumberfix.R;

public class ActivityMain extends AppCompatActivity {

    private AppCompatButton btnViewContacts;
    private AppCompatButton btnFixContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btnViewContacts = (AppCompatButton) findViewById(R.id.btnViewContacts);
        btnViewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, ActivityContacts.class));
            }
        });
        btnFixContacts = (AppCompatButton) findViewById(R.id.btnFixContacts);

    }


}
