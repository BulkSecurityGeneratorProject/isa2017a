package isa.service.dao;

import isa.domain.Gost;
import isa.domain.User;
import isa.security.SecurityUtils;
import isa.service.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GostDao {
    public final JdbcTemplate jdbcTemplate;
    UserDao userDao ;


    public GostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public long getUserIdByCurrentLogin () {
        String sql="select * from jhi_gost where login='"+ SecurityUtils.getCurrentUserLogin()+"'";
        return  this.jdbcTemplate.queryForObject(sql, long.class);
    }

    public long getGostByUser () {
        String sql="select * from jhi_gost where login='"+ SecurityUtils.getCurrentUserLogin()+"'";
        return  this.jdbcTemplate.queryForObject(sql, long.class);
    }
}
