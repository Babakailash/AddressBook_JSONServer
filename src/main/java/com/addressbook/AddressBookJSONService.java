package com.addressbook;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.ArrayList;
import java.util.List;

public class AddressBookJSONService {

    private List<AddressBookData> addressBookDataList;

    public AddressBookJSONService(List<AddressBookData> addressBookDataList) {
        this.addressBookDataList = new ArrayList<>(addressBookDataList);
    }

    public AddressBookJSONService() {}

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

    public void updateAddressBook(String name, String zip) {
        AddressBookData addressBookData = this.getAddressBookData(name);
        if (addressBookData != null){
            addressBookData.zip = zip;
        }
    }

    public AddressBookData getAddressBookData(String name) {
        return this.addressBookDataList.stream().filter(addressBookData -> addressBookData.firstName.equals(name)).findFirst().orElse(null);
    }

    public boolean deleteContactData(String firstName) {
        AddressBookData addressBookData = this.getAddressBookData(firstName);
        return addressBookDataList.remove(addressBookData);
    }

    public long countEntries() {
        return addressBookDataList.size();
    }
}

