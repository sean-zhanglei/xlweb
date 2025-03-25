package org.nbug.mico.domain.customer.gateway;

import org.nbug.mico.domain.customer.Customer;

public interface CustomerGateway {
    Customer getByById(String customerId);
}
