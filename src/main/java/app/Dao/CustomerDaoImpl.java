package app.Dao;

import app.Entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl implements CustomerDao {
    public static final String CUSTOMER_ID = "customerID";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String GET_CUSTOMER_BY_EMAIL = "SELECT * FROM customers WHERE email = ?";
    public static final String CREATE_CUSTOMER = "INSERT INTO customers (email, password) VALUES (?, ?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Customer getCustomerByEmail(String email) throws DataAccessException {
        return jdbcTemplate.queryForObject(
                GET_CUSTOMER_BY_EMAIL,
                (rs, rowNum) -> {
                    var c = new Customer();
                    c.setCustomerID(rs.getInt(CUSTOMER_ID));
                    c.setEmail(rs.getString(EMAIL));
                    c.setPassword(rs.getString(PASSWORD));
                    return c;
                },
                email
        );
    }

    @Override
    public int createCustomer(Customer customer) {
        return jdbcTemplate.update(
            CREATE_CUSTOMER,
                customer.getEmail(),
                customer.getPassword()
        );
    }

}
