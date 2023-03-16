package app.Service;

import app.Dao.CustomerDao;
import app.Entities.Customer;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerDao customers;
    public Optional<Customer> authenticate(String email, String password) {
        try {
            var customer = customers.getCustomerByEmail(email);
            if (hashPassword(password).equals(customer.getPassword())) {
                return Optional.of(customer);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public String createToken(Customer customer) {
        return Base64.getEncoder().encodeToString(customer.getEmail().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean create(String email, String password) {
        var candidate = new Customer();
        candidate.setEmail(email);
        candidate.setPassword(hashPassword(password));
        var rowAffected = customers.createCustomer(candidate);
        return rowAffected > 0;
    }

    private String hashPassword(String plainPassword) {
        return DigestUtils.sha3_256Hex(plainPassword);
    }
}
