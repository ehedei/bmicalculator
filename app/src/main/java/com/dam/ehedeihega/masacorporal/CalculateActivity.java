package com.dam.ehedeihega.masacorporal;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dam.ehedeihega.masacorporal.model.Man;
import com.dam.ehedeihega.masacorporal.model.Person;
import com.dam.ehedeihega.masacorporal.model.Woman;
import com.dam.ehedeihega.masacorporal.utilityclass.ActivityHelper;

public class CalculateActivity extends AppCompatActivity {
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String AGE = "age";
    private static final String SEX = "sex";


    private EditText editTextHeight, editTextWeight, editTextAge;
    private RadioGroup radioGroupSex;
    private Button buttonCalculate;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        editTextAge = findViewById(R.id.edittext_calculate_age);
        editTextHeight = findViewById(R.id.edittext_calculate_height);
        editTextWeight = findViewById(R.id.edittext_calculate_weight);
        radioGroupSex = findViewById(R.id.radiogroup_calculate_sex);
        buttonCalculate = findViewById(R.id.button_calculate_calculate);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextHeight.getText()) ||
                        TextUtils.isEmpty(editTextAge.getText()) ||
                        TextUtils.isEmpty(editTextWeight.getText()) ||
                        radioGroupSex.getCheckedRadioButtonId() == -1
                        )
                    Toast.makeText(getApplicationContext(), R.string.all_errorform, Toast.LENGTH_SHORT).show();

                else {
                    String sex;
                    int age = Integer.parseInt(editTextAge.getText().toString());
                    double weight = Double.parseDouble(editTextWeight.getText().toString());
                    double height = Double.parseDouble(editTextHeight.getText().toString());


                    if (radioGroupSex.getCheckedRadioButtonId() == R.id.radiobutton_calculate_man) {
                        sex = getResources().getString(R.string.all_man);
                        person = new Man(age, height, weight, sex);
                    } else {
                        sex = getResources().getString(R.string.all_woman);
                        person = new Woman(age, height, weight, sex);
                    }

                    Intent intent = new Intent(getApplicationContext(), SaveDataActivity.class);
                    intent.putExtra(ActivityHelper.KEY_PERSON, (Parcelable) person);
                    startActivity(intent);
                }

            }
        });



    }


    //Métodos para no perder los datos introducidos en formulario si se recrea la activity
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(HEIGHT, editTextHeight.getText().toString());
        outState.putString(WEIGHT, editTextWeight.getText().toString());
        outState.putString(AGE, editTextAge.getText().toString());
        outState.putInt(SEX, radioGroupSex.getCheckedRadioButtonId());

    }

    //Se ejecuta tras el método onStart()
    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        editTextHeight.setText(savedInstanceState.getString(HEIGHT));
        editTextAge.setText(savedInstanceState.getString(AGE));
        editTextWeight.setText(savedInstanceState.getString(WEIGHT));
        radioGroupSex.check(savedInstanceState.getInt(SEX));
    }


}
