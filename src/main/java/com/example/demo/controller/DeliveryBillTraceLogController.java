package com.example.demo.controller;


import com.example.demo.service.DeliveryBillTraceLogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sgl on 18/5/2.
 */

@Controller
@RequestMapping("/deliveryBillTraceLog")
public class DeliveryBillTraceLogController {

    @Autowired
    private DeliveryBillTraceLogService deliveryBillTraceLogService;


    @RequestMapping("/toDeliveryBillTraceLog")
    public ModelAndView toLogistics(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("deliveryBillTraceLog");
        return mv;
    }



    @RequestMapping("/getDeliveryTraceLogInfo")
    @ResponseBody
    public JSONObject getLogisticsInfo(String postid){
        return JSONObject.fromObject(deliveryBillTraceLogService.getDeliveryBillTraceLogInfoList(postid));
    }
}
