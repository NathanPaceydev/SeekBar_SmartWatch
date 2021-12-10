package com.example.seekbartest;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final int MAXTIME = 5000;
    private SeekBar seekBar;
    private TextView textView, textView1, textView2,textView3;
    private boolean direction = true;
    private float prog = 0;
    private float progPrior = 0;
    //Resources res = getResources();
    //Drawable shape = ResourcesCompat.getDrawable(res, R.drawable.custom_thumb, getTheme());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Drawable thumb = getResources().getDrawable(R.drawable.custom_thumb);
        Drawable thumb1 = getResources().getDrawable(R.drawable.custom_thumb1);
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setThumb(thumb1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int lastprogress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prog = progress;
                if(direction) {
                    //seekBar.setThumb(thumb1);
                    if (progress < lastprogress) {
                        seekBar.setProgress(lastprogress);
                    } else {
                        lastprogress = progress;
                        //direction = !direction;
                    }
                }
                else{
                    //seekBar.setThumb(thumb);
                    if (progress > lastprogress) {
                        seekBar.setProgress(lastprogress);
                    } else {
                        lastprogress = progress;
                        //direction = !direction;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(prog != progPrior){
                    textView.setText("Started");
                }
             //   textView2.setText("" + 0);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(prog != progPrior){
                    direction = !direction;
                    String movingText = "Moving to " + (int)((abs(prog)/4*100)) + "%";
                    textView.setText(movingText);
                    //seekBar.setThumb(thumb);
                }
                //textView3.setText("" + direction);
                //textView1.setText(""+ (int)(abs(prog-progPrior)/4*100) + "");
                seekBar.setEnabled(false);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Ready");
                       // textView2.setText("" + (long) (MAXTIME*((abs(prog-progPrior)/4))));
                        seekBar.getProgressDrawable().setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
                        progPrior = prog;
                        seekBar.setEnabled(true);
                        if(direction){
                            seekBar.setThumb(thumb1);
                            seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.magnaBlue), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                        else{
                            seekBar.setThumb(thumb);
                            seekBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                    }
                }, (long) (MAXTIME*((abs(prog-progPrior)/4))));

                //progPrior = prog;
                //   runit((int)(prog/4));
            }
        });


    }

        public void runit(int mf){
            new java.util.Timer().schedule(
                    new java.util.TimerTask(){
                        @Override
                        public void run(){
                            textView.setText("Ended");
                        }
                    },
                    (long) (MAXTIME*((abs(prog-progPrior)/4)))
            );
            }
}