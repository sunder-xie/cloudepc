package com.tqmall.data.epc.biz.sys;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.AddressBO;
import com.tqmall.data.epc.bean.bizBean.shop.ShopBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.base.VerifyBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.biz.util.UserUtil;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.enums.UserType;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.CookieUtil;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.base.RegionServiceExt;
import com.tqmall.data.epc.exterior.dubbo.stall.VerifyUserExt;
import com.tqmall.data.epc.exterior.dubbo.uc.AccountServiceExt;
import com.tqmall.data.epc.exterior.dubbo.uc.AddressServiceExt;
import com.tqmall.data.epc.exterior.dubbo.uc.ShopInfoServiceExt;
import com.tqmall.data.epc.exterior.dubbo.uc.ShopTagServiceExt;
import com.tqmall.tqmallstall.domain.result.servant.user.UserDTO;
import com.tqmall.ucenter.object.result.account.AccountDTO;
import com.tqmall.ucenter.object.result.shop.ShopDTO;
import com.tqmall.ucenter.object.result.shoptag.ShopTagDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Created by huangzhangting on 16/6/21.
 */
@Service
@Slf4j
public class UserBizImpl implements UserBiz {

    @Value("${verify.switch}")
    private String verifySwitch;

    @Autowired
    private ShiroBiz shiroBiz;

    @Autowired
    private VerifyBiz verifyBiz;
    @Autowired
    private AccountServiceExt accountServiceExt;
    @Autowired
    private ShopInfoServiceExt shopInfoServiceExt;
    @Autowired
    private AddressServiceExt addressServiceExt;
    @Autowired
    private RegionServiceExt regionServiceExt;
    @Autowired
    private ShopTagServiceExt shopTagServiceExt;
    @Autowired
    private VerifyUserExt verifyUserExt;


    @Override
    public UserBO getByMobile(String mobile) {
        AccountDTO accountDTO = accountServiceExt.getAccountByMobile(ConstantBean.APPLICATION_NAME, mobile);
        if(accountDTO==null){
            return null;
        }

        UserBO user = new UserBO();
        user.setPassword("");
        user.setMobile(mobile);
        user.setHiddenMobile(EPCUtil.hideMobile(mobile));
        user.setShopId(accountDTO.getShopId());
        user.setRealName(accountDTO.getRealName());
        user.setAccountId(accountDTO.getId());

        ShopDTO shopDTO = shopInfoServiceExt.getShopByShopId(ConstantBean.APPLICATION_NAME, accountDTO.getShopId());

        if(shopDTO != null){
            user.setUid(shopDTO.getUserId());
            user.setShopBO(BdUtil.do2bo(shopDTO, ShopBO.class));
        }

        List<ShopTagDTO> tagDTOs =
                shopTagServiceExt.getTagsByShopId(ConstantBean.APPLICATION_NAME, accountDTO.getShopId());

        boolean flag = false;
        for(ShopTagDTO tag : tagDTOs){
            if(ConstantBean.YUN_XIU_TAG_KEYS.contains(tag.getTagKey())){
                user.setUserTypeCode(UserType.YUN_XIU.getCode());
                flag = true;
                break;
            }
        }
        if(!flag){
            user.setUserTypeCode(UserType.B_USER.getCode());
        }

        return user;
    }

    @Override
    public Result<AddressBO> getDefaultAddress() {
        List<AddressBO> addressBOList =
                addressServiceExt.getAddressByTypeAndUserId(ConstantBean.APPLICATION_NAME, shiroBiz.getUserId(), 2);

        if(addressBOList.isEmpty()){
            return ResultUtil.errorResult("", "获取默认地址失败");
        }
        AddressBO defaultAddress = null;
        for(AddressBO address : addressBOList){
            if(address.getAddrType()==2 && address.getIsDefault()==1){ //收货地址、默认地址
                defaultAddress = address;
                break;
            }
        }
        if(defaultAddress==null){
            Integer city = shiroBiz.getUserCity();
            for(AddressBO address : addressBOList){
                if(address.getAddrType()==2 && city.equals(address.getCityId())){
                    defaultAddress = address;
                    break;
                }
            }

            if(defaultAddress==null){
                return ResultUtil.errorResult("", "获取默认地址失败");
            }

        }
        return ResultUtil.successResult(defaultAddress);
    }

