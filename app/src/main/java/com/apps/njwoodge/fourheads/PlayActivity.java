package com.apps.njwoodge.fourheads;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    long timeLength = 30;
    Random rn = new Random();
    private boolean locked;
    List<String> items = new LinkedList<String>(Arrays.asList(new String[]{"Sokka", "Katara", "Aang", "Appa", "Momo", "Zuko", "Toph", "Azula", "Iroh", "Ozai", "Combustion Man", "King Bumi", "Mai", "Ty Lee", "Suki"}));
    List<String> itemsPlayed = new LinkedList<String>();
    List<Boolean> isCorrect = new LinkedList<Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.topLayout);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getX();
                if(!locked) {
                    if((v.getWidth() / 2) > x) {
                        passCorrect("Correct");
                    } else {
                        passCorrect("Pass");
                    }
                }

                return false;
            }
        });
        itemsPlayed = new LinkedList<String>();
        isCorrect = new LinkedList<Boolean>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        nextItem();
        initTimer();
    }

    private void nextItem() {
        if(items.size() == 0) {
            completeRound();
        }

        int index = rn.nextInt((items.size()));

        TextView item = (TextView)findViewById(R.id.item);
        item.setText(items.get(index));

        items.remove(index);
    }

    private void passCorrect(String text) {
        TextView time = (TextView)findViewById(R.id.time);
        TextView item = (TextView)findViewById(R.id.item);

        if(text.equals("Correct")) {
            itemsPlayed.add((String)item.getText());
            isCorrect.add(true);
        } else {
            itemsPlayed.add((String)item.getText());
            isCorrect.add(false);
        }

        time.setVisibility(View.INVISIBLE);
        item.setVisibility(View.INVISIBLE);

        TextView passCorrect = (TextView)findViewById(R.id.passCorrect);
        passCorrect.setText(text);
        passCorrect.setVisibility(View.VISIBLE);
        final String t = text;
        locked = true;
        new CountDownTimer(1500, 1500) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if(!t.equals("Time")) {
                    nextItem();

                    TextView time = (TextView) findViewById(R.id.time);
                    TextView item = (TextView) findViewById(R.id.item);
                    time.setVisibility(View.VISIBLE);
                    item.setVisibility(View.VISIBLE);

                    TextView passCorrect = (TextView) findViewById(R.id.passCorrect);
                    passCorrect.setVisibility(View.INVISIBLE);
                } else {
                    endRoundActivity();
                }
                locked = false;
            }
        }.start();
    }

    private void initTimer() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                TextView t=(TextView)findViewById(R.id.time);
                t.setText(String.valueOf(millisUntilFinished/1000));
            }

            public void onFinish() {
                completeRound();
            }
        }.start();
    }

    private void completeRound() {
        passCorrect("Time");
    }

    private void endRoundActivity() {
        Object[] i = itemsPlayed.toArray();
        Object[] b = isCorrect.toArray();
        boolean[] b2 = new boolean[b.length];
        String[] i2 = new String[b.length];
        for(int j = 0; j < b.length;j++) {
            b2[j] = (Boolean)b[j];
            i2[j] = i[j].toString();
        }

        Intent intent = new Intent(this, RoundEndActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        intent.putExtra("items", i2);
        intent.putExtra("isCorrect", b2);
        startActivity(intent);
    }

}

