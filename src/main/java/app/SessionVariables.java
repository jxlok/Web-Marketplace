package app;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope
public class SessionVariables {

    private Map<Integer, String> inMemCustomerLoginCache = new HashMap<>();
    private Map<Integer, String> inMemAdminLoginCache = new HashMap<>();
    private boolean customerLoggedIn=false;
    private boolean adminLoggedIn=false;
    private boolean searching=false;

    public int getBasketCount() {
        return basketCount;
    }

    public void setBasketCount(int basketCount) {
        this.basketCount = basketCount;
    }

    private int basketCount=0;

    public boolean isCustomerLoggedIn() {
        return customerLoggedIn;
    }

    public void setCustomerLoggedIn(boolean customerLoggedIn) {
        this.customerLoggedIn = customerLoggedIn;
    }

    public boolean isAdminLoggedIn() {
        return adminLoggedIn;
    }

    public void setAdminLoggedIn(boolean adminLoggedIn) {
        this.adminLoggedIn = adminLoggedIn;
    }

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    public void addCustomerLogin(int customerId, String token) {
        inMemCustomerLoginCache.put(customerId, token);
    }

    public boolean isCustomerLoggedIn(int customerId) {
        return inMemCustomerLoginCache.containsKey(customerId);
    }

    public void removeCustomerLogin(int customerId) {
        inMemCustomerLoginCache.remove(customerId);
    }

    public void addAdminLogin(int adminId, String token) {
        inMemAdminLoginCache.put(adminId, token);
    }

    public boolean isAdminLoggedIn(int adminId) {
        return inMemAdminLoginCache.containsKey(adminId);
    }

    public void removeAdminLogin(int adminId) {
        inMemAdminLoginCache.remove(adminId);
    }
}
