package ru.rusquant.ngingot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.rusquant.ngingot.service.HelloService;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello(ModelMap model) {
        String hello = helloService.getHelloMessage();
        model.addAttribute("message", hello);
        return hello;
    }
}
