package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailViewActivity extends Activity {

    private EditText nameField, primaryField, numberField, addressField, ptField;
    Business receivedBusinessInfo;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedBusinessInfo = (Business)getIntent().getSerializableExtra("Business");
        appState = ((MyApplicationData) getApplicationContext());

        //Creating local references to the interface elements
        nameField = (EditText) findViewById(R.id.name);
        primaryField = (EditText) findViewById(R.id.primaryBusiness);
        numberField = (EditText) findViewById(R.id.businessNumber);
        addressField = (EditText) findViewById(R.id.address);
        ptField = (EditText) findViewById(R.id.provinceTerritory);

        if(receivedBusinessInfo != null){
            nameField.setText(receivedBusinessInfo.name);
            primaryField.setText(receivedBusinessInfo.primary_Business);
            numberField.setText(receivedBusinessInfo.business_Number);
            addressField.setText(receivedBusinessInfo.address);
            ptField.setText(receivedBusinessInfo.province_Territory);
        }
    }

    public void updateBusiness(View v){
        String name = nameField.getText().toString();
        String primary_Business = primaryField.getText().toString();
        String business_Number = numberField.getText().toString();
        String address = addressField.getText().toString();
        String province_Territory = ptField.getText().toString();
        Business updatedBusiness = new Business(receivedBusinessInfo.db_ID, business_Number, name, primary_Business, address, province_Territory);

        appState.firebaseReference.child(updatedBusiness.db_ID).setValue(updatedBusiness);
        receivedBusinessInfo = updatedBusiness;
    }

    public void eraseBusiness(View v)
    {
        appState.firebaseReference.child(receivedBusinessInfo.db_ID).removeValue();
        finish();
    }
}
