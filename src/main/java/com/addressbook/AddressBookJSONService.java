package com.addressbook;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddressBookJSONService {

    public AddressBookData[] readAddressBookData() {
        RestAssured.baseURI = "http://localhost:3000";

        Response response = RestAssured.get("/addressbook");
        System.out.println("Contacts in JSON are : " + response.asString());
        AddressBookData[] addressBookData = new Gson().fromJson(response.asString(),AddressBookData[].class);
        return addressBookData;
    }
}
