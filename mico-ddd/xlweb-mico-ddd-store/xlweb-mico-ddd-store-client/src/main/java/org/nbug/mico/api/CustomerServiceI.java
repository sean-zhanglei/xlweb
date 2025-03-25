package org.nbug.mico.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import org.nbug.mico.dto.CustomerAddCmd;
import org.nbug.mico.dto.CustomerListByNameQry;
import org.nbug.mico.dto.data.CustomerDTO;

public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd customerAddCmd);

    MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry);
}
