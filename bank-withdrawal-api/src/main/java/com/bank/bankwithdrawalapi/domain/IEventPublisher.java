package com.bank.bankwithdrawalapi.domain;

/**
 * Represents a mechanism for publishing events related to withdrawals.
 * The implementation of this interface is responsible for handling
 * how and where these events are delivered to notify other components
 * or external systems about withdrawal activities.
 */
public interface IEventPublisher {
    /**
     * Publishes a withdrawal event to notify other components or external systems.
     *
     * @param event the WithdrawalEvent object containing details about the withdrawal, including the amount, account ID, and status.
     */
    public void publishEvent(WithdrawalEvent event);
}
