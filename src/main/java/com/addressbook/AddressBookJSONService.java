package com.addressbook;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookJSONService {

    public AddressBookData[] readAddressBookData() {
        RestAssured.baseURI = "http://localhost:3000";
        Response response = RestAssured.get("/addressbook");
        System.out.println("Contacts in JSON are : " + response.asString());
        AddressBookData[] addressBookData = new Gson().fromJson(response.asString(), AddressBookData[].class);
        return addressBookData;
    }

    public Response addContactstoJSONServer(AddressBookData addressBookData){
        RestAssured.baseURI = "http://localhost:3000";
        String contact = new Gson().toJson(addressBookData);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type", "application/json");
        requestSpecification.body(contact);
        return requestSpecification.post("/addressbook");
    }
}
