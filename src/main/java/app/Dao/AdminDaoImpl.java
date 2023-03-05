package app.Dao;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class AdminDaoImpl extends JdbcDaoSupport implements AdminDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialise(){
        setDataSource(dataSource);
    }

    @Override
    public boolean validateAdmin(String email, String password) {
        String sql = "SELECT * FROM admins WHERE email='"+email+"' AND password='"+password+"'";
        List<Map<String, Object>> admin = getJdbcTemplate().queryForList(sql);

        return !admin.isEmpty();
    }
}
