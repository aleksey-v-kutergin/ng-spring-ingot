package ru.rusquant.ngingot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.rusquant.ngingot.domain.AppModule;
import ru.rusquant.ngingot.service.AppModuleService;

import java.util.List;

@RestController
@RequestMapping("/api/app/module")
public class AppModuleController {

    @Autowired
    private AppModuleService appModuleService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<AppModule> getAllAppModules() {
        return appModuleService.getAllAppModules();
    }
}