    @Override
    public Result<List<AddressBO>> getUserAddressList() {
        List<AddressBO> addressBOList =
                addressServiceExt.getAddressByTypeAndUserId(ConstantBean.APPLICATION_NAME, shiroBiz.getUserId(), 2);

        if(addressBOList.isEmpty()){
            return ResultUtil.errorResult("", "获取地址列表失败");
        }

        Set<Integer> regionIds = new HashSet<>();
        List<AddressBO> list = new ArrayList<>();
        for(AddressBO address : addressBOList){
            if(address.getAddrType()==2){ //收货地址
                regionIds.add(address.getProvinceId());
                regionIds.add(address.getCityId());
                regionIds.add(address.getDistrictId());
                regionIds.add(address.getStreetId());

                list.add(address);
            }
        }
        if(regionIds.isEmpty()){
            return ResultUtil.errorResult("", "获取地址列表失败");
        }

        Map<Integer, String> nameMap = regionServiceExt.getRegionNameMap(regionIds);
        if(nameMap != null){
            for(AddressBO address : list){
                String name = nameMap.get(address.getProvinceId());
                address.setProvinceName(name==null?"":name);

                name = nameMap.get(address.getCityId());
                address.setCityName(name==null?"":name);

                name = nameMap.get(address.getDistrictId());
                address.setDistrictName(name==null?"":name);

                name = nameMap.get(address.getStreetId());
                address.setStreetName(name==null?"":name);
            }
        }

        Collections.sort(list, new Comparator<AddressBO>() {
            @Override
            public int compare(AddressBO o1, AddressBO o2) {
                int flag = o2.getIsDefault().compareTo(o1.getIsDefault());
                if(flag == 0){
                    flag = o1.getProvinceName().compareTo(o2.getProvinceName());
                    if(flag == 0){
                        return o1.getCityName().compareTo(o2.getCityName());
                    }
                }
                return flag;
            }
        });

        return ResultUtil.successResult(list);
    }

    @Override
    public Result<String> checkShopVerifyStatus() {
        UserBO user = shiroBiz.getCurrentUser();
        if(user==null){
            return ResultUtil.errorResult("", "请先登录");
        }

        return UserUtil.checkShopVerifyStatus(user.getShopBO());
    }

    @Override
    public void resetVerifyStatus() {
        UserBO user = shiroBiz.getCurrentUser();
        if(user !=null){
            Result<UserDTO> result = verifyUserExt.getVerifyInfo(user.getUid());
            if (result != null && result.isSuccess()) {
                UserDTO userDTO = result.getData();
                if(null != userDTO) {
                    Integer verifyStatus = userDTO.getVerifyStatus();
                    // 如果认证状态不一致，则更新认证状态
                    if (verifyStatus != null && !user.getShopBO().getVerifyStatus().equals(verifyStatus)) {
                        ShopBO shopBO = user.getShopBO();
                        //设置 认证状态
                        shopBO.setVerifyStatus(verifyStatus);
                        //
                        if(StringUtils.isEmpty(shopBO.getCompanyName())){
                            shopBO.setCompanyName(userDTO.getUserTitle());
                        }
                        if(StringUtils.isEmpty(user.getRealName())){
                            user.setRealName(userDTO.getContact());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Result<UserBO> login(String userName, String verifyCode) {

        //校验手机号
        if(!EPCUtil.isMobile(userName)){
            return ResultUtil.errorResult("", "请输入正确的手机号");
        }

        //校验验证码
        if(ConstantBean.VERIFY_ON.equals(verifySwitch)){
            if(StringUtils.isEmpty(verifyCode)){
                return ResultUtil.errorResult("", "请输入正确的验证码");
            }
            if(!verifyBiz.verifying(userName, verifyCode)){
                return ResultUtil.errorResult("", "请输入正确的验证码");
            }
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, "");
        try {
            subject.login(token);
            log.info("user login success, userName:{}, verifyCode:{}", userName, verifyCode);
            return ResultUtil.successResult(shiroBiz.getCurrentUser());
        }catch (AuthenticationException ae){
            shiroBiz.removeCurrentUser();
            log.info("user login failed, userName:{}, verifyCode:{}, message:{}", userName, verifyCode, ae.getMessage());
            return ResultUtil.errorResult("", ae.getMessage());
        }catch (Exception e){
            shiroBiz.removeCurrentUser();
            log.error("user login system error, userName:"+userName, e);
            return ResultUtil.errorResult("", "登录失败，系统内部错误");
        }
    }

    @Override
    public void logout(HttpServletResponse response) {
        UserBO user = shiroBiz.getCurrentUser();
        if(user != null){
            log.info("user logout, mobile:{}", user.getMobile());
        }

        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        //删除cookie
        CookieUtil.delUserLoginInfo(response);
    }
}
