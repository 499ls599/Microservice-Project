package com.microservice.house.common.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import lombok.Data;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @ Description   :  返回页面信息（错误信息或者成功信息）
 * @ Author        :  1910959369@qq.com
 * @ CreateDate    :  2020/7/5 14:17
 */
//---------------------------------------------------------------------------------------------
@Data
public class ResultMsg {
    //---------------------------------------------------------------------------------------------
    public static final String errorMsgKey="errorMsg";

    public static final String successMsgKey="successMsg";

    private String errorMsg;

    private String successMsg;

    //---------------------------------------------------------------------------------------------
    //是否成功
    public boolean isSuccess(){
        return  errorMsg==null;
    }

    //---------------------------------------------------------------------------------------------
    public static ResultMsg errorMsg(String msg){
        ResultMsg resultMsg=new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }

    //---------------------------------------------------------------------------------------------
    public static ResultMsg successMsg(String msg){
        ResultMsg resultMsg=new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }

    //---------------------------------------------------------------------------------------------
    public Map<String,String>asMap(){
        Map<String,String>map= Maps.newHashMap();
        map.put(successMsgKey,successMsg);
        map.put(errorMsgKey,errorMsg);
        return map;
    }

    //---------------------------------------------------------------------------------------------
    //TODO  需要理解
    public String asUrlParams(){
        Map<String,String>map=asMap();
        Map<String,String>newMap=Maps.newHashMap();

        map.forEach((k,v)->{if(v!=null) {
            newMap.put(k, URLEncoder.encode("UTF-8"));
        }
        });
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }
}
