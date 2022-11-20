package com.example.demo.guard;

import com.example.demo.state.PurchaseState;
import com.example.demo.event.PurchaseEvent;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class HideGuard implements Guard<PurchaseState, PurchaseEvent> {

    @Override
    public boolean evaluate(StateContext<PurchaseState, PurchaseEvent> context) {
        return false;
    }
}