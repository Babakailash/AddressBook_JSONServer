package com.addressbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressBookDataJSONTest {

    @Test
    public void givenContactDataInJsonServer_WhenRetrievedShouldMatchThe_Count() {
        AddressBookJSONService addressBookJSONService = new AddressBookJSONService();
        AddressBookData[] addressBookData = addressBookJSONService.readAddressBookData();
        System.out.println(addressBookData);
        Assertions.assertEquals(3, addressBookData.length);
        }
}



