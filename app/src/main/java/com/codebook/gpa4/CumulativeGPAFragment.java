package com.codebook.gpa4;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import xyz.hanks.library.bang.SmallBangView;

public class CumulativeGPAFragment extends AppCompatActivity {
    private double total1 = 0.0, total2 = 0.0, total3 = 0.0, hSum1 = 0.0, totalSemGPA =0.0 ,finalGPA=0.0,cum=0.0,sem=0.0,cumH=0.0,semH=0.0;
    private TextView semTextView,cumTV;
    private TextInputEditText cumET,cumHourET,semET,semHourET;
    LinearLayout semesterLinearLayout;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumulative_gpafragment);
        final SmallBangView like_heart = findViewById(R.id.like);
        like_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like_heart.isSelected()) {
                    like_heart.setSelected(false);
                } else {
                    like_heart.setSelected(true);
                    like_heart.likeAnimation(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                           Intent intent=new Intent(CumulativeGPAFragment.this,BreakAds.class);
                            startActivity(intent);


                        }
                    });
                }
            }
        });


        Intent intent=getIntent();
        semTextView=findViewById(R.id.semtextView);
        cumTV=findViewById(R.id.cumTV);

        cumET=(TextInputEditText)findViewById(R.id.cumET);
        semET=(TextInputEditText)findViewById(R.id.semET);
        cumHourET=(TextInputEditText)findViewById(R.id.cumHourET);
        semHourET=(TextInputEditText)findViewById(R.id.semHourET);

        semesterLinearLayout=(LinearLayout) findViewById(R.id.semesterLinearLayout);





        total1=Double.parseDouble(intent.getStringExtra(MainActivity.EXTRA_TOTAL1));
        hSum1 =Double.parseDouble(intent.getStringExtra(MainActivity.EXTRA_HOURS));
        if(hSum1 !=0.0 ) {
            totalSemGPA = total1 / hSum1;

            if(totalSemGPA ==0.0) {
                semesterLinearLayout.setVisibility(View.VISIBLE);
                semTextView.setVisibility(View.GONE);
                flag=false;

            }
            else {
                semesterLinearLayout.setVisibility(View.GONE);
                semTextView.setVisibility(View.VISIBLE);
                String s= String.format(getText(R.string.semester_gpa) +"= %.5s", totalSemGPA)  ;
                semTextView.setText(s);
                flag=true;

            }
        }


    }



    public void calculate(View view) {



        try {
            if(flag==false) {
                cum=Double.parseDouble(cumET.getText().toString());
                cumH=Double.parseDouble(cumHourET.getText().toString());
                sem=Double.parseDouble(semET.getText().toString());
                semH=Double.parseDouble(semHourET.getText().toString());

                total2=cum * cumH;
                total3=sem * semH;
                    double h=cumH + semH;
                if((cumH+semH)!=0) {
                   double GPA = (total2 + total3)/h;
                    cumTV.setText(String.format(getText(R.string.cumulative_gpa) + "=%.5s", GPA));
                }
                else
                {
                    //toast
                }
            }
            else {

                cum=Double.parseDouble(cumET.getText().toString());
                cumH=Double.parseDouble(cumHourET.getText().toString());

                total2=cum*cumH;

                if ((cumH + hSum1) != 0) {
                    finalGPA = (total2 + total1) / (cumH + hSum1);
                    cumTV.setText(String.format(getText(R.string.cumulative_gpa) + "=%.5s", finalGPA));
                }
                else
                {
                    //toast
                }
            }}
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),R.string.emptyFields,Toast.LENGTH_SHORT).show();
        }
/*
        total2 = 0.0; total3 = 0.0;
         totalSemGPA =0.0 ;
         finalGPA=0.0;
         cum=0.0; flag=false;
         sem=0.0;cumH =0.0;
         semH=0.0;*/


    }

    public void back(View view) {
        Intent intent=new Intent(CumulativeGPAFragment.this,MainActivity.class);
        startActivity(intent);
    }
}
