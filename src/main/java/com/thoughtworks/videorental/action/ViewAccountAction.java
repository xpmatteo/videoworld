package com.thoughtworks.videorental.action;

import java.util.Collection;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.interceptor.CustomerAware;

public class ViewAccountAction  extends ActionSupport implements CustomerAware {

    private Customer customer;

    @Override
    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public String getFrequentRenterPointsStatement() {
        return customer.getFrequentRenterPointsStatement();
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
}
