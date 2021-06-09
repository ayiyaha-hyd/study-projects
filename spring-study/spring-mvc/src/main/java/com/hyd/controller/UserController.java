package com.hyd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("save2")
    public ModelAndView save2(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        modelAndView.addObject("username","xiaoyu");
        return modelAndView;
    }

    @RequestMapping(value = "save",method = RequestMethod.GET,params = {"username"})
    public String save(){
        System.out.println("controller save() running ...");
        return "success";
    }
}
