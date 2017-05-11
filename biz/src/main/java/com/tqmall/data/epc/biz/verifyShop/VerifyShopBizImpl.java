package com.tqmall.data.epc.biz.verifyShop;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.ShopVerityBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.exterior.dubbo.holy.VerifyMessageExt;
import com.tqmall.data.epc.exterior.dubbo.stall.VerifyUserExt;
import com.tqmall.holy.provider.entity.VerifyInfoDTO;
import com.tqmall.tqmallstall.domain.param.user.VerifyRequest;
import com.tqmall.tqmallstall.domain.result.servant.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by zxg on 16/10/14.
 * 17:36
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Service
public class VerifyShopBizImpl implements VerifyShopBiz {

    @Autowired
    private VerifyUserExt verifyUserExt;
    @Autowired
    private VerifyMessageExt verifyMessageExt;

    @Autowired
    protected ShiroBiz shiroBiz;

    @Override
    public ShopVerityBO getPreVerifyInfo(Integer userId) {
        if (userId == null || userId < 0) {
            return null;
        }
        Result<UserDTO> result = verifyUserExt.getVerifyInfo(userId);
        if (result != null && result.isSuccess()) {
            ShopVerityBO verityBO = BdUtil.do2bo(result.getData(), ShopVerityBO.class);
            return verityBO;
        }
        return null;
    }

    @Override
    public ShopVerityBO getVerifyInfo(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        Result<VerifyInfoDTO> verifyInfoDTOResult = verifyMessageExt.getVerifyInfoByMobile(mobile);
        if (verifyInfoDTOResult.isSuccess()) {
            VerifyInfoDTO verifyInfoDTO = verifyInfoDTOResult.getData();
            if (verifyInfoDTO != null) {
                //地区和地址、图片字段名一致
                ShopVerityBO shopVerityBO = new ShopVerityBO();
                shopVerityBO.setUserTitle(verifyInfoDTO.getCompanyName());
                shopVerityBO.setBusinessLicence(verifyInfoDTO.getCompanyFormalName());
                shopVerityBO.setContact(verifyInfoDTO.getContactsName());
                shopVerityBO.setProvince(verifyInfoDTO.getProvince().intValue());
                shopVerityBO.setCity(verifyInfoDTO.getCity().intValue());
                shopVerityBO.setDistrict(verifyInfoDTO.getDistrict().intValue());
                shopVerityBO.setStreet(verifyInfoDTO.getStreet().intValue());
                shopVerityBO.setAddress(verifyInfoDTO.getAddress());
                shopVerityBO.setImgs(verifyInfoDTO.getImgs());
                shopVerityBO.setVerifyFeedback(verifyInfoDTO.getMemo());

                return shopVerityBO;
            }
        }
        return null;
    }

    @Override
    public Boolean register(ShopVerityBO shopVerityBO) {
        if (shopVerityBO == null || shopVerityBO.getUid() == null) {
            return false;
        }
        UserBO userBO = shiroBiz.getCurrentUser();
        if (userBO == null) {
            return false;
        }
        //地区和地址一致
        VerifyRequest verifyRequest = new VerifyRequest();
        verifyRequest.setUid(userBO.getUid());
        verifyRequest.setMobile(userBO.getMobile());
        verifyRequest.setCompanyName(shopVerityBO.getUserTitle());
        verifyRequest.setBusinessLicense(shopVerityBO.getBusinessLicence());
        verifyRequest.setConsignee(shopVerityBO.getContact());
        verifyRequest.setProvince(shopVerityBO.getProvince());
        verifyRequest.setCity(shopVerityBO.getCity());
        verifyRequest.setDistrict(shopVerityBO.getDistrict());
        verifyRequest.setStreet(shopVerityBO.getStreet());
        verifyRequest.setAddress(shopVerityBO.getAddress());
        verifyRequest.setSaleMobile(shopVerityBO.getSaleMobile());

        Boolean is_register = verifyUserExt.register(verifyRequest);
        if (is_register) {
            is_register = this.updateImage(shopVerityBO.getImgs(), shopVerityBO.getUid());
        }
        return is_register;
    }

    @Override
    public Boolean updateImage(String imageUrl, Integer userId) {
        if (userId == null || userId < 0 || StringUtils.isEmpty(imageUrl)) {
            return false;
        }
        return verifyUserExt.updateImage(imageUrl, userId);
    }
}
