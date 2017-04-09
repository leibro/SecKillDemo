package com.leibro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Created by leibro on 2017/4/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:Spring/spring-dao.xml","classpath:Spring/spring-service.xml"})
public class SeckillSerivceTest {
    @Autowired
    private SeckillSerivce seckillSerivce;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getSeckillList() throws Exception {

    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void exportSeckillUrl() throws Exception {
    }

    @Test
    public void executeSeckill() throws Exception {
    }

}