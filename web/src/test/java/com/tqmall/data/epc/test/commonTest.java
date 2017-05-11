package com.tqmall.data.epc.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Maps;
import com.tqmall.core.common.errorcode.ErrorCodeBuilder;
import com.tqmall.core.common.errorcode.PlatformErrorCode;
import com.tqmall.data.epc.bean.param.order.pay.PayCallbackPO;
import com.tqmall.data.epc.bean.param.order.pay.PaymentPO;
import com.tqmall.data.epc.client.bean.param.order.PayCallbackParam;
import com.tqmall.data.epc.client.bean.param.order.PaymentParam;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.bean.HttpClientResult;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zxg on 16/2/2.
 * 11:02
 */
@Slf4j
public class commonTest {

    @Test
    public void test() {

        String url = "http://app.360cec.com/car/vin/LFV3A28E183004843";
//        String url = "http://app.360cec.com/car/vin/LFV3A289999999999";

        HttpClientResult result = HttpClientUtil.get(url);
        String resultData = result.getData();
        JsonNode jsonNode = JsonUtil.getJsonNode(resultData);
        JsonNode data = jsonNode.findValue("data");

        if (data.isNull()) {
            System.out.println("null");
        } else {
            JsonNode carObject = data.findValue("cars");
            if (carObject.isNull()) {
                System.out.println("null");
                return;
            }
            ArrayNode cars = (ArrayNode) carObject;
            for (JsonNode car : cars) {
                JsonNode idNode = car.findValue("id");
                Integer carId = idNode.intValue();
                System.out.println(carId + "");
            }
        }

        log.info("getCenterCarByPid from athena failed, code:{}, msg:{}, parameter pid:{}",
                EpcError.UN_KNOW_EXCEPTION.getCode(), EpcError.UN_KNOW_EXCEPTION.getErrorMessage(), 1234);
    }

    //正则
    @Test
    public void regular() {
        String value = "car\"+onmouseover=3+d=\"";
        value = value.replaceAll("onmouse[*]=", "");
        System.out.printf(value);
    }

