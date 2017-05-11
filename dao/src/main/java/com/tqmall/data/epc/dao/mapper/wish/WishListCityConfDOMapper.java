package com.tqmall.data.epc.dao.mapper.wish;

import com.tqmall.data.epc.bean.entity.wish.WishListCityConfDO;

import java.util.List;

public interface WishListCityConfDOMapper {

    Integer checkCity(Integer cityId);

    List<WishListCityConfDO> getAllCity();
}