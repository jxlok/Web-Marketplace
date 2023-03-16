package app.Dao;

import app.Entities.Customer;
import org.springframework.dao.DataAccessException;

public interface CustomerDao {
    Customer getCustomerByEmail(String email) throws DataAccessException;

    int createCustomer(Customer customer);
}
