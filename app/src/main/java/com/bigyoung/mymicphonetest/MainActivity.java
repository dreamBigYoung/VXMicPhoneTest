package com.bigyoung.mymicphonetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.bigyoung.mymicphonetest.utils.MediaRecorderUtils;
import com.bigyoung.mymicphonetest.view.FloatingVolume;

public class MainActivity extends AppCompatActivity {

    public MediaRecorderUtils mRecorderUtils;
    public View mShowArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecorderUtils = new MediaRecorderUtils();
        Button clickBtn = (Button)findViewById(R.id.hello);
        FloatingVolume micView =(FloatingVolume)findViewById(R.id.mic_view);
        mShowArea = findViewById(R.id.show_area);
        mRecorderUtils.setFractionListener(micView);
        clickBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mRecorderUtils.startRecord();
                        mShowArea.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        mRecorderUtils.stopRecord();
                        mShowArea.setVisibility(View.GONE);
                        break;
                }

                return false;
            }
        });

    }
}
