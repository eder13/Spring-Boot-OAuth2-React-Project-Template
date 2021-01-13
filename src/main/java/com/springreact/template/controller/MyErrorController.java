package com.springreact.template.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/// Remaps: "NOT FOUND" maps always to index.html -> React (handle not found messages in React!)

@Controller
public class MyErrorController implements ErrorController {

    @ResponseBody
    @RequestMapping("/error")
    public Object error(@RequestParam(required = false) String message, HttpServletRequest httpServletRequest) {

        // if no parameter applied (=standard 404 error), sent back index.html
        // else return JSON with error text
        if (message != null) {
            if (message.equals("true")) {
                String errorMessage = (String) httpServletRequest.getSession().getAttribute("error.message");
                httpServletRequest.getSession().removeAttribute("error.message");
                return errorMessage;
            }
            else {
                return new ModelAndView("index");
            }
        } else {
            return new ModelAndView("index");
        }
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
