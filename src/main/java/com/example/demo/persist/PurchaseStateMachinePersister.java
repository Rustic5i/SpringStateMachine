package com.example.demo.persist;

import com.example.demo.event.PurchaseEvent;
import com.example.demo.state.PurchaseState;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;


/**
 * Следующий важный момент, это то как вы будете восстанавливать состояние своей машины,
 * ведь на каждое обращение будет создан новый bean,
 * который ничего про ваши предыдущие статусы машины и ее контекст не знает.
 * Для этих целей в spring statemachine есть механизм Persistens:
 * <p>
 * Для нашей наивной реализации мы используем в качестве хранилища состояний обычную Map,
 * в ненаивной реализации это будет какая-то БД, обратите внимание на третий generic типа String,
 * это ключ по которому будет сохраняться состояние вашей машины, со всеми статусами, переменными в контексте,
 * id и тд. В своей примере я использовал id пользователя для ключа сохранения,
 * что может быть указан совершенно любой ключ(session_id пользователя, уникальный логин и т.д.).
 */
public class PurchaseStateMachinePersister implements StateMachinePersist<PurchaseState, PurchaseEvent, String> {

    /**
     * Для нашей наивной реализации мы используем в качестве хранилища состояний обычную Map,
     * в ненаивной реализации это будет какая-то БД, обратите внимание на третий generic типа String,
     * это ключ по которому будет сохраняться состояние вашей машины, со всеми статусами, переменными в контексте,
     * id и тд. В своей примере я использовал id пользователя для ключа сохранения,
     * что может быть указан совершенно любой ключ(session_id пользователя, уникальный логин и т
     */
    private final HashMap<String, StateMachineContext<PurchaseState, PurchaseEvent>> contexts = new HashMap<>();

    @Override
    public void write(StateMachineContext<PurchaseState, PurchaseEvent> stateMachineContext, String contextObj) throws Exception {
        contexts.put(contextObj, stateMachineContext);
    }

    @Override
    public StateMachineContext<PurchaseState, PurchaseEvent> read(String contextObj) throws Exception {
        return contexts.get(contextObj);
    }
}
