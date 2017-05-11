package com.tqmall.data.epc.common.utils;

import com.tqmall.data.epc.common.bean.enums.PayMessage;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zxg on 16/2/1.
 * 16:34
 */
public class EPCUtil {

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};


    public static String getIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * vin码验证
     * @param vin
     * @return
     */
    public static boolean isVin(String vin){
        if(vin==null){
            return false;
        }
        vin = vin.trim();
        if(vin.length()!=17){
            return false;
        }

        Pattern pattern = Pattern.compile("^[A-Z0-9]+$");
        Matcher matcher = pattern.matcher(vin.toUpperCase());

        return matcher.matches();
    }

    /**
     * 验证手机号
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Pattern pattern = Pattern.compile("^(13|14|15|17|18)\\d{9}$");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 隐藏手机号
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile){
        if(isMobile(mobile)){
            String pre = mobile.substring(0, 3);
            String suf = mobile.substring(7);
            return pre+"****"+suf;
        }
        return mobile;
    }

    public static String token() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        int random = (int)(Math.random() * 9999);
        return formatter.format(new Date()) + String.format("%04d", random);
    }

    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(PayMessage.DES_IV.getMessage().getBytes());
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }

    /**
     * 获取随机数字符串
     * @param len
     * @return
     */
    public static String getRandomNum(int len){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<len; i++){
            sb.append((int)(Math.random()*10));
        }
        return sb.toString();
    }

    /**
     * 根据长度totalLen，补充位数
     * @param seq
     * @param totalLen
     * @return
     */
    public static String supplementNum(int seq, int totalLen){
        String str = seq+"";
        int len = str.length();
        if(len>=totalLen){
            return str;
        }
        int n = totalLen - len;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++){
            sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }

    //转换比率(因为目前存储的是：例如 3% 数据库存储 3，所以这里统一除以100)
    public static BigDecimal getRate(BigDecimal rate){
        if(rate==null){
            return null;
        }
        return rate.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP);
    }
    //保留两位小数、四舍五入
    public static BigDecimal getDecimalHalfUp(BigDecimal decimal){
        if(decimal==null){
            return null;
        }
        return decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static String bytesToStr(byte[] bytes){
        if(bytes==null){
            return "";
        }
        int len = bytes.length;
        if(len==0){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<len; i++){
            sb.append(",").append(bytes[i]);
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }
    public static byte[] strToBytes(String str){
        if(StringUtils.isEmpty(str)){
            return new byte[0];
        }
        String[] strings = str.split(",");
        int len = strings.length;
        byte[] bytes = new byte[len];
        for(int i=0; i<len; i++){
            bytes[i] = Byte.parseByte(strings[i]);
        }
        return bytes;
    }

    //判断ajax请求
    public static boolean isAjaxRequest(HttpServletRequest request){
        String requestType = request.getHeader("X-Requested-With");
        //System.out.println(requestType);
        return "XMLHttpRequest".equals(requestType);
    }

}
