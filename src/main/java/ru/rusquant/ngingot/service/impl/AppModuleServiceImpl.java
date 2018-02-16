package ru.rusquant.ngingot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rusquant.ngingot.domain.AppModule;
import ru.rusquant.ngingot.repository.AppModuleRepository;
import ru.rusquant.ngingot.service.AppModuleService;

import java.util.List;

@Service
public class AppModuleServiceImpl implements AppModuleService {

    @Autowired
    private AppModuleRepository appModuleRepository;

    @Override
    public List<AppModule> getAllAppModules() {
        return appModuleRepository.findAll();
    }

}
