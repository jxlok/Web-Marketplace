package app.Service;

import app.Dao.AdminDao;
import app.Dao.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminDao adminDao;

    @Override
    public boolean validateAdmin(String email, String password) {
        return adminDao.validateAdmin(email, password);
    }
}
