package com.codebook.gpa4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    protected final Double [] sciencePoints = {0.0, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0};
    protected final Double [] compPoints = {0.0, 2.0, 2.2, 2.4, 2.6, 2.8, 3.0, 3.2,3.4,3.6,3.8,4.0};
    protected final Double []Hours = {4.0, 3.0, 2.0, 1.0};
    protected final String []faculty={"Science","BFCI"};
    private double []pnts = new double[8];
    private double []hours = new double[8];
    private TextView gpaText,resultTextView;
    private String []getUndo = new String[8];
    private int i = 0;
    private double total1 = 0.0, hSum = 0.0,totalGPA=0.0;
    public static final String EXTRA_TOTAL1="android.example.gpa4.total1" ;
    public static final String EXTRA_PNTS="android.example.gpa4.points1" ;
    public static final String EXTRA_HOURS="android.example.gpa4.hours1" ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //faculty spinner
        ArrayAdapter<String> adapter0 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, faculty);
        final Spinner fspinner = findViewById(R.id.fSpinner);
        fspinner.setAdapter(adapter0);

        final ArrayAdapter<Double> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sciencePoints);
        final ArrayAdapter<Double> adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, compPoints);
        final Spinner spinner1 = findViewById(R.id.pntsSpinner);
        if(fspinner.getSelectedItem().equals("Science"))
        spinner1.setAdapter(adapter);
        else
            spinner1.setAdapter(adapter3);
        fspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(fspinner.getSelectedItem().equals("Science"))
                    spinner1.setAdapter(adapter);
                else
                    spinner1.setAdapter(adapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<Double> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Hours);
        Spinner spinner2 = findViewById(R.id.hoursSpinner);
        spinner2.setAdapter(adapter2);
        gpaText = (TextView) findViewById(R.id.gpaTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);


    }

    public void add(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.blink_anim);
        view.startAnimation(animation);
        Spinner spinner1 = findViewById(R.id.pntsSpinner);
        Spinner spinner2 = findViewById(R.id.hoursSpinner);
        if (i < 8) {

            pnts[i] = (double) spinner1.getSelectedItem();
            hours[i] = (double) spinner2.getSelectedItem();
            //التغيير في السطرين دول
            getUndo[i] = "#" + (i + 1) + " Hours:[" + hours[i] + "] Points:[" + pnts[i] + "]\n";
            resultTextView.append(getUndo[i]);
            /**/
            i++;

        } else
            showToast();
    }

    public void reset(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        view.startAnimation(animation);
        i = 0;
        resultTextView.setText("");
        gpaText.setText("");
        total1 = 0.0;
        hSum = 0.0;
        totalGPA=0.0;

    }

    public void undo(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.lefttoright);
        view.startAnimation(animation);
        if (i > 0) {
            i--;
            //التغيير في السطرين دول
            resultTextView.setText("");
            for (int j = 0; j <= i - 1; j++)
                resultTextView.append(getUndo[j]);

        } else if (i == 0)
            resultTextView.setText("");
        /**/
    }

    public void calculate(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        view.startAnimation(animation);
        total1 = 0.0;
        hSum = 0.0;
        if (i == 0) {
            showToast1();
        } else {
            for (int j = 0; j <= i - 1; j++) {
                total1 += pnts[j] * hours[j];
                hSum += hours[j];
            }
             totalGPA = total1 / hSum;
            gpaText.setText(String.format(getText(R.string.semester_gpa)+"=%.5s" ,totalGPA ));

            /**/
        }
    }

    public void showToast() {


        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast));
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public void showToast1() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast1, (ViewGroup) findViewById(R.id.toast1));
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public void intent(View view) {
        showAlert();
    }
    public void showAlert()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("").setMessage(getText(R.string.message)).setPositiveButton(getText(R.string.Lets_go), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(MainActivity.this,CumulativeGPAFragment.class);

                intent.putExtra(EXTRA_TOTAL1,total1+"");
                intent.putExtra(EXTRA_PNTS,totalGPA+"");
                intent.putExtra(EXTRA_HOURS,hSum+"");
                startActivity(intent);
            }
        }).setNegativeButton(getText(R.string.stay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

}
