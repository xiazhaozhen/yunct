package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sgl on 18/5/11.
 */
@Controller
@RequestMapping("/random")
public class RandomController {

    @RequestMapping("/toRandomPage")
    public ModelAndView toRandomPage(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("random");
        return mv;
    }



}
