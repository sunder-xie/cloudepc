package com.tqmall.data.epc.dao.mapper.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpcOrderDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcOrderDO record);

    EpcOrderDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcOrderDO record);

    /*=========BY ZXG==============*/
    //获得 epc 订单列表
    List<EpcOrderDO> selectListByParam(@Param("shopId")Integer shopId,@Param("orderStatusArray")String[] orderStatusArray,@Param("startNum")Integer startNum,@Param("pageSize")Integer pageSize);
    //订单 总数
    int selectListCountByParam(@Param("shopId")Integer shopId,@Param("orderStatusArray")String[] orderStatusArray);

    /*=========START LYJ==============*/
    //根据 对象自身元素 全量匹配检索 list
    List<EpcOrderDO> selectByDO(EpcOrderDO searchDO);

    //获取需要自动签收的订单
    List<EpcOrderDO> selectNeedAutoSignOrder(@Param("startTime")String startTime, @Param("endTime")String endTime);

}