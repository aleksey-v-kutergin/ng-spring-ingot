package ru.rusquant.ngingot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/** Общий контроллер приложения. Кто-то же должен возврашать index.html **/
@Controller
public class MvcController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/ng-spring-ingot", method = RequestMethod.GET)
    public ModelAndView indexBaseHref() {
       return new ModelAndView("index");
    }
}
