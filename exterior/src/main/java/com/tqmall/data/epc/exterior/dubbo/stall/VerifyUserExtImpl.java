package com.tqmall.data.epc.exterior.dubbo.stall;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.ShopVerityBO;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.tqmallstall.domain.param.user.VerifyRequest;
import com.tqmall.tqmallstall.domain.result.servant.user.UserDTO;
import com.tqmall.tqmallstall.service.user.RpcUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zxg on 16/10/14.
 * 10:24
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Service
@Slf4j
public class VerifyUserExtImpl implements VerifyUserExt {

    @Autowired
    private RpcUserService rpcUserService;

    @Override
    public Result<UserDTO> getVerifyInfo(Integer userId) {
        try {
            log.info("start getVerifyInfo,userId:{}",userId);

            Result<UserDTO> result = rpcUserService.getOneUser(userId);
            if (!result.isSuccess()) {
                log.info("getVerifyInfo failed, result:{}", JsonUtil.objectToJson(result));
            }

            return result;

        } catch (Exception e) {
            log.error("getVerifyInfo, error", e);
            return null;
        }
    }

    @Override
    public Boolean register(VerifyRequest verifyRequest) {
        log.info("申请认证B用户,param= {}" , JsonUtil.objectToJson(verifyRequest));
        Result<Map<String, Object>> result = Result.wrapErrorResult("","未知错误");
        try {
            result = rpcUserService.verify2B(verifyRequest);
        } catch (Exception e) {
            log.error("申请认证B用户失败", e);
        }
        return result.isSuccess();
    }

    @Override
    public Boolean updateImage(String imageUrl, Integer userId) {
        log.info("更新认证的图片，imgUrl:{},userId:{}",imageUrl,userId);
        try {
            Result<Integer> result = rpcUserService.updateImage(imageUrl, userId);
            if (result.isSuccess()) {
                return true;
            }
        } catch (Exception e) {
            log.error("上传用户认证图片", e, "imageUrl=" +imageUrl+" userId="+ userId);
        }

        return false;
    }
}
