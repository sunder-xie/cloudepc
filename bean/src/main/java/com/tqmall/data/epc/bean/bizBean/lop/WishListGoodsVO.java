package com.tqmall.data.epc.bean.bizBean.lop;

import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import lombok.Data;

import java.util.List;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class WishListGoodsVO {
    /**
     * 需求单商品ID
     */
    private Integer wishListGoodsId;
    /**
     * 需求单商品名称
     */
    private String requireGoodsName;
    /**
     * 需求单商品OE码
     */
    private String requireOeNum;
    /**
     * 需求单商品首选品质
     */
    private String requireGoodsQualityType;
    /**
     * 需求单商品次选品质
     */
    private String requireGoodsQualityTypeReserve;
    /**
     * 需求单商品数量
     */
    private Integer requireGoodsNumber;

    /**
     * 需求商品单位
     */
    private String requireGoodsMeasureUnit;
    /**
     * 需求单商品备注
     */
    private String requireGoodsMemo;
    /**
     * 需求单商品图片
     */
    private List<String> requireGoodsImages;
    /**
     * 需求单分组标识
     * groupId相同的，为需求单中同一个商品，不同品质
     */
    private Integer groupId;

    public List<String> getRequireGoodsImages() {
        if (requireGoodsImages != null && !requireGoodsImages.isEmpty()) {
            for (int i = 0; i < requireGoodsImages.size(); i++) {
                String image = requireGoodsImages.get(i);
                requireGoodsImages.set(i, ImgUtil.getFullPath(image));
            }
        }
        return requireGoodsImages;
    }
}