    @Test
    public void testDecimal() {
        Integer goodsNumber = 5;
        BigDecimal taxRate = BigDecimal.valueOf(0.00);
        System.out.println(BigDecimal.ONE.add(taxRate));

        BigDecimal goodsPrice = BigDecimal.valueOf(88.23);
        BigDecimal amount = goodsPrice.multiply(BigDecimal.ONE.add(taxRate));
        System.out.println(amount);
        System.out.println(amount.setScale(2, BigDecimal.ROUND_HALF_UP));

        System.out.println(goodsPrice.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP));
    }


    @Test
    public void testNull() {
        String a = null;
        log.info("{} is hhh", BigDecimal.ZERO);
        System.out.println("".equals(a));
    }

    @Test
    public void testsubtract() {
        System.out.println(new BigDecimal("12.32"));
        Double a = 12.32;
        System.out.println(new BigDecimal(a));
        System.out.println(new BigDecimal(a.toString()));
        System.out.println(new BigDecimal(100));
        BigDecimal result = new BigDecimal(12.32).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
        System.out.println(result);
        System.out.println(BigDecimal.ONE.subtract(result));
    }

    @Test
    public void testBd() {
        PayCallbackParam payCallbackParam = new PayCallbackParam();
        payCallbackParam.setOrderSn("123123");
        payCallbackParam.setTotalFee(new BigDecimal(888));
        payCallbackParam.setSuccess(true);

        PaymentParam paymentParam = new PaymentParam();
        paymentParam.setPayId(4);
        paymentParam.setPayName("支付");

        payCallbackParam.setPayment(paymentParam);

        PayCallbackPO payCallbackPO = BdUtil.do2bo(payCallbackParam, PayCallbackPO.class);
        payCallbackPO.setPayment(BdUtil.do2bo(payCallbackParam.getPayment(), PaymentPO.class));

        System.out.println(JsonUtil.objectToJson(payCallbackPO));
    }


    @Test
    public void testSwitch() {
//        Integer test = 3;
//        switch (test) {
//            case 1:
//                System.out.println("1:" + test);
//            case 2:
//                System.out.println("2:" + test);
//            case 3:
//                System.out.println("3:" + test);
//                test = test + 2;
//            case 4:
//                System.out.println("4:" + test);
//            case 5:
//                System.out.println("5:" + test);
//            default:
//                System.out.println("finally:" + test);
//        }



        String orderSn = "123123-";
        System.out.println(orderSn + (int)(Math.random()*10000));
    }


    @Test
    public void testBean(){
        String json = "{\"oid_partner\":[\"201503031000226507\"],\"sign_type\":[\"MD5\"],\"sign\":[\"6f17f4891af8a9745e0fe209cbcd49a6\"],\"dt_order\":[\"20160905155346\"],\"no_order\":[\"Q16090593440008-17\"],\"oid_paybill\":[\"2016090515773022\"],\"money_order\":[\"0.01\"],\"settle_date\":[\"20160905\"],\"info_order\":[\"曲轴位置传感器\"],\"pay_type\":[\"D\"],\"bank_code\":[\"03080000\"],\"result_pay\":[\"SUCCESS\"]}";

        Map<String, List<String>> map = JsonUtil.jsonToObject(json, Map.class);

        System.out.println(map);

        Map<String, String[]> requestMap = new HashMap<>();

        for(Map.Entry<String, List<String>> entry : map.entrySet()){
            List<String> value = entry.getValue();
            requestMap.put(entry.getKey(), value.toArray(new String[value.size()]));
        }

        System.out.println(requestMap);



    }

    @Data
    public class Show{
        private String te;
    }
    private void doShow(Show show){
        show.setTe("ads");
    }



    @Test
    public void testRandom() throws ParseException {
        String sDt = "08/31/2016 23:59:00";
        SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dt2 = sdf.parse(sDt);

        Date nextDay = getOutStockTime(dt2);
        System.out.println(DateUtil.dateToString(nextDay, DateUtil.yyyy_MM_dd_HH_mm_ss));
    }

    private Date getOutStockTime(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        Random random = new Random();
        int s = random.nextInt(3)+1 ;

        System.out.println("random time = [" + s + "]");
        int oldMonth = dateTime.getMonthOfYear();
        DateTime newTime = dateTime.plusDays(s);

        System.out.println(DateUtil.dateToString(newTime.toDate(),DateUtil.yyyy_MM_dd_HH_mm_ss));


        int newMonth = newTime.getMonthOfYear();
        int day = newTime.getDayOfMonth();
        if (oldMonth != newMonth) {
            newTime = newTime.plusDays(-day);
        }
        int hour = dateTime.getHourOfDay();
        if (hour < 8) {
            newTime = newTime.plusHours(10);
        } else if (hour > 17) {
            newTime = newTime.plusHours(-10);
        }

        //如果 新的时间比 dateTime 时间小或者一样,即 此为最后一天 18:00 以后
        if(!newTime.isAfter(dateTime)){
            if(dateTime.getHourOfDay() == 23){
                if(dateTime.getMinuteOfHour() == 59){
                    //23:59 分，不能跨日，最后一天了
                    newTime = dateTime;
                }else{
                    //23 点，+1分钟，出库
                    newTime = dateTime.plusMinutes(1);
                }
            }else {
                //最后一天，在入库时间+1个小时 出库
                newTime = dateTime.plusHours(1);
            }
        }

        return newTime.toDate();
    }


    @Test
    public void testTime(){
        Map<String, String> map = getStartEndTime(1);
        System.out.println(map);

        System.out.println(isInSendSmsTime());


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);
        System.out.println(DateUtil.dateToString(calendar.getTime(), DateUtil.yyyy_MM_dd_HH_mm_ss));
    }

    private Map<String, String> genDoAutoPayTimeStartEndTime() {
        Map<String, String> map = Maps.newHashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);//分钟数数清零
        calendar.set(Calendar.SECOND, 0);//秒数清零

        map.put("endTime", DateUtil.dateToString(calendar.getTime(), DateUtil.yyyy_MM_dd_HH_mm_ss));

        calendar.add(Calendar.HOUR_OF_DAY, -5);//起始时间
        map.put("startTime", DateUtil.dateToString(calendar.getTime(), DateUtil.yyyy_MM_dd_HH_mm_ss));
        return map;
    }

    private Map<String, String> getStartEndTime(int day) {
        Map<String, String> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);//分钟数数清零
        calendar.set(Calendar.SECOND, 0);//秒数清零

        String df = DateUtil.yyyy_MM_dd_HH_mm_ss;
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        if (h == 21) {//晚上21点，要发送，21点到24点订单的提醒
            calendar.set(Calendar.HOUR_OF_DAY, 21);
            map.put("startTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));
            calendar.set(Calendar.HOUR_OF_DAY, 24);
            map.put("endTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));

        } else if (h == 8) {//早上8点，要发送，0点到9点订单的提醒
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            map.put("startTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            map.put("endTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));

        } else {//其他时间段，从当前小时，到下一个小时
            map.put("startTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));
            calendar.set(Calendar.HOUR_OF_DAY, h + 1);
            map.put("endTime", DateUtil.getFewDaysAgoOrAfterDate(calendar, day, df));

        }

        return map;
    }

    private boolean isInSendSmsTime() {
        Calendar now = Calendar.getInstance();
        int h = now.get(Calendar.HOUR_OF_DAY);
        System.out.println(h);
        return !(h >= 22 || h < 8);
    }


    @Test
    public void testErrorCode(){
        String a = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, "01")
                .setDetailCode("0001").buildErrorCode();
        String b = EpcError.UN_KNOW_EXCEPTION.getCode();

        System.out.println(a);
        System.out.println(b);
        System.out.println(EpcError.OPERATOR_SOURCE_ERROR.getCode());
        System.out.println(EpcError.NO_ORDER_ERROR.getCode());
        System.out.println(EpcError.AMOUNT_ERROR.getCode());
        System.out.println(EpcError.SHIPPING_ERROR.getCode());
        System.out.println(EpcError.ORDER_GOODS_ERROR.getCode());
        System.out.println(a.equals(b));

    }

    @Test
    public void testBigDecimal(){
        BigDecimal b1 = new BigDecimal("123.456777753413321231");
        String result = b1.toString();
        System.out.println("result = "+result);

        result = "";
        System.out.println(result.length());


        result = "  123qwe 123-qwe ";
        result = result.toUpperCase();
        System.out.println(result.replaceAll("[^0-9A-Z]", ""));
    }

    @Test
    public void testSub(){
        String fileName = "test.png";
        System.out.println(fileName.substring(0,fileName.lastIndexOf(".")));

        String test = "ksidsd+234";
        String b = test.replace("+","_");
        System.out.println(b);
    }

    @Test
    public void test_regexp(){
        String str = "CloudEpc_order_no_seq_1116";
        for(String key : RedisKeyBean.UN_FRESH_KEY_SET) {
            String newKey = key.replaceAll("%s", ".+").replaceAll("%d", ".+");
            System.out.println(key + "    "+newKey);

            Pattern pattern = Pattern.compile(newKey);
            Matcher matcher = pattern.matcher(str);
            System.out.println(matcher.matches());
        }
    }

    @Test
    public void testMD5(){
        String legendKeyPrefix = "0d0304d5b";
        String legendKeySuffix = "89e9e1348bef7";
        String param = "ewogICJjaXR5SWQiIDogNzYsCiAgIm1vYmlsZSIgOiAiMTU5NjgxMTUzNzciCn0=";

        String sign = "E4F37847848944B7236BAB3F9D370A11";

        String md5Str = MD5Utils.MD5(legendKeyPrefix + param + legendKeySuffix);
        System.out.println(md5Str.toUpperCase());
        System.out.println(md5Str.equalsIgnoreCase(sign));
    }
}
