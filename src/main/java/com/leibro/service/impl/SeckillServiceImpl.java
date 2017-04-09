package com.leibro.service.impl;

import com.leibro.dao.SeckillDao;
import com.leibro.dao.SuccessKilledDao;
import com.leibro.dto.Exposer;
import com.leibro.dto.SeckillExecution;
import com.leibro.entity.SecKill;
import com.leibro.entity.SuccessKilled;
import com.leibro.enums.SeckillStateEnum;
import com.leibro.exception.RepeatKillException;
import com.leibro.exception.SeckillCloseException;
import com.leibro.exception.SeckillException;
import com.leibro.service.SeckillSerivce;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by leibro on 2017/4/9.
 */
@Service
public class SeckillServiceImpl implements SeckillSerivce {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    //MD5加盐，混淆
    private final String salt = "sadzc11&*^&*HGjhb21zc";

    public List<SecKill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public SecKill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        SecKill seckill = seckillDao.queryById(seckillId);
        if(seckill == null)
            return new Exposer(false,seckillId);
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime())
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        //转化特定字符串的过程，不可逆
        String md5 = null;
        return new Exposer(true,md5,seckillId);
    }

    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑 减库存 + 记录购买记录
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updateCount <= 0) {
                //没有更新到记录，秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSucessKilled(seckillId,userPhone);
                //唯一:seckillId,userPhone
                if(insertCount <= 0)
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e){
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
