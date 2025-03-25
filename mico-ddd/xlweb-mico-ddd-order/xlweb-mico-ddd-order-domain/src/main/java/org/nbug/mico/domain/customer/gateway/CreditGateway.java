package org.nbug.mico.domain.customer.gateway;

import org.nbug.mico.domain.customer.Credit;

//Assume that the credit info is in another distributed Service
public interface CreditGateway {
    Credit getCredit(String customerId);
}
