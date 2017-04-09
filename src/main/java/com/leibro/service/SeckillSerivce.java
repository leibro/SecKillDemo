package com.leibro.service;

import com.leibro.dto.Exposer;
import com.leibro.dto.SeckillExecution;
import com.leibro.entity.SecKill;
import com.leibro.exception.RepeatKillException;
import com.leibro.exception.SeckillCloseException;
import com.leibro.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在"使用者"角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型(return 类型/异常)
 * Created by leibro on 2017/4/9.
 */
public interface SeckillSerivce {


    /**
     * 查询所有秒杀记录
     * @return
     */
    List<SecKill> getSeckillList();

    SecKill getById(long seckillId);

    Exposer exportSeckillUrl(long seckillId);

    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}
