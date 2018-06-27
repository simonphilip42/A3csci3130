package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.espresso.matcher.ViewMatchers;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;

import java.util.Collections;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FishBusinessUITests{
    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);


    private Business goodInput;
    private String badName;
    private String badNumber;
    private String badPrimary;
    private String badAddress;
    private String badPT;

    @BeforeClass
    private void setup(){
        badName = String.join("", Collections.nCopies(3, "More than 48 Chars,"));
        badNumber = "1";
        badAddress = String.join("", Collections.nCopies(3, "More than 50 Chars,"));
        badPT = "Montana";
        badPrimary = "Farmer";

        reset();
    }

    private void reset(){
        goodInput = new Business();
        goodInput.name = "Fontaine Fisheries";
        goodInput.primary_Business = "Distributor";
        goodInput.business_Number = "123456789";
        goodInput.address = "Neptune's Bounty, Rapture";
        goodInput.province_Territory = "NS";
    }


    @Test
    public void CreateRecord() throws Exception {
        reset();
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.name)).perform(typeText(goodInput.name), closeSoftKeyboard());
        onView(withId(R.id.primaryBusiness)).perform(typeText(goodInput.primary_Business), closeSoftKeyboard());
        onView(withId(R.id.businessNumber)).perform(typeText(goodInput.business_Number), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText(goodInput.address), closeSoftKeyboard());
        onView(withId(R.id.provinceTerritory)).perform(typeText(goodInput.province_Territory ), closeSoftKeyboard());

        onView(withId(R.id.submitButton)).perform(click());

        ListView list = (ListView) testRule.getActivity().findViewById(R.id.listView);
        FirebaseListAdapter<Business> listAdapter = (FirebaseListAdapter<Business>) list.getAdapter();
    }
}
