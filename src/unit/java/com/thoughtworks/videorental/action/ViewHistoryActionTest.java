package com.thoughtworks.videorental.action;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;

public class ViewHistoryActionTest {

    @Test
    public void ShouldShowTheNumberOfFrequentRentsPoints() {

        Customer customer = new Customer("Test user");
        ViewHistoryAction target = new ViewHistoryAction(null);
        target.setCustomer(customer);

        assertNotNull(target.getFrequentRenterPointsStatement());
    }
}
