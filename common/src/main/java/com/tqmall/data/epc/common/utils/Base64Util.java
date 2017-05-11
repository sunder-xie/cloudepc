package com.tqmall.data.epc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by hzt
 */
public class Base64Util {

    private static Logger logger = LoggerFactory.getLogger(Base64Util.class);

    public static String encode(String str, String charset) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("使用base64加密异常,str" + str, e);
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    public static String decode(String str, String charset) {
        byte[] b = null;
        String result = null;
        if (str != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(str);
                result = new String(b, charset);
            } catch (Exception e) {
                logger.error("使用base64解密异常,str" + str, e);
            }
        }
        return result;
    }

}
