package com.tqmall.data.epc.exterior.dubbo.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.ZTree;
import com.tqmall.data.epc.bean.bizBean.base.RegionBO;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.tqmallstall.domain.result.CityListDTO;
import com.tqmall.tqmallstall.domain.result.RegionDTO;
import com.tqmall.tqmallstall.service.common.AppRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by huangzhangting on 16/7/5.
 */
@Service
public class RegionServiceExtImpl implements RegionServiceExt {
    private static final Integer CHINA_ID = 1;

    @Autowired
    private RedisClientTemplate redisClient;
    @Autowired
    private AppRegionService stallRegionService;


    @Override
    public List<ZTree> cityZTreeList() {
        String redisStr = redisClient.get(RedisKeyBean.ALL_CITY_KEY);
        if(redisStr!=null){
            return JsonUtil.jsonToList(redisStr, ZTree.class);
        }

        List<ZTree> zTreeList = new ArrayList<>();
        //中国省份
        Result<List<RegionDTO>> provResult = stallRegionService.subRegion(CHINA_ID);
        if (provResult == null || !provResult.isSuccess()) {
            return zTreeList;
        }

        List<RegionDTO> provList = provResult.getData();
        if (CollectionUtils.isEmpty(provList)) {
            return zTreeList;
        }

        //已开通城市
        Result<CityListDTO> cityResult = stallRegionService.citySite();
        if (cityResult == null || !cityResult.isSuccess()) {
            return zTreeList;
        }

        CityListDTO cityListDTO = cityResult.getData();
        if (cityListDTO == null) {
            return zTreeList;
        }

        List<RegionDTO> cityList = cityListDTO.getCities();
        if (CollectionUtils.isEmpty(cityList)) {
            return zTreeList;
        }

        //组装ztree数据
        for (RegionDTO prov : provList) {
            List<ZTree> list = new ArrayList<>();
            for (RegionDTO city : cityList) {
                if (city.getParentId().equals(prov.getId())) {
                    ZTree ct = new ZTree(true);
                    ct.setId(city.getId());
                    ct.setName(city.getRegionName());
                    ct.setPId(prov.getId());

                    list.add(ct);
                }
            }

            if (!list.isEmpty()) {
                ZTree zTree = new ZTree(false);
                zTree.setId(prov.getId());
                zTree.setName(prov.getFirstLetter() + " " + prov.getRegionName());
                zTree.setPId(CHINA_ID);
                zTree.setChildren(list);

                zTreeList.add(zTree);
            }
        }

        Collections.sort(zTreeList, new Comparator<ZTree>() {
            @Override
            public int compare(ZTree o1, ZTree o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        redisClient.lazySet(RedisKeyBean.ALL_CITY_KEY, zTreeList, RedisKeyBean.RREDIS_EXP_DAY);
        return zTreeList;
    }

    @Override
    public List<RegionBO> subRegions(Integer pid) {
        if(pid==null || pid<0){
            return new ArrayList<>();
        }

        String key = String.format(RedisKeyBean.REGION_LIST_PID_KEY, pid);
        String redisStr = redisClient.get(key);
//        String redisStr = null;
        if(redisStr != null){
            return JsonUtil.jsonToList(redisStr, RegionBO.class);
        }

        Result<List<RegionDTO>> result = stallRegionService.subRegion(pid);
        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            List<RegionBO> list = BdUtil.do2bo4List(result.getData(), RegionBO.class);
            Collections.sort(list, new Comparator<RegionBO>() {
                @Override
                public int compare(RegionBO o1, RegionBO o2) {
                    return o1.getFirstLetter().compareTo(o2.getFirstLetter());
                }
            });
            redisClient.lazySet(key, list, RedisKeyBean.RREDIS_EXP_DAY);
            return list;
        }

        return new ArrayList<>();
    }

    @Override
    public RegionBO getRegionById(Integer id) {
        if(id==null || id<1){
            return null;
        }

        String key = String.format(RedisKeyBean.REGION_BY_ID_KEY, id);
        String redisStr = redisClient.get(key);
        if(redisStr != null){
            return JsonUtil.jsonToObject(redisStr, RegionBO.class);
        }

        Result<List<RegionDTO>> result = stallRegionService.getRegionListByIds(id+"");
        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            RegionBO regionBO = BdUtil.do2bo(result.getData().get(0), RegionBO.class);
            redisClient.lazySet(key, regionBO, RedisKeyBean.RREDIS_EXP_DAY);
            return regionBO;
        }
        return null;
    }

    @Override
    public Map<Integer, String> getRegionNameMap(Collection<Integer> regionIds) {
        if(CollectionUtils.isEmpty(regionIds)){
            return null;
        }
        List<Integer> idList = new ArrayList<>(regionIds);
        Result<Map<Integer, String>> result = stallRegionService.getRegionNameMap(idList);
        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            return result.getData();
        }
        return null;
    }
}
