package com.example.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceA implements IServiceA{

    @Autowired
    private ServiceB serviceB;

    @Override
    public void test() {
        serviceB.test();
        String c = "dw";
    }
}