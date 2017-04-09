package com.leibro.dao;

import com.leibro.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by leibro on 2017/4/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:Spring/spring-dao.xml")
public class SuccessKilledDaoTest {
    @Autowired
    SuccessKilledDao successKilledDao;

    @Test
    public void insertSucessKilled() throws Exception {
        long id = 10000;
        long phone = 13502183333L;
        int insertCount = successKilledDao.insertSucessKilled(id,phone);
        System.out.println("insertCount = " + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 10000;
        long phone = 13502183333L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);

    }

}