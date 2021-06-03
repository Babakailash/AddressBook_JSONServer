package com.addressbook;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AddressBookDataJSONTest {

    @Test
    public void givenContactDataInJsonServer_WhenRetrievedShouldMatchThe_Count() {
        AddressBookJSONService addressBookJSONService = new AddressBookJSONService();
        AddressBookData[] addressBookData = addressBookJSONService.readAddressBookData();
        System.out.println(addressBookData);
        Assertions.assertEquals(5, addressBookData.length);
    }

    @Test
    public void whenNewContactisInsertedShouldReturnResponseCode201() {
        AddressBookJSONService addressBookJSONService = new AddressBookJSONService();
        AddressBookData store = new AddressBookData(6, "Arvind", "Kejriwal", "Moti-Road", "Kaushambi",
                                            "New Delhi", "110096", "444444444", "arvind@kejri.com", "2014-06-25");
        Response response = addressBookJSONService.addContactstoJSONServer(store);
        int statusCode = response.statusCode();
        System.out.println(statusCode);
        AddressBookData[] addressBookData = addressBookJSONService.readAddressBookData();
        System.out.println(addressBookData);
        Assertions.assertEquals(201, statusCode);
    }

    @Test
    public void givenContactToUpdateShouldReturnResponseCode200() {
        AddressBookJSONService addressBookRestService = null;
        AddressBookData[] arrayOfContacts = addressBookRestService.readAddressBookData();
        addressBookRestService = new AddressBookJSONService(Arrays.asList(arrayOfContacts));

        addressBookRestService.updateAddressBook("Nadeem", "456123");
        AddressBookData addressBookData = addressBookRestService.getAddressBookData("Nadeem");

        String json = new Gson().toJson(addressBookData);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(json);
        Response response = request.put("/addressbook" +addressBookData.id);
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(200, statusCode);
    }


    @Test
    public void givenContactToDelete_WhenUpdated_ShouldMatchCountAndResponseCode200() {
        AddressBookJSONService addressBookRestService = null;
        AddressBookData[] arrayOfContacts = addressBookRestService.readAddressBookData();
        addressBookRestService = new AddressBookJSONService(Arrays.asList(arrayOfContacts));

        AddressBookData addressBookData = addressBookRestService.getAddressBookData("Arvind");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        Response response = request.delete("/addressbook" + addressBookData.id);
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(200, statusCode);
        addressBookRestService.deleteContactData(addressBookData.firstName);
        long entries = addressBookRestService.countEntries();
        Assertions.assertEquals(4, entries);
    }
}




