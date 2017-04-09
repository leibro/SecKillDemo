package com.leibro.dao;

import com.leibro.entity.SecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 *配置spring和junit整合
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:Spring/spring-dao.xml")
public class SeckillDaoTest {
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(10000,killTime);
        System.out.println("updateCount = " + updateCount);
    }

    @Test
    public void queryById() throws Exception {
        long id = 10000;
        SecKill secKill = seckillDao.queryById(id);
        System.out.println(secKill);
    }

    @Test
    public void queryAll() throws Exception {
        List<SecKill> secKills = seckillDao.queryAll(0,100);
        for(SecKill secKill:secKills)
            System.out.println(secKill);
    }

}