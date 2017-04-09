package com.leibro.service;

import com.leibro.dto.Exposer;
import com.leibro.dto.SeckillExecution;
import com.leibro.entity.SecKill;
import com.leibro.exception.RepeatKillException;
import com.leibro.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;

import java.util.List;

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
        List<SecKill> list = seckillSerivce.getSeckillList();
        logger.info("seckill = {}",list);
    }

    @Test
    public void getById() throws Exception {
        long id = 10000;
        SecKill seckill = seckillSerivce.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 10000;
        Exposer exposer = seckillSerivce.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
    }

//    @Test
//    public void executeSeckill() throws Exception {
//        long id = 10000;
//        long phone = 17841241110L;
//        String md5 = "0c599bad66be3179be680982c00f7363";
//        try {
//            SeckillExecution execution = seckillSerivce.executeSeckill(id, phone, md5);
//            logger.info("seckillExecution={}",execution);
//        } catch (RepeatKillException e) {
//            logger.error(e.getMessage());
//        } catch (SeckillCloseException e) {
//            logger.error(e.getMessage());
//        }
//
//
//    }


    /**
     * 集成测试，完整测试逻辑
     * @throws Exception
     */
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 10000;
        long phone = 17841241110L;
        Exposer exposer = seckillSerivce.exportSeckillUrl(id);
        if(exposer.isExposed()) {
            try {
                SeckillExecution execution = seckillSerivce.executeSeckill(id, phone, exposer.getMd5());
                logger.info("seckillExecution={}",execution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("exposer={}",exposer);
        }
    }

}