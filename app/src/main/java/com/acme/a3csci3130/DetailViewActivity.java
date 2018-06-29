package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class DetailViewActivity extends Activity {

    private EditText nameField, primaryField, numberField, addressField, ptField;
    Business receivedBusinessInfo;
    private MyApplicationData appState;

    /**
     * OnCreate, called anytime this activity is created. Performs setup for
     * this activities lifecycle
     * @param savedInstanceState Saved information about the activities instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedBusinessInfo = (Business)getIntent().getSerializableExtra("Business");
        appState = ((MyApplicationData) getApplicationContext());

        //Creating local references to the interface elements
        nameField = (EditText) findViewById(R.id.nameUpdate);
        primaryField = (EditText) findViewById(R.id.primaryBusinessUpdate);
        numberField = (EditText) findViewById(R.id.businessNumberUpdate);
        addressField = (EditText) findViewById(R.id.addressUpdate);
        ptField = (EditText) findViewById(R.id.provinceTerritoryUpdate);

        if(receivedBusinessInfo != null){
            nameField.setText(receivedBusinessInfo.name);
            primaryField.setText(receivedBusinessInfo.primary_Business);
            numberField.setText(receivedBusinessInfo.business_Number);
            addressField.setText(receivedBusinessInfo.address);
            ptField.setText(receivedBusinessInfo.province_Territory);
        }
    }

    /**
     * Updates a business stored on the external database
     * @param v View, unused
     */
    public void updateBusiness(View v){
        String name = nameField.getText().toString();
        String primary_Business = primaryField.getText().toString();
        String business_Number = numberField.getText().toString();
        String address = addressField.getText().toString();
        String province_Territory = ptField.getText().toString();
        Business updatedBusiness = new Business(receivedBusinessInfo.db_ID, business_Number, name, primary_Business, address, province_Territory);

        appState.firebaseReference.child(updatedBusiness.db_ID).setValue(updatedBusiness, new DatabaseReference.CompletionListener() {
            /**
             * Handles the return of the database operation
             * @param databaseError The error, if there is one
             * @param databaseReference The relevant database entry
             */
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), "Inputted data doesn't pass validation.", Toast.LENGTH_LONG).show();
                }
                else{
                    finish();
                }
            }
        });

        receivedBusinessInfo = updatedBusiness;
    }

    /**
     * Deletes a business from the external database
     * @param v The view, unused
     */
    public void eraseBusiness(View v)
    {
        appState.firebaseReference.child(receivedBusinessInfo.db_ID).removeValue();
        finish();
    }
}
