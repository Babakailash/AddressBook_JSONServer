package com.addressbook;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}



