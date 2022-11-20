package com.example.demo.action;

import com.example.demo.event.PurchaseEvent;
import com.example.demo.state.PurchaseState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ReservedAction implements Action<PurchaseState, PurchaseEvent> {
    @Override
    public void execute(StateContext<PurchaseState, PurchaseEvent> context) {
        final String productId = context.getExtendedState().get("PRODUCT_ID", String.class);
        System.out.println("Товар с номером " + productId + " зарезервирован.");
    }
}
