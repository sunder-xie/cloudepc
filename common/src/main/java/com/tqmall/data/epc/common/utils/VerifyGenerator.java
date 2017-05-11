package com.tqmall.data.epc.common.utils;

/**
 * 数字验证码生成器
 * Created by huangzhangting on 16/6/5.
 */
public class VerifyGenerator {

    public static String genVerify(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<4; i++){
            sb.append((int)(Math.random()*10));
        }
        return sb.toString();
    }



    //main
    public static void main(String[] args){
        System.out.println(genVerify());
    }
}
