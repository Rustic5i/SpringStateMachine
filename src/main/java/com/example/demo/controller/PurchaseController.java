package com.example.demo.controller;

import com.example.demo.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @RequestMapping(path = "/reserve")
    public boolean reserve(final String userId, final String productId) {
        return purchaseService.reserved(userId, productId);
    }

    @RequestMapping(path = "/cancel")
    public boolean cancelReserve(final String userId) {
        return purchaseService.cancelReserve(userId);
    }

    @RequestMapping(path = "/buy")
    public boolean buyReserve(final String userId) {
        return purchaseService.buy(userId);
    }

}