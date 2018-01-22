package ru.rusquant.ngingot.service.impl;


import org.springframework.stereotype.Service;
import ru.rusquant.ngingot.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String getHelloMessage() {
        return "Hello from NgSpringIngot!!!";
    }

}
