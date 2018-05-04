package com.example.demo.mapper;

import com.example.demo.bean.DeliveryBillTraceLogInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by sgl on 18/5/2.
 */
@Repository
public interface DeliveryBillTraceLogMapper {
    public List<DeliveryBillTraceLogInfo> getInDeliveryBillTraceLogInfoList(Map<String,Object> map);


    public List<DeliveryBillTraceLogInfo> getOutDeliveryBillTraceLogInfoList(Map<String,Object> map);

}



