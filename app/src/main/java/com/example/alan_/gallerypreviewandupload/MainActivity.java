package com.example.alan_.gallerypreviewandupload;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imgPrev1, imgPrev2, imgPrev3;
    ImageView imgPrevSupr1, imgPrevSupr2, imgPrevSupr3;
    ImageView imgPreview;
    CardView cardPrev1, cardPrev2, cardPrev3;
    Button btnUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPrev1=findViewById(R.id.imgPrev1);
        imgPrev2=findViewById(R.id.imgPrev2);
        imgPrev3=findViewById(R.id.imgPrev3);

        imgPrevSupr1=findViewById(R.id.imgPrevSupr1);
        imgPrevSupr2=findViewById(R.id.imgPrevSupr2);
        imgPrevSupr3=findViewById(R.id.imgPrevSupr3);

        cardPrev1=findViewById(R.id.cardPrev1);
        cardPrev2=findViewById(R.id.cardPrev2);
        cardPrev3=findViewById(R.id.cardPrev3);

        btnUpload=findViewById(R.id.btnUpload);

        imgPreview=findViewById(R.id.imgPreview);

        /*imgPrev1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnUpload.setBackgroundColor(Color.WHITE);
                return false;
            }
        });*/
        imgPrev1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imgPreview.setImageResource(R.drawable.vegetta);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imgPreview.setImageResource(0);
                }
                return true;
            }
        });
        imgPrevSupr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardPrev1.getLayoutParams().width=1;
                cardPrev1.getLayoutParams().height=1;
                cardPrev1.setVisibility(View.INVISIBLE);
            }
        });
    }
}
