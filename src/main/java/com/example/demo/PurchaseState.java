package com.example.demo;

/**
 * Состояние покупки
 */
public enum PurchaseState {

    /**
     * Новый
     */
    NEW,

    /**
     * ЗАРЕЗЕРВИРОВАННЫЙ
     */
    RESERVED,

    /**
     * ОТМЕНА ЗАРЕЗЕРВИРОВАНО
     */
    CANCEL_RESERVED,

    /**
     * ПОКУПКА ЗАВЕРШЕНА
     */
    PURCHASE_COMPLETE
}
