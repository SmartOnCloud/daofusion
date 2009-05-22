package com.anasoft.os.daofusion.sample.hellodao.server;

import java.util.ArrayList;
import java.util.List;

import com.anasoft.os.daofusion.criteria.PersistentEntityCriteria;
import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.server.CriteriaTransferObjectConverter;
import com.anasoft.os.daofusion.cto.server.CriteriaTransferObjectCountWrapper;
import com.anasoft.os.daofusion.sample.hellodao.client.dto.CustomerDto;
import com.anasoft.os.daofusion.sample.hellodao.client.service.CustomerService;
import com.anasoft.os.daofusion.sample.hellodao.client.service.ResultSet;
import com.anasoft.os.daofusion.sample.hellodao.client.service.ServiceException;
import com.anasoft.os.daofusion.sample.hellodao.server.cto.CustomerCtoConverter;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.CustomerDao;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.ContactDetails;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.Customer;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.impl.DaoManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server-side {@link CustomerService} implementation
 * based on GWT RPC's {@link RemoteServiceServlet}.
 */
public class RpcServiceServlet extends RemoteServiceServlet
        implements CustomerService {
    
    private static final long serialVersionUID = -5111678141824416652L;
    
    private static final CriteriaTransferObjectConverter ctoConverter = new CustomerCtoConverter();
    
    private static final CustomerDao customerDao = DaoManager.getCustomerDao();
    
    public ResultSet<CustomerDto> fetch(CriteriaTransferObject cto) throws ServiceException {
        PersistentEntityCriteria queryCriteria = ctoConverter.convert(
                cto, CustomerCtoConverter.GROUP_NAME);
        List<Customer> cList = customerDao.query(queryCriteria);
        
        PersistentEntityCriteria countCriteria = ctoConverter.convert(
                new CriteriaTransferObjectCountWrapper(cto).wrap(),
                CustomerCtoConverter.GROUP_NAME);
        int totalRecords = customerDao.count(countCriteria);
        
        List<CustomerDto> dtoList = toDtoList(cList);
        
        return new ResultSet<CustomerDto>(dtoList, totalRecords);
    }
    
    public CustomerDto add(CustomerDto dto) throws ServiceException {
        Customer c = new Customer();
        c.setContactDetails(new ContactDetails());
        copyValues(dto, c);
        c = customerDao.saveOrUpdate(c);
        copyValues(c, dto);
        return dto;
    }
    
    public CustomerDto update(CustomerDto dto) throws ServiceException {
        Customer c = customerDao.get(Long.valueOf(dto.getId()));
        copyValues(dto, c);
        c = customerDao.saveOrUpdate(c);
        copyValues(c, dto);
        return dto;
    }
    
    public void remove(CustomerDto dto) throws ServiceException {
        customerDao.delete(Long.valueOf(dto.getId()));
    }
    
    // DTO <--> persistent entity transformations
    
    private void copyValues(Customer from, CustomerDto to) {
        to.setId(String.valueOf(from.getId()));
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setTotalOrders(from.getUnmodifiableOrderList().size());
        to.setContactEmail(from.getContactDetails().getEmail());
        to.setContactPhone(from.getContactDetails().getPhone());
    }
    
    private void copyValues(CustomerDto from, Customer to) {
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.getContactDetails().setEmail(from.getContactEmail());
        to.getContactDetails().setPhone(from.getContactPhone());
    }
    
    private List<CustomerDto> toDtoList(List<Customer> cList) {
        List<CustomerDto> result = new ArrayList<CustomerDto>();
        for (Customer c : cList) {
            CustomerDto dto = new CustomerDto();
            copyValues(c, dto);
            result.add(dto);
        }
        return result;
    }
    
}
