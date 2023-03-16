package app.Service;

import app.Entities.Customer;

import java.util.Optional;

public interface CustomerService {
     Optional<Customer> authenticate(String email, String password);
     String createToken(Customer customer);

     boolean create(String email, String password);
}
