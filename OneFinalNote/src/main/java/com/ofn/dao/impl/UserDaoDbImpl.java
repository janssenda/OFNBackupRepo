package com.ofn.dao.impl;

import com.ofn.dao.interfaces.UserDao;
import com.ofn.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ofn.dao.impl.DataManipulator.nullify;
import static com.ofn.dao.impl.DataManipulator.varArgs;

/*
*
*   @author SethroTull
*
* */

public class UserDaoDbImpl implements UserDao {

    private static final String SQL_INSERT_AUTHORITY
            = "insert into authorities (UserName, Authority) values (?, ?)";

    private static final String SQL_DELETE_AUTHORITIES
            = "delete from authorities where UserName = ?";

    private static final String SQL_INSERT_USER
            = "insert into users (UserName, UserPass, Avatar, UserProfile, Enabled) values (?, ?, ?, ?, 1)";

    private static final String SQL_UPDATE_USER
            = "update users set UserName = ?, UserPass = ?, Avatar = ?, UserProfile = ?, Enabled = ? where UserID = ?";

    private static final String SQL_GET_ALL_USERS
            = "select * from users";

    private static final String SQL_GET_USER_BY_ID
            = "select * from users where UserID = ?";

    private static final String SQL_DELETE_USER
            = "delete from users where UserID = ?";

    private static final String GET_USER_QUERY_MULTI =
            "SELECT * FROM users WHERE 1 = 1 " +
                    "AND (@UserID IS NULL OR UserID = @UserID) " +
                    "AND (@UserName IS NULL OR UserName = @UserName) ";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int userId) {
        return jdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, new UserMapper(), userId);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserMapper());
    }

    @Override
    public User addUser(User user) {
        int success = jdbcTemplate.update(SQL_INSERT_USER, user.getUserName(), user.getUserPW(),
                user.getUserAvatar(), user.getUserProfile());
        if (success == 1) {
            int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
            User userAdded = jdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, new UserMapper(), id);

            // now insert user's roles
            ArrayList<String> authorities = user.getAuthorities();
            for (String authority : authorities) {
                jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                        user.getUserName(),
                        authority);
            }

            return userAdded;
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        int success = jdbcTemplate.update(SQL_UPDATE_USER, user.getUserName(), user.getUserPW(),
                user.getUserAvatar(), user.getUserProfile(), user.getIsEnabled(), user.getUserId());

        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, user.getUserName());

        // now insert user's roles
        ArrayList<String> authorities = user.getAuthorities();
        for (String authority : authorities) {
            jdbcTemplate.update(SQL_INSERT_AUTHORITY,
                    user.getUserName(),
                    authority);
        }

        if (success == 1) {
            User updatedUser = jdbcTemplate.queryForObject(SQL_GET_USER_BY_ID, new UserMapper(),
                    user.getUserId());
            if (user.getUserId() == updatedUser.getUserId()) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean removeUser(int userID) {
        boolean success = false;

        if (jdbcTemplate.update(SQL_DELETE_AUTHORITIES, getUserById(userID).getUserName())>=0) {
            success = (jdbcTemplate.update(SQL_DELETE_USER, userID)>0);
        }
        return success;
    }

    @Override
    @Transactional
    public List<User> searchUsers(String... args) {

        String[] allArgs = varArgs(args, 2);
        String setup = "SET @UserID = ?, @UserName = ?; ";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, nullify(allArgs[0]));
            ps.setString(2, nullify(allArgs[1]));

            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();

            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);

            // Search for usersw based on the given criteria,
            return jdbcTemplate.query(GET_USER_QUERY_MULTI, new UserMapper());

        } catch (SQLException e) {
            return null;
        }
    }

    public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getInt("UserID"));
            user.setUserName(resultSet.getString("UserName"));
            user.setUserPW(resultSet.getString("UserPass"));
            user.setUserAvatar(resultSet.getString("Avatar"));
            user.setUserProfile(resultSet.getString("UserProfile"));
            return user;
        }
    }

}


