package com.example.demo.service;

import com.example.demo.bean.Result;

/**
 * Created by sgl on 18/5/2.
 */
public interface DeliveryBillTraceLogService {

    /**
     * 通过运单号查询运单日志信息
     * @param deliveryCode
     * @return
     */
  public Result getDeliveryBillTraceLogInfoList(String deliveryCode);

}
