package com.dam.ehedeihega.masacorporal;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;


import com.dam.ehedeihega.masacorporal.model.Person;
import com.dam.ehedeihega.masacorporal.utilityclass.ActivityHelper;

public class SaveDataActivity extends AppCompatActivity {
    private Button buttonBack, buttonSave;
    private WebView webViewShowData;
    private Person person;
    private FrameLayout frameLayoutForm;
    private SaveDataFragment fragmentSaveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //Mueve las vistas para que el elemento con foco sea visible pese al Input (Teclado)

        frameLayoutForm = findViewById(R.id.frame_savedata_form);

        person = getIntent().getParcelableExtra(ActivityHelper.KEY_PERSON);

        webViewShowData = findViewById(R.id.webview_savedata_showdata);
        webViewShowData.loadData(getHTML(person), "text/html", "utf-8");

        buttonBack = findViewById(R.id.button_savedata_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSave = findViewById(R.id.button_savedata_save);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentSaveData = SaveDataFragment.newInstance(person);

                fragmentTransaction.add(R.id.frame_savedata_form, fragmentSaveData);
                fragmentTransaction.commit();

                buttonSave.setVisibility(View.GONE);
            }
        });


    }

    //Devuelve el HTML para el WebView
    private String getHTML (Person person) {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>" +
                getResources().getString(R.string.app_name) +
                "</title>\n" +
                "\t<link rel=\"stylesheet\" href=\"\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<ul>\n" +
                "\t\t<li>\n" +
                getResources().getString(R.string.all_age) + ": " + person.getAge() +
                "\t\t</li>\n" +
                "\t\t<li>\n" +
                getResources().getString(R.string.all_height) + ": " + person.getHeight() +
                "\t\t</li>\n" +
                "\t\t<li>\n" +
                getResources().getString(R.string.all_sex) + ": " + person.getSex() +
                "\t\t</li>\n" +
                "\t\t<li>\n" +
                getResources().getString(R.string.all_weight) + ": " + person.getWeight() +
                "\t\t</li>\n" +
                "\t\t<li>\n" +
                getResources().getString(R.string.all_bmi) + ": " + String.format("%.2f", person.getBMI()) +
                "\t\t</li>\n" +
                "\t</ul>\n" +
                "</body>\n" +
                "</html>";

        return html;
    }



    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        if (frameLayoutForm.getChildCount() > 0) {
            buttonSave.setVisibility(View.GONE);
        }

    }



}
