package com.tqmall.data.epc.bean.entity.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class GoodsQuoteDO {
    public static Map<Integer, String> qualityMap;

    static {
        qualityMap = new HashMap<>();
        qualityMap.put(0, "原厂");
        qualityMap.put(1, "品牌件");
    }

    private Integer id;

    private Integer goodsId;

    private Integer carId;

    private BigDecimal quotedPrice;

    private String company;

    private Integer sellerLoginId;

    private Integer goodsQuality;
    private String goodsQualityStr;

    private Integer brandId;

    private String brandName;

    private Date gmtModified;

    private Boolean isAvailable; // 有/无货，true：有货，false：无货

}