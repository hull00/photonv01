package com.example.marcin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class AdventureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);

        RelativeLayout FunButton = (RelativeLayout) findViewById(R.id.funLayout);
        FunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdventureActivity.this, FunActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout CompareButton = (RelativeLayout) findViewById(R.id.compareLayout);
        CompareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(AdventureActivity.this, CompareActivity.class);
                //startActivity(intent);
            }
        });

        RelativeLayout ProgressButton = (RelativeLayout) findViewById(R.id.progressLayout);
        ProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(AdventureActivity.this, ProgressActivity.class);
                //startActivity(intent);
            }
        });

        RelativeLayout ChallengeButton = (RelativeLayout) findViewById(R.id.challengeLayout);
        ChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(AdventureActivity.this, ChallengeActivity.class);
                //startActivity(intent);
            }
        });

        RelativeLayout YourPhotonButton = (RelativeLayout) findViewById(R.id.yourPhotonLayout);
        YourPhotonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(AdventureActivity.this, YourPhotonActivity.class);
                //startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adventure, menu);
        return true;
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
