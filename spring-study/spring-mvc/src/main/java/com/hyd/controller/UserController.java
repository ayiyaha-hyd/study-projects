package com.hyd.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyd.domain.User;
import com.hyd.domain.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @RequestMapping("upload2")
    public void upload2(){

    }

    @RequestMapping("upload")
    @ResponseBody
    public void upload(String username, @RequestParam("f1") MultipartFile file1){
        System.out.println(username);
        System.out.println(file1.getName());
        System.out.println(file1.getContentType());
    }
    @RequestMapping("test7")
    @ResponseBody
    public String test7(@RequestHeader(value = "User-Agent",required = false) String header){
        return header;
    }

    @RequestMapping("test6")
    @ResponseBody
    public String test6(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "User-Agent",required = false) String header){
        String header1 = request.getHeader("User-Agent");
        System.out.println(request);
        System.out.println(header);
        System.out.println(header1);
        return header+"\t"+header1+"\t"+response;
    }

    @RequestMapping("test5")
    @ResponseBody
    public List<User> test5(@RequestBody List<User> userList) throws JsonProcessingException {
        System.out.println(userList);
        return userList;
    }

    @RequestMapping("test4")
    @ResponseBody
    public List<User> test4(UserVo userVo) throws JsonProcessingException {
        List<User> userList = userVo.getUserList();
        return userList;
    }
    @RequestMapping("test3")
    @ResponseBody
    public User test3(User user) throws JsonProcessingException {

        return user;
    }

    @RequestMapping("test2")
    @ResponseBody
    public User test2(String username,int age) throws JsonProcessingException {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        return user;
    }

    @RequestMapping("test1")
    @ResponseBody
    public User test1() throws JsonProcessingException {
        User user = new User();
        user.setUsername("xiaoyu");
        user.setAge(18);
        return user;
    }

    @RequestMapping("test")
    @ResponseBody
    public String test() throws JsonProcessingException {
        User user = new User();
        user.setUsername("xiaoyu");
        user.setAge(18);
        String userStr = new ObjectMapper().writeValueAsString(user);
        return userStr;
    }

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
