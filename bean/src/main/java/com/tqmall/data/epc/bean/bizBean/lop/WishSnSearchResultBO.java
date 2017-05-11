package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/12.
 */
@Data
public class WishSnSearchResultBO {
    private int total;
    private List<String> snList;
}
