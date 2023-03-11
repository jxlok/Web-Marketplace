package app;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope
public class SessionVariables {


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
}
