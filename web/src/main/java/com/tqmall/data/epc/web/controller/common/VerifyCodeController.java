package com.tqmall.data.epc.web.controller.common;

import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.utils.VerifyCodeUtil;
import com.tqmall.data.epc.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by lyj on 16/8/19.
 * 开开心心写代码-lyj!
 */

@Controller
@RequestMapping(value = "verifyCode")
public class VerifyCodeController extends BaseController {

    @RequestMapping(value = "getVerifyCode")
    public void getVerifyCode(HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        BufferedImage image = initVerifyCode2SessionAndGetImgObj();

        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage initVerifyCode2SessionAndGetImgObj() {
        //生成随机字串
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);

        //把验证码放到session
        putIntoSession(ConstantBean.CHECK_CODE_KEY, verifyCode);

        //生成图片
        int w = 200, h = 80;
        try {
            return VerifyCodeUtil.outputImage(w, h, verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    }

    @RequestMapping("verifyCode")
    public ModelAndView verifyCode() {

        return new ModelAndView(webVersion + "/cloudepc/lop/demo/verifyCode");
    }
}
