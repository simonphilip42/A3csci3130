package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase database. This is converted to a JSON format
 */

public class Business implements Serializable {

    public String db_ID;
    public String business_Number;
    public String name;
    public String primary_Business;
    public String address;
    public String province_Territory;

    /**
     * No-arg constructor for the Business object, necessary for firebase integration
     */
    public Business() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    /**
     * Argumented constructor for a Business object
     * @param db_ID The database assigned ID value
     * @param business_Number The business number, should be a 9-digit number. Is type string to include 0 prefixed numbers
     * @param name The name for a business, not required to be unique, 2-48 characters
     * @param primary_Business The field of the business, {Fisher, Distributor, Processor, Fish Monger}
     * @param address The location of the business, length < 50
     * @param province_Territory The province or territory where this business is located : {AB, BC, MB, NB, NL, NS, NT, NU, ON, PE, QC, SK, YT, “ “}
     */
    public Business(String db_ID, String business_Number, String name, String primary_Business, String address, String province_Territory) {
        this.db_ID = db_ID;
        this.business_Number = business_Number;
        this.name = name;
        this.primary_Business = primary_Business;
        this.address = address;
        this.province_Territory = province_Territory;
    }

    /**
     * Converts the Business instance into a Map of values, keyed by the property name.
     * @return
     */
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("db_ID", db_ID);
        result.put("business_Number", business_Number);
        result.put("name", name);
        result.put("primary_Business", primary_Business);
        result.put("address", address);
        result.put("province_Territory", province_Territory);

        return result;
    }
}
