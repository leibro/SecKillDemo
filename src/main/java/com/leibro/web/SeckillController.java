package com.leibro.web;

import com.leibro.dto.Exposer;
import com.leibro.dto.SeckillExecution;
import com.leibro.dto.SeckillResult;
import com.leibro.entity.SecKill;
import com.leibro.enums.SeckillStateEnum;
import com.leibro.exception.RepeatKillException;
import com.leibro.exception.SeckillCloseException;
import com.leibro.service.SeckillSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by leibro on 2017/4/9.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillSerivce seckillSerivce;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model) {
        List<SecKill> secKillList = seckillSerivce.getSeckillList();
        model.addAttribute("list",secKillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable Long seckillId,Model model) {
        if(seckillId == null)
            return "redirect:/seckill/list";
        SecKill secKill = seckillSerivce.getById(seckillId);
        if(secKill == null)
            return "redirect:/seckill/list";
        model.addAttribute("seckill",secKill);
        return "detail";
    }


    //ajax json
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> /*TODO*/ exposer(Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillSerivce.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,@PathVariable("md5") String md5,@CookieValue(value = "killPhone",required = false) Long phone) {
        if(phone == null)
            return new SeckillResult<SeckillExecution>(false,"未注册");
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution = seckillSerivce.executeSeckill(seckillId,phone,md5);
            result = new SeckillResult<SeckillExecution>(true,seckillExecution);
        } catch (RepeatKillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(false,seckillExecution);
        } catch (SeckillCloseException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.END);
            result = new SeckillResult<SeckillExecution>(false,seckillExecution);
        } catch (Exception e) {
            logger.error(e.getMessage());
            SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(false,seckillExecution);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
