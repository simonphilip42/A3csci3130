package com.acme.a3csci3130;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FishBusinessUITests{
    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    public static boolean setupFlag = false;

    private Business goodInput;
    private String goodName;
    private String badName;
    private String badNumber;
    private String badPrimary;
    private String badAddress;
    private String badPT;

    /**
     * Run before each test
     */
    @Before
    public void reset(){
        badName = String.join("", Collections.nCopies(3, "More than 48 Chars,"));
        badNumber = "1";
        badAddress = String.join("", Collections.nCopies(3, "More than 50 Chars,"));
        badPT = "Montana";
        badPrimary = "Farmer";

        goodName = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());

        goodInput = new Business();
        //THis is so that if the delete test fails, further tests won't use the same name
        goodInput.name = goodName;
        goodInput.primary_Business = "Distributor";
        goodInput.business_Number = "123456789";
        goodInput.address = "Neptune's Bounty, Rapture";
        goodInput.province_Territory = "NS";
    }

    /**
     * Tests inputting bad data, which will be rejected when trying to create a new Business
     */
    @Test
    public void Test1BadCreateRecord() {
        onView(withId(R.id.createButton)).perform(click());

        onView(withId(R.id.nameCreate)).perform(typeText(badName), closeSoftKeyboard());
        onView(withId(R.id.businessNumberCreate)).perform(typeText(badNumber), closeSoftKeyboard());
        onView(withId(R.id.primaryBusinessCreate)).perform(typeText(badPrimary), closeSoftKeyboard());
        onView(withId(R.id.addressCreate)).perform(typeText(badAddress), closeSoftKeyboard());
        onView(withId(R.id.provinceTerritoryCreate)).perform(typeText(badPT), closeSoftKeyboard());

        onView(withId(R.id.submitButton)).perform(click());

        //This will fail if the create window is still open
        try{
            onView(withId(R.id.nameCreate)).perform(click());
        }
        catch(NoMatchingViewException e){
            assert(false);
        }
    }

    /**
     * Tests creating a record with good data, which will function properly
     */
    @Test
    public void Test2CreateRecord(){
        onView(withId(R.id.createButton)).perform(click());

        onView(withId(R.id.nameCreate)).perform(typeText(goodInput.name), closeSoftKeyboard());
        onView(withId(R.id.businessNumberCreate)).perform(typeText(goodInput.business_Number), closeSoftKeyboard());
        onView(withId(R.id.primaryBusinessCreate)).perform(typeText(goodInput.primary_Business), closeSoftKeyboard());
        onView(withId(R.id.addressCreate)).perform(typeText(goodInput.address), closeSoftKeyboard());
        onView(withId(R.id.provinceTerritoryCreate)).perform(typeText(goodInput.province_Territory ), closeSoftKeyboard());

        onView(withId(R.id.submitButton)).perform(click());

        //This will fail if the create window is still open
        try{
            onView(withId(R.id.nameCreate)).perform(click());
            assert(false);
        }
        catch(NoMatchingViewException e){
        }
    }

    /**
     * Tests that the good data that was just inputted appears in the list
     */
    @Test
    public void Test3ReadRecord(){
        ListView list = (ListView) testRule.getActivity().findViewById(R.id.listView);
        FirebaseListAdapter<Business> listAdapter = (FirebaseListAdapter<Business>) list.getAdapter();
        Business listEntry = listAdapter.getItem(listAdapter.getCount() - 1);

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(listAdapter.getCount() - 1).check(matches(withText(containsString(goodInput.name))));
    }

    /**
     * Tests that updating the last entry in the list with bad data will be refused
     */
    @Test
    public void Test4BadUpdateRecord(){
        ListView list = (ListView) testRule.getActivity().findViewById(R.id.listView);
        FirebaseListAdapter<Business> listAdapter = (FirebaseListAdapter<Business>) list.getAdapter();
        Business listEntry = listAdapter.getItem(listAdapter.getCount() - 1);

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(listAdapter.getCount() - 1).perform((click()));

        onView(withId(R.id.nameUpdate)).perform(typeText(badName), closeSoftKeyboard());

        onView(withId(R.id.updateButton)).perform(click());

        //This will fail if the update window is still open
        try{
            onView(withId(R.id.nameUpdate)).perform(click());
        }
        catch(NoMatchingViewException e){
            assert(false);
        }
    }

    /**
     * Tests that the app can update a record properly
     */
    @Test
    public void Test5UpdateRecord(){
        ListView list = (ListView) testRule.getActivity().findViewById(R.id.listView);
        FirebaseListAdapter<Business> listAdapter = (FirebaseListAdapter<Business>) list.getAdapter();
        Business listEntry = listAdapter.getItem(listAdapter.getCount() - 1);

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(listAdapter.getCount() - 1).perform((click()));

        onView(withId(R.id.nameUpdate)).perform(typeText("New name"), closeSoftKeyboard());

        onView(withId(R.id.updateButton)).perform(click());

        //This will fail if the update window is still open
        try{
            onView(withId(R.id.nameUpdate)).perform(click());
            assert(false);
        }
        catch(NoMatchingViewException e){
        }
    }

    /**
     * Tests that the app can delete a business properly
     */
    @Test
    public void Test6DeleteRecord(){
        ListView list = (ListView) testRule.getActivity().findViewById(R.id.listView);
        FirebaseListAdapter<Business> listAdapter = (FirebaseListAdapter<Business>) list.getAdapter();
        Business listEntry = listAdapter.getItem(listAdapter.getCount() - 1);

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(listAdapter.getCount() - 1).perform((click()));

        onView(withId(R.id.deleteButton)).perform(click());

        if(listAdapter.getCount() != 0){
            onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(listAdapter.getCount() - 1).check(matches(not(withText(containsString(goodInput.name)))));
        }
    }
}
