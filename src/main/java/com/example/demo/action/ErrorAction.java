package com.example.demo.action;

import com.example.demo.event.PurchaseEvent;
import com.example.demo.state.PurchaseState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ErrorAction implements Action<PurchaseState, PurchaseEvent> {
    @Override
    public void execute(final StateContext<PurchaseState, PurchaseEvent> context) {
        System.out.println("Ошибка при переходе в статус " + context.getTarget().getId());
    }
}
