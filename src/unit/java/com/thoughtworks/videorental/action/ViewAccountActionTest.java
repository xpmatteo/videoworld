package com.thoughtworks.videorental.action;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.junit.Before;

import com.thoughtworks.videorental.domain.Customer;

public class ViewAccountActionTest {

    private ViewAccountAction target;

    @Before
    public void Setup() {
        target = new ViewAccountAction();
        target.setCustomer(new Customer("Test user"));
    }

    @Test
    public void shouldShowTheCustomerName() {
        assertNotNull(target.getCustomerName());
    }

    @Test
    public void shouldShowTheNumberOfFrequentRentsPoints() {
        assertNotNull(target.getFrequentRenterPointsStatement());
    }


}
