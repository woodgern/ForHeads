package com.apps.njwoodge.fourheads;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;

public class RoundEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_end);
        populateItems(getIntent());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.back:
                newGame();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newGame() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    private void populateItems(Intent intent) {
        Bundle b = intent.getExtras();
        String[] items = b.getStringArray("items");
        boolean[] isCorrect = b.getBooleanArray("isCorrect");
        if(items == null) {
            System.out.println("NULLLLLLLLLLLLLLLLLLLLLLL");
            return;
        }


        int count = 0;
        for(int i = 0; i < items.length;i++) {
            ViewGroup layout = (ViewGroup) findViewById(R.id.lLayout);
            TextView tv = new TextView(this);
            tv.setText(items[i]);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(40);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(tv);

            if(isCorrect[i]) {
                count++;
            } else {
                tv.setTextColor(Color.rgb(200,0,0));
            }
        }
        TextView number = (TextView)findViewById(R.id.correctNumber);
        number.setText(String.valueOf(count));
    }

    public void toMenu(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
