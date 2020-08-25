package android.example.gpa4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.codebook.gpa4.R;

public class MainActivity extends AppCompatActivity {
    protected final Double points[] = {0.0, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0};
    protected final Double Hours[] = {4.0, 3.0, 2.0, 1.0};
    private double pnts[] = new double[8];
    private double hours[] = new double[8];
    /*تغيير*/
    private String getUndo[] = new String[8];
    private String recent = "";
    /**/
    private int i = 0;
    private double total1 = 0.0, hSum = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<Double> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, points);
        Spinner spinner1 = findViewById(R.id.pntsSpinner);
        spinner1.setAdapter(adapter);

        ArrayAdapter<Double> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Hours);
        Spinner spinner2 = findViewById(R.id.hoursSpinner);
        spinner2.setAdapter(adapter2);


    }

    public void add(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.blink_anim);
        view.startAnimation(animation);
        Spinner spinner1 = findViewById(R.id.pntsSpinner);
        Spinner spinner2 = findViewById(R.id.hoursSpinner);
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        Button button = findViewById(R.id.deleteButton);


        if (i < 8) {

            pnts[i] = (double) spinner1.getSelectedItem();
            hours[i] = (double) spinner2.getSelectedItem();
            //التغيير في السطرين دول
            getUndo[i] = "#" + (i + 1) + " Hours:[" + hours[i] + "] Points:[" + pnts[i] + "]\n";
            resultTextView.append(getUndo[i]);
            /**/
            i++;

        } else
            Toast.makeText(getApplicationContext(), " You can't add more than 8 Courses.", Toast.LENGTH_SHORT).show();
    }

    public void reset(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        view.startAnimation(animation);
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        i = 0;
        resultTextView.setText("");
        recent = "";
        total1 = 0.0;
        hSum = 0.0;

    }

    public void undo(View view) {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.lefttoright);
        view.startAnimation(animation);
        Button button = findViewById(R.id.deleteButton);
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

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
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        total1 = 0.0;
        hSum = 0.0;
        if (i == 0) {
            //التغيير في السطرين دول
            Toast.makeText(getApplicationContext(), "Select Hours and Points.", Toast.LENGTH_SHORT).show();

            return;
        } else {
            for (int j = 0; j <= i - 1; j++) {
                total1 += pnts[j] * hours[j];
                hSum += hours[j];
            }
            double total = total1 / hSum;
            resultTextView.append("\nYour GPA=" + total);
            /**/
        }
    }
}
