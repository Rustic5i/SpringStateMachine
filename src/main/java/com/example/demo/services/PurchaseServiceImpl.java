package com.example.demo.services;

import com.example.demo.PurchaseEvent;
import com.example.demo.PurchaseState;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import static com.example.demo.PurchaseEvent.RESERVE;

@Component
public class PurchaseServiceImpl implements PurchaseService{

    @Override
    public boolean reserved(String userId, String productId) {
        final StateMachine<PurchaseState, PurchaseEvent> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.getExtendedState().getVariables().put("PRODUCT_ID", productId);
        stateMachine.sendEvent(RESERVE);
        try {
            persister.persist(stateMachine, userId);
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean cancelReserve(String userId) {
        return false;
    }

    @Override
    public boolean buy(String userId) {
        return false;
    }
}
