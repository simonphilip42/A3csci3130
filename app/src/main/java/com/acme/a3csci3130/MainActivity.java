package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Franz, modified by Simon Walker, 6/26/2018.
 * Main Activity for the fish-market tracking software,
 * displays a list of businesses stored on a firebase repo.
 * Clicking on the create button will navigate to an interface
 * to create a new business, and clicking on a list entry will
 * navigate to an update/delete interface for the selected business
 */
public class MainActivity extends Activity {


    private ListView businessListView;
    private FirebaseListAdapter<Business> firebaseAdapter;

    /**
     * Runs whenever the activity is created, performs setup tasks for the interface
     * @param savedInstanceState Saved information about the app instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the app wide shared variables
        MyApplicationData appData = (MyApplicationData)getApplication();

        //Set-up Firebase
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("businesses");

        //Get the reference to the UI contents
        businessListView = (ListView) findViewById(R.id.listView);

        //Set up the List View
        firebaseAdapter = new FirebaseListAdapter<Business>(this, Business.class,
                android.R.layout.simple_list_item_1, appData.firebaseReference) {
            @Override
            protected void populateView(View v, Business model, int position) {
                TextView businessName = (TextView)v.findViewById(android.R.id.text1);
                businessName.setText(model.name);
            }
        };
        businessListView.setAdapter(firebaseAdapter);
        businessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * onItemClick method is called every time a user clicks an item on the list
             * Calls the showdetailview method with the selected business
             * @param parent Parent adapter, unused
             * @param view View, unused
             * @param position Position within the list
             * @param id Object id, unused
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Business business_Instance = (Business) firebaseAdapter.getItem(position);
                showDetailView(business_Instance);
            }
        });
    }

    /**
     * Navigates to the business creation interface when a user clicks the create button
     * @param v View, unused
     */
    public void createBusinessButton(View v)
    {
        Intent intent=new Intent(this, CreateBusinessActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the business detail view for the provided business
     * From here a user can update or delete a businesses information
     * @param business_Instance The business to display
     */
    private void showDetailView(Business business_Instance)
    {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("Business", business_Instance);
        startActivity(intent);
    }



}
