package com.tqmall.data.epc.common.utils;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.core.common.errorcode.ErrorCode;
import com.tqmall.data.epc.common.bean.EpcError;

import java.util.Collection;
import java.util.List;

/**
 * Created by huangzhangting on 16/1/26.
 */
public class ResultUtil {
    public static <D> Result<D> errorResult(String code, String message) {
        return Result.wrapErrorResult(code, message);
    }

    public static <D> Result<D> errorResult(ErrorCode errorCode) {
        return Result.wrapErrorResult(errorCode.getCode(), errorCode.getErrorMessage());
    }

    public static <D> Result<D> errorResult(String code, String message, D data) {
        Result<D> result = Result.wrapErrorResult(code, message);
        result.setData(data);
        return result;
    }

    public static <D> Result<D> errorResult(ErrorCode errorCode, D data) {
        Result<D> result = Result.wrapErrorResult(errorCode.getCode(), errorCode.getErrorMessage());
        result.setData(data);
        return result;
    }

    public static <D> Result<D> errorResult(Result result){
        return Result.wrapErrorResult(result.getCode(), result.getMessage());
    }

    public static <D> Result<D> successResult(D data) {
        return Result.wrapSuccessfulResult(data);
    }

    public static <D> Result<D> successResult(D data, String message) {
        return Result.wrapSuccessfulResult(message, data);
    }

    public static <DO, BO> Result<List<BO>> successResult4List(Collection<DO> collection, Class<BO> boClass) {
        return successResult(BdUtil.do2bo4List(collection, boClass));
    }

    public static <DO, BO> Result<List<BO>> handleResult4List(Result<List<DO>> result, Class<BO> boClass){
        if(result.isSuccess()){
            return successResult(BdUtil.do2bo4List(result.getData(), boClass));
        }
        return errorResult(result);
    }

    public static <DO, BO> Result<BO> handleResult(Result<DO> result, Class<BO> boClass){
        if(result.isSuccess()){
            return successResult(BdUtil.do2bo(result.getData(), boClass));
        }
        return errorResult(result);
    }

    public static <D> PagingResult<D> pageErrorResult(ErrorCode errorCode) {
        return PagingResult.wrapErrorResult(errorCode.getCode(), errorCode.getErrorMessage());
    }


    //可以详细区别具体异常，返回相应的异常错误
    public static <D> Result<D> handleException(Exception e){
        if(e instanceof NullPointerException){
            return errorResult(EpcError.NPE_ERROR);
        }
        if(e instanceof IndexOutOfBoundsException){
            return errorResult(EpcError.ARRAY_OUT_OF_BOUNDS);
        }


        return errorResult(EpcError.UN_KNOW_EXCEPTION);
    }
}
