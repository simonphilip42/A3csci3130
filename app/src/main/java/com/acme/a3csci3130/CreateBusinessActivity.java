package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateBusinessActivity extends Activity {

    private Button submitButton;
    private EditText nameField, primaryField, numberField, addressField, ptField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business_activity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        //Creating local references to the interface elements
        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        primaryField = (EditText) findViewById(R.id.primaryBusiness);
        numberField = (EditText) findViewById(R.id.businessNumber);
        addressField = (EditText) findViewById(R.id.address);
        ptField = (EditText) findViewById(R.id.provinceTerritory);
    }

    public void submitInfoButton(View v) {
        //each entry needs a unique ID

        String db_ID = appState.firebaseReference.push().getKey();

        String name = nameField.getText().toString();
        String primary_Business = primaryField.getText().toString();
        String business_Number = numberField.getText().toString();
        String address = addressField.getText().toString();
        String province_Territory = ptField.getText().toString();
        Business created_Business = new Business(db_ID, business_Number, name, primary_Business, address, province_Territory);

        appState.firebaseReference.child(db_ID).setValue(created_Business);

        finish();
    }
}
