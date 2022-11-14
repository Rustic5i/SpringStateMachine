package com.example.demo.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceB implements IQServiceB  {


    {
        System.out.println("Я готов");
    }

    @Override
    public void test() {
        String c = "daw";
    }
}
