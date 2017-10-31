package com.ofn.dao.impl;

import com.ofn.dao.interfaces.CategoryDao;
import com.ofn.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CategoryDaoDbImplTest {

    private CategoryDao dao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                "test-applicationContext.xml");
        dao = ctx.getBean("CategoryDao", CategoryDao.class);
        List<Category> categoryDBEntries = dao.getAllCategories();
        for(Category cat : categoryDBEntries){
            dao.removeCategory(cat.getCategoryID());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllCategories() throws Exception {
        Category cat = new Category();
        cat.setCategoryName("metal");
        cat.setDescription("all things metal");
        dao.addCategory(cat);
        Category cat2 = new Category();
        cat2.setCategoryName("jazz");
        cat2.setDescription("all things jazz");
        dao.addCategory(cat2);
        List<Category> cats = dao.getAllCategories();
        assertEquals(2, cats.size());
    }

    @Test
    public void getCategory() throws Exception {
        Category cat = new Category();
        cat.setCategoryName("deathmetal");
        cat.setDescription("all things deathmetal");
        cat = dao.addCategory(cat);
        Category getcat = dao.getCategory(cat.getCategoryID());
        assertNotNull(getcat);
        assertEquals(cat.getCategoryID(), getcat.getCategoryID());
    }

    @Test
    public void addCategory() throws Exception {
        Category cat = new Category();
        cat.setCategoryName("Rush");
        cat.setDescription("all things Rush");
        Category catadded = dao.addCategory(cat);
        assertEquals(catadded.getCategoryName(), cat.getCategoryName());
    }

    @Test
    public void removeCategory() throws Exception {
        Category cat = new Category();
        cat.setCategoryName("jam band");
        cat.setDescription("all things jam band");
        cat = dao.addCategory(cat);
        boolean isDeleted = dao.removeCategory(cat.getCategoryID());
        assertTrue(isDeleted);
    }

    @Test
    public void updateCategory() throws Exception {
        Category cat = new Category();
        cat.setCategoryName("hippies");
        cat.setDescription("news on jam band, rave, and prog rock bands, festivals, and deals");
        Category catAdded = dao.addCategory(cat);
        boolean isCatEdited = dao.updateCategory(catAdded);
        assertTrue(isCatEdited);
    }

}