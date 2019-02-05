package com.dam.ehedeihega.masacorporal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.ehedeihega.masacorporal.model.DataBaseBMI;
import com.dam.ehedeihega.masacorporal.model.Man;
import com.dam.ehedeihega.masacorporal.model.Person;

import java.util.ArrayList;

public class GetDataActivity extends AppCompatActivity {
    private static final String FIRST_NAME = "fistName";

    private Button buttonSearch;
    private EditText editTextFirstName;
    private WebView webViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        editTextFirstName = findViewById(R.id.edittext_getdata_firstname);
        webViewResults = findViewById(R.id.webview_getdata_results);

        buttonSearch = findViewById(R.id.button_getdata_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillWebView(editTextFirstName.getText().toString());
            }
        });


    }

    private void fillWebView(String firstName) {
        ArrayList<Person> people;
        DataBaseBMI db = DataBaseBMI.getDataBaseBMI(getApplicationContext());
        people = db.getPeople(db.getReadableDatabase(), firstName);
        db.close();

        webViewResults.loadData(getHtml(people), "text/html", "utf-8");
    }


    private String getListHtml(ArrayList<Person> people) {
        StringBuilder listHtml = new StringBuilder();
        String sex;
        if (people.isEmpty()) {
            listHtml.append("<p>" + getString(R.string.getdata_noresults) + "</p>");
        } else {
            for (Person person: people) {
                if (person instanceof Man) {
                    sex = getString(R.string.all_man);
                } else {
                    sex = getString(R.string.all_woman);
                }

                listHtml.append("<ul>\n" +
                                    "\t\t<li>\n" +
                                    getString(R.string.all_firstname) + ": " + person.getFistName() +
                                    "\t\t</li>\n" +
                                    "\t\t<li>\n" +
                                    getString(R.string.all_lastname) + ": " + person.getLastName() +
                                    "\t\t</li>\n" +
                                    "\t\t<li>\n" +
                                    getString(R.string.all_age) + ": " + person.getAge() +
                                    "\t\t</li>\n" +
                                    "\t\t<li>\n" +
                                    getString(R.string.all_height) + ": " + person.getHeight() +
                                    "\t\t</li>\n" +
                                    "\t\t<li>\n" +
                                    getString(R.string.all_weight) + ": " + person.getWeight() +
                                    "\t\t</li>\n" +
                                    "\t\t<li>\n" +
                                    getString(R.string.all_bmi) + ": " + String.format("%.2f", person.getBMI()) +
                                    "\t\t</li>\n" +
                                    "\t\t<li>\n" +
                                    getString(R.string.all_sex) + ": " + sex +
                                    "\t\t</li>\n" +
                                    "\t\t<li>\n" +
                                    getString(R.string.getdata_idealweight) + ": " + String.format("%.2f", person.getIdealWeight()) +
                                    "\t\t</li>\n" +
                                "\t</ul>\n");
            }

        }

        return listHtml.toString();
    }

    private String getHtml(ArrayList<Person> people) {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>" +
                getString(R.string.app_name) +
                "</title>\n" +
                "\t<link rel=\"stylesheet\" href=\"\">\n" +
                "</head>\n" +
                "<body>\n" +
                getListHtml(people) +
                "</body>\n" +
                "</html>";
        return html;
    }

    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FIRST_NAME, editTextFirstName.getText().toString());
    }

    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String name = savedInstanceState.getString(FIRST_NAME);
        editTextFirstName.setText(name);
        fillWebView(name);

    }


}
