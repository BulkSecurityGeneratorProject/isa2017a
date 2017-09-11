package isa.service;

import isa.security.SecurityUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {
    public final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public long getUserIdByCurrentLogin () {
        String sql="select * from jhi_user where login='"+ SecurityUtils.getCurrentUserLogin()+"'";
        return  this.jdbcTemplate.queryForObject(sql, long.class);
    }
}
