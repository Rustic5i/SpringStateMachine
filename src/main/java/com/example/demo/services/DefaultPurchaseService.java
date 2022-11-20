package com.example.demo.services;

import com.example.demo.event.PurchaseEvent;
import com.example.demo.state.PurchaseState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import static com.example.demo.event.PurchaseEvent.*;

@Service
@SuppressWarnings("all")
public class DefaultPurchaseService implements PurchaseService {

    private final StateMachinePersister<PurchaseState, PurchaseEvent, String> persister;

    private final StateMachineFactory<PurchaseState, PurchaseEvent> stateMachineFactory;

    public DefaultPurchaseService(StateMachinePersister<PurchaseState, PurchaseEvent, String> persister, StateMachineFactory<PurchaseState, PurchaseEvent> stateMachineFactory) {
        this.persister = persister;
        this.stateMachineFactory = stateMachineFactory;
    }


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
        System.out.println("WEFDWA дольтдл  дль ");
        final StateMachine<PurchaseState, PurchaseEvent> stateMachine = stateMachineFactory.getStateMachine();
        try {
            persister.restore(stateMachine, userId);
            stateMachine.sendEvent(RESERVE_DECLINE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean buy(String userId) {
        final StateMachine<PurchaseState, PurchaseEvent> stateMachine = stateMachineFactory.getStateMachine();
        try {
            persister.restore(stateMachine, userId);
            stateMachine.sendEvent(BUY);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
