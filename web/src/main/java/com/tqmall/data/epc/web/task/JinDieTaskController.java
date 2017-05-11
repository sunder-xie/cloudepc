package com.tqmall.data.epc.web.task;

import com.tqmall.data.epc.bean.entity.jindie.EpcJinDieTaskDO;
import com.tqmall.data.epc.biz.jindie.JinDieBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by zxg on 16/9/5.
 * 11:08
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@RestController
@RequestMapping("/testTask")
@Slf4j
public class JinDieTaskController {
    @Autowired
    private JinDieBiz jinDieBiz;

//    @RequestMapping(value = "/testInsert", method = RequestMethod.GET)
//    public Boolean testInsert(Integer orderId,String orderSn,Integer orgId){
//        return jinDieBiz.insertDO(orderId, orderSn, orgId);
//    }

    // 筛选购买进金蝶，每半个小时执行一次
    @Scheduled(cron = "0 0/30 * * * ?")
    @RequestMapping(value = "/testSelectBuyOrder", method = RequestMethod.GET)
    public void selectBuyOrder() {
        long count = 0;
        log.info("=======[Task]开始进行 筛选购买进金蝶-start selectBuy into jindie =======");
        List<EpcJinDieTaskDO> list = jinDieBiz.getListByResultStatus(EpcJinDieTaskDO.RESULT_STATUS_NEW);

        if(CollectionUtils.isEmpty(list)){
            log.info("[Task] need do list is empty");
            return ;
        }

        for (EpcJinDieTaskDO epcJinDieTaskDO : list){
            try {
                Boolean result = jinDieBiz.toJinDieReport(epcJinDieTaskDO);
                if(result){
                    count ++;
                    log.info("[Task]任务执行成功,orderSn={}", epcJinDieTaskDO.getOrderSn());
                }
            } catch (Exception e) {
                log.error("[Task]任务执行异常,orderSn=" + epcJinDieTaskDO.getOrderSn(), e);
                try {
                    jinDieBiz.updateTaskFail(epcJinDieTaskDO.getId(), e.getMessage());
                } catch (Exception e1) {
                    log.info("update task fail,exception:{}",e1.getMessage());
                }
            }
        }
        log.info("=======[Task]结束 筛选购买进金蝶- end selectBuy into jindie =======");
        log.info("################[Task]success={}, fail={}###############", count, list.size() - count);

    }
}
