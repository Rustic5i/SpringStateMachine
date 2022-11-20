package com.example.demo.config;

import com.example.demo.action.BuyAction;
import com.example.demo.action.CancelAction;
import com.example.demo.event.PurchaseEvent;
import com.example.demo.state.PurchaseState;
import com.example.demo.action.ErrorAction;
import com.example.demo.action.ReservedAction;
import com.example.demo.guard.HideGuard;
import com.example.demo.listener.PurchaseStateMachineApplicationListener;
import com.example.demo.persist.PurchaseStateMachinePersister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;

import static com.example.demo.event.PurchaseEvent.*;
import static com.example.demo.state.PurchaseState.*;

/**
 * Конфигурация StateMachine
 */
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<PurchaseState, PurchaseEvent> {


    /**
     * У класса EnumStateMachineConfigurerAdapter есть несколько методов, переопределяя которые мы настраиваем машинку.
     * Для начала зарегистрируем статусы:
     *
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(final StateMachineStateConfigurer<PurchaseState, PurchaseEvent> states) throws Exception {
        states
                .withStates()
                // — это статус в котором будет находиться машина после создания bean-а,
                .initial(NEW)
                // — статус зайдя в который машина выполнит statemachine.stop(). Конец машины, работа закончина
                .end(PurchaseState.PURCHASE_COMPLETE)
                //Регестрируем все статусы/список всех статусов, можно пихать скопом
                .states(EnumSet.allOf(PurchaseState.class));

    }

    /**
     * Сконфигурим глобальный настройки машины.
     * в этом же конфиге можно задать отдельный TaskExecutor,
     * что удобно тогда, когда на каком-то из переходов выполняется долгое действие, а приложение должно идти дальше.
     *
     * @param config
     * @throws Exception
     */
    @Override
    public void configure(final StateMachineConfigurationConfigurer<PurchaseState, PurchaseEvent> config) throws Exception {
        config
                .withConfiguration()

                // Тут определяет будет ли запущена машина сразу после создания по-умолчанию,
                // иными словами — перейдет ли она автоматически в статус NEW(по умолчанию false)
                .autoStartup(true)

                //Тут же мы регистрируем listener для контекста машины
                .listener(new PurchaseStateMachineApplicationListener());
    }

    /**
     * Конфигуратор перехода конечного автомата.
     * Вся логика переходов или transitions задается тут,
     * на переходы можно навешивать Guard,
     * компонент который всегда возвращает boolean,
     * что именно вы будете проверять на переходе из одного статуса в другой на ваше усмотрение,
     * в Guard-е может быть совершенна любая логика, это совершенно обычный компонент,но вернуть он должен boolean.
     * <p>
     * В рамках нашего проекта, например, HideGuard может проверять некую настройку,
     * которую мог выставить пользователь(не показывать данный товар)и в соответствии
     * с ней не пропускать машину в state защищенный Guard-ом. Отмечу что Guard на один переход
     * в конфиге может быть добавлен только один, вот такая конструкция не сработает:
     * <p>
     * .withExternal()
     * .source(RESERVED)
     * .target(PURCHASE_COMPLETE)
     * .event(BUY)
     * .guard(hideGuard())
     * .guard(veryHideGuard())
     * <p>
     * Точнее сработает, но только первый guard(hideGuard())
     *
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(final StateMachineTransitionConfigurer<PurchaseState, PurchaseEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(NEW)
                .target(RESERVED)
                .event(RESERVE)
                .action(reservedAction(), errorAction())

                .and()
                .withExternal()
                .source(RESERVED)
                .target(CANCEL_RESERVED)
                .event(RESERVE_DECLINE)
                .action(cancelAction(), errorAction())

                .and()
                .withExternal()
                .source(RESERVED)
                .target(PURCHASE_COMPLETE)
                .event(BUY)

                //Guard-е может быть совершенна любая логика,
                // это совершенно обычный компонент, но вернуть он должен boolean.
                // В рамках нашего проекта, например, HideGuard может проверять некую настройку,
                // которую мог выставить пользователь(не показывать данный товар)и в соответствии
                // с ней не пропускать машину в state защищенный Guard-ом. Отмечу что Guard на один
                // переход в конфиге может быть добавлен только один
                .guard(hideGuard())

                //вторым аргументом идет ErrorAction, управление попадет к нему в случае если ReservedAction бросит исключение (throw е).
                //Грабли
                //Имейте в виду, что если в вашем Action,
                // вы все-таки обработаете ошибку, через try/catch, то в ErrorAction вы уже не зайдете,
                // если надо и обработать и зайти-таки в ErrorAction то следет бросить из catch RuntimeException(),
                .action(buyAction(), errorAction());
    }

    @Bean
    public Action<PurchaseState, PurchaseEvent> reservedAction() {
        return new ReservedAction();
    }

    @Bean
    public Action<PurchaseState, PurchaseEvent> cancelAction() {
        return new CancelAction();
    }

    @Bean
    public Action<PurchaseState, PurchaseEvent> buyAction() {
        return new BuyAction();
    }

    @Bean
    public Action<PurchaseState, PurchaseEvent> errorAction() {
        return new ErrorAction();
    }

    @Bean
    public Guard<PurchaseState, PurchaseEvent> hideGuard() {
        return new HideGuard();
    }

    @Bean
    public StateMachinePersister<PurchaseState, PurchaseEvent, String> persister() {
        return new DefaultStateMachinePersister<>(new PurchaseStateMachinePersister());
    }
}
