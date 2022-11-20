package com.example.demo.listener;

import com.example.demo.event.PurchaseEvent;
import com.example.demo.state.PurchaseState;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

public class PurchaseStateMachineApplicationListener implements StateMachineListener<PurchaseState, PurchaseEvent> {

    @Override
    public void stateChanged(State<PurchaseState, PurchaseEvent> from, State<PurchaseState, PurchaseEvent> to) {
        if (from.getId() != null) {
            System.out.println("Переход из статуса " + from.getId() + " в статус " + to.getId());
        }
    }

    @Override
    public void stateEntered(State<PurchaseState, PurchaseEvent> state) {

    }

    @Override
    public void stateExited(State<PurchaseState, PurchaseEvent> state) {

    }

    @Override
    public void eventNotAccepted(Message<PurchaseEvent> event) {
        System.out.println("Евент не принят " + event);
    }

    @Override
    public void transition(Transition<PurchaseState, PurchaseEvent> transition) {

    }

    @Override
    public void transitionStarted(Transition<PurchaseState, PurchaseEvent> transition) {

    }

    @Override
    public void transitionEnded(Transition<PurchaseState, PurchaseEvent> transition) {

    }

    @Override
    public void stateMachineStarted(StateMachine<PurchaseState, PurchaseEvent> stateMachine) {
        System.out.println("Machine started");
    }

    @Override
    public void stateMachineStopped(StateMachine<PurchaseState, PurchaseEvent> stateMachine) {

    }

    @Override
    public void stateMachineError(StateMachine<PurchaseState, PurchaseEvent> stateMachine, Exception exception) {

    }

    @Override
    public void extendedStateChanged(Object key, Object value) {

    }

    @Override
    public void stateContext(StateContext<PurchaseState, PurchaseEvent> stateContext) {

    }
}
