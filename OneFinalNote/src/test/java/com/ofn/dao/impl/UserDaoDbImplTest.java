package com.ofn.dao.impl;

import com.ofn.dao.interfaces.UserDao;
import com.ofn.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class UserDaoDbImplTest {

    private UserDao dao;
    private DBMaintenanceDao mDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("UserDao", UserDao.class);
//        List<User> userDBEntries = dao.getAllUsers();
//        for(User u : userDBEntries){
//            dao.removeUser(u.getUserId());
//        }

        mDao = ctx.getBean("maintenanceDao", DBMaintenanceDao.class);
        mDao.refresh();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUserById() throws Exception {
        User u = new User();
        u.setUserName("hlemke91");
        u.setUserPW("corndog");
        u.setUserAvatar("hlemkedeathmetalpic.jpg");
        u.setUserProfile("ofn.org/users/hlemke91");
        u = dao.addUser(u);
        User getUser = dao.getUserById(u.getUserId());
        assertNotNull(getUser);
        assertEquals(u.getUserName(),getUser.getUserName());
        assertEquals(u.getUserPW(),u.getUserPW());
    }

    @Test
    public void getAllUsers() throws Exception {
//        User u = new User();
//        u.setUserName("hlemke91");
//        u.setUserPW("corndog");
//        u.setUserAvatar("hlemkedeathmetalpic.jpg");
//        u.setUserProfile("ofn.org/users/hlemke91");
//        dao.addUser(u);
//        u = new User();
//        u.setUserName("janssenda");
//        u.setUserPW("corndogs");
//        u.setUserAvatar("janssendatechmetalpic.jpg");
//        u.setUserProfile("ofn.org/users/janssenda");
//        dao.addUser(u);
//        u = new User();
//        u.setUserName("sethroTull");
//        u.setUserPW("cornwolf");
//        u.setUserAvatar("sethroTullprogmetalpic.jpg");
//        u.setUserProfile("ofn.org/users/sethroTull");
//        dao.addUser(u);
        List<User> allUsers = dao.getAllUsers();
        assertTrue(allUsers.size() == 4);
    }

    @Test
    public void addUser() throws Exception {
        User addUser = new User();
        addUser.setUserName("phaug");
        addUser.setUserPW("umphreesmcgeerulez");
        addUser.setUserAvatar("phaugjammetal.jpg");
        addUser.setUserProfile("ofn.org/users/phaug");
        User addedUser = dao.addUser(addUser);
        assertEquals(addedUser.getUserName(),addUser.getUserName());
    }

    @Test
    public void updateUser() throws Exception {
        User u = new User();
        u.setUserName("hlemke91");
        u.setUserPW("corndog");
        u.setUserAvatar("hlemkedeathmetalpic.jpg");
        u.setUserProfile("ofn.org/users/hlemke91");
        u = dao.addUser(u);
        int key = u.getUserId();
        User newUser = new User();
        newUser.setUserId(key);
        newUser.setUserName("jstuart");
        newUser.setUserPW("yogibear");
        newUser.setUserAvatar("jstuartjazzmetal.jpg");
        newUser.setUserProfile("ofn.org/users/jstuart");
        boolean isUserUpdated = dao.updateUser(newUser);
        assertTrue(isUserUpdated);
    }

    @Test
    public void removeUser() throws Exception {
        User u = new User();
        u.setUserName("hlemke91");
        u.setUserPW("corndog");
        u.setUserAvatar("hlemkedeathmetalpic.jpg");
        u.setUserProfile("ofn.org/users/hlemke91");
        u = dao.addUser(u);
        boolean isRemoved = dao.removeUser(u.getUserId());
        assertTrue(isRemoved);
    }

}