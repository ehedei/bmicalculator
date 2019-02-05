package com.dam.ehedeihega.masacorporal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.ehedeihega.masacorporal.model.DataBaseBMI;
import com.dam.ehedeihega.masacorporal.model.Person;
import com.dam.ehedeihega.masacorporal.utilityclass.ActivityHelper;


public class SaveDataFragment extends Fragment {
    private static final String BUTTON_ENABLED = "buttonEnabled";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    private Button buttonSaveConfirmation;
    private EditText editTextFirstName, editTextLastName;
    private Person person;
    private View fragmentView;

    public SaveDataFragment() {
        // Required empty public constructor
    }

 public static SaveDataFragment newInstance(Person person) {
        SaveDataFragment fragment = new SaveDataFragment();
        Bundle args = new Bundle();
        args.putParcelable(ActivityHelper.KEY_PERSON, (Parcelable) person);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        person = getArguments().getParcelable(ActivityHelper.KEY_PERSON);

        fragmentView = inflater.inflate(R.layout.fragment_save_data, container, false);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonSaveConfirmation = fragmentView.findViewById(R.id.button_savedata_saveconfirmation);
        editTextFirstName = fragmentView.findViewById(R.id.edittext_savedata_firstname);
        editTextLastName = fragmentView.findViewById(R.id.edittext_savedata_lastname);

        if (savedInstanceState != null) {
            buttonSaveConfirmation.setEnabled(savedInstanceState.getBoolean(BUTTON_ENABLED));
        }


        buttonSaveConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextFirstName.getText()) ||
                        TextUtils.isEmpty(editTextLastName.getText())
                        )
                    Toast.makeText(getContext(), R.string.all_errorform, Toast.LENGTH_SHORT).show();

                else {
                    person.setFistName(editTextFirstName.getText().toString());
                    person.setLastName(editTextLastName.getText().toString());

                    buttonSaveConfirmation.setEnabled(false);

                    new SaveDataThread(person, getContext()).run();


                }
            }
        });


    }



    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUTTON_ENABLED, buttonSaveConfirmation.isEnabled());
        outState.putString(FIRST_NAME, editTextFirstName.getText().toString());
        outState.putString(LAST_NAME, editTextLastName.getText().toString());
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        editTextFirstName.setText(savedInstanceState.getString(FIRST_NAME));
        editTextLastName.setText(savedInstanceState.getString(FIRST_NAME));
    }



    private class SaveDataThread implements Runnable {
        private Person person;
        private Context context;

        SaveDataThread (Person person, Context context) {
            this.person = person;
            this.context = context;
        }


        @Override
        public void run() {
            DataBaseBMI db = DataBaseBMI.getDataBaseBMI(context);
            db.insertPerson(db.getWritableDatabase(), person);
            db.close();

            Toast.makeText(context, R.string.savedata_confirmation, Toast.LENGTH_SHORT).show();
        }
    }



}
