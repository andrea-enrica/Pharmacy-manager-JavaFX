package org.example.domain;

public class ClientCardNumberCardWithNumberOfTransactions {
    private int clientCardNumber;
    private int numberOfTransactions;

    public ClientCardNumberCardWithNumberOfTransactions(int clientCardNumber, int numberOfTransactions) {
        this.clientCardNumber = clientCardNumber;
        this.numberOfTransactions = numberOfTransactions;
    }

    public int getClientCardNumber() {
        return clientCardNumber;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    @Override
    public String toString() {
        return "ClientNumberCardWithNumberOfTransactions{" +
                "clientCardNumber=" + clientCardNumber +
                ", numberOfTransactions=" + numberOfTransactions +
                '}';
    }
}
