package com.example.demo.service.impl;

import com.example.demo.bean.DeliveryBillTraceLogInfo;
import com.example.demo.bean.Result;
import com.example.demo.mapper.DeliveryBillTraceLogMapper;
import com.example.demo.service.DeliveryBillTraceLogService;
import com.example.demo.util.HttpClientUtils;
import com.example.demo.util.MD5Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sgl on 18/5/2.
 */
@Service
public class DeliveryBillTraceLogServiceImpl  implements DeliveryBillTraceLogService{


    public static final String GETCOMCODE_URL="http://www.kuaidi100.com/autonumber/auto";

    public static final String KEY="qlyvvBXO4320";

    public static final String GETLOG_URL="http://poll.kuaidi100.com/poll/query.do";

    public final static String CUSTOMER = "BE07EB4D92E5FC9DB3B0E8E553CF41DF";

    public static final String MESSAGE="message";

   @Autowired
   private DeliveryBillTraceLogMapper deliveryBillTraceLogMapper;

    /**
     * 运单信息
     * @param deliveryCode
     * @return
     */
    @Override
    public Result getDeliveryBillTraceLogInfoList(String deliveryCode){
        Result result=new Result();
        if("".equals(deliveryCode)){
            result.setStatus("500");
            result.setMessage("参数运单号为空");
            return result;
        }
        result.setCom("云村通");
        result.setCondition("Foo");
        result.setIscheck("1");
        result.setMessage("ok");
        result.setState("3");
        result.setStatus("200");
        List<DeliveryBillTraceLogInfo> deliveryBillTraceLogMapperList=new ArrayList<>();
        Map map=new HashMap<>();
        map.put("deliveryCode",deliveryCode);

        //判断是否已字母开头
        if((int)deliveryCode.charAt(0)>=65 && (int)deliveryCode.charAt(0)<=90){
            map.put("inOrOut","1");//运单信息
            deliveryBillTraceLogMapperList=  deliveryBillTraceLogMapper.getInDeliveryBillTraceLogInfoList(map);
        }else {//寄件单
            map.put("inOrOut","2");//寄件单信息
            deliveryBillTraceLogMapperList=  deliveryBillTraceLogMapper.getOutDeliveryBillTraceLogInfoList(map);
        }
        if(deliveryBillTraceLogMapperList!=null&&deliveryBillTraceLogMapperList.size()>0){
            result.setData(getKuaiDiLogInfo(deliveryBillTraceLogMapperList,deliveryBillTraceLogMapperList.get(0).getCourierNo()));
        }
        if(deliveryBillTraceLogMapperList==null||deliveryBillTraceLogMapperList.size()==0){
            result.setMessage("暂无信息");
            result.setStatus("500");
        }


//        deliveryBillTraceLogMapperList=  deliveryBillTraceLogMapper.getInDeliveryBillTraceLogInfoList(map);
//        if(deliveryBillTraceLogMapperList!=null&&deliveryBillTraceLogMapperList.size()>0){
//            result.setData(deliveryBillTraceLogMapperList);
//        }else {
//            map.put("inOrOut","2");//寄件单信息
//            deliveryBillTraceLogMapperList=  deliveryBillTraceLogMapper.getOutDeliveryBillTraceLogInfoList(map);
//            if(deliveryBillTraceLogMapperList!=null&&deliveryBillTraceLogMapperList.size()>0){
//                result.setData(getKuaiDiLogInfo(deliveryBillTraceLogMapperList,deliveryBillTraceLogMapperList.get(0).getCourierNo()));
//
//            }else {
//                result.setMessage("暂无信息");
//                result.setStatus("500");
//            }
//        }
        return   result;
    }



    /**
     * 获取快递100运单信息
     * @param deliveryCode
     * @return
     */
    public static  List<DeliveryBillTraceLogInfo>  getKuaiDiLogInfo(List<DeliveryBillTraceLogInfo> deliveryBillTraceLogInfos,String deliveryCode){
        Map params=new HashMap();
        params.put("num",deliveryCode);
        params.put("key",KEY);
        //通过快递号获取快递公司编码
        String comCoderesult= HttpClientUtils.doPost(GETCOMCODE_URL,params);
        JSONArray comCodeArray=JSONArray.fromObject(comCoderesult);
        String comCode="";
        if(comCodeArray!=null&&comCodeArray.size()>0){
            comCode=comCodeArray.getJSONObject(0).getString("comCode");
        }else {
            return deliveryBillTraceLogInfos;
        }
        //获取快递信息
        String param ="{\"com\":\"{1}\",\"num\":\"{2}\"}";
        param=param.replace("{1}",comCode).replace("{2}",deliveryCode);
        String sign = MD5Util.toMD5(param+KEY+CUSTOMER);
        params=new HashMap();
        params.put("param",param);
        params.put("sign",sign);
        params.put("customer",CUSTOMER);
        String logResult=HttpClientUtils.doPost(GETLOG_URL,params);
        JSONObject resultJsonObject= JSONObject.fromObject(logResult);
        if(resultJsonObject!=null&&"ok".equals(resultJsonObject.getString(MESSAGE))){
            JSONArray jsonArray=resultJsonObject.getJSONArray("data");
            if(jsonArray!=null&&jsonArray.size()>0){
                for (int i=0;i<jsonArray.size();i++){
                    DeliveryBillTraceLogInfo deliveryBillTraceLogInfo=new DeliveryBillTraceLogInfo();
                    deliveryBillTraceLogInfo.setLocation(jsonArray.getJSONObject(i).getString("context"));
                    deliveryBillTraceLogInfo.setContext(jsonArray.getJSONObject(i).getString("context"));
                    deliveryBillTraceLogInfo.setTime(jsonArray.getJSONObject(i).getString("time"));
                    deliveryBillTraceLogInfo.setFtime(jsonArray.getJSONObject(i).getString("time"));
                    deliveryBillTraceLogInfos.add(deliveryBillTraceLogInfo);
                }
            }else {
                return deliveryBillTraceLogInfos;
            }
        }else {
            return  deliveryBillTraceLogInfos;
        }
        return deliveryBillTraceLogInfos;
    }

}
