package com.masternaut;

import com.masternaut.domain.Asset;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DomainHelperTest {
    @Test
    public void getCustomerIdFromList_for_emptyList() {
        try {
            DomainHelper.getCustomerIdFromList(new ArrayList<CustomerIdentifiable>());
            fail("Exception not thrown");
        } catch (PaddingtonException pe) {
            assertEquals("No items in list.", pe.getMessage());
        }
    }

    @Test
    public void getCustomerIdFromList() {
        Asset asset = new Asset();
        asset.setCustomerId("MyCustomerId");

        assertEquals("MyCustomerId", DomainHelper.getCustomerIdFromList(Arrays.asList(asset)));
    }

    @Test
    public void getCustomerIdFromList_with_differentCustomerIds() {
        Asset asset1 = new Asset();
        asset1.setCustomerId("MyCustomerId1");

        Asset asset2 = new Asset();
        asset2.setCustomerId("MyCustomerId2");

        try{
            DomainHelper.getCustomerIdFromList(Arrays.asList(asset1,  asset2));
            fail("Exception not thrown");
        }catch (PaddingtonException pe){
            assertEquals("List contains object with different customerIds", pe.getMessage());
        }
    }

    @Test
    public void getCustomerIdFromList_with_null_CustomerId() {
        Asset asset1 = new Asset();

        try{
            DomainHelper.getCustomerIdFromList(Arrays.asList(asset1));
            fail("Exception not thrown");
        }catch (PaddingtonException pe){
            assertEquals("List contains object null customerId", pe.getMessage());
        }
    }
}
