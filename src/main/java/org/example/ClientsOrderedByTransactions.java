package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableView;

import org.example.domain.ClientCardNumberCardWithNumberOfTransactions;
import org.example.service.ServiceTransaction;

public class ClientsOrderedByTransactions {
    public TableView tblClientWithTransactions;
    private ServiceTransaction serviceTransaction;

    public void setServiceTransactions(ServiceTransaction serviceTransaction) {
        this.serviceTransaction = serviceTransaction;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            ObservableList<ClientCardNumberCardWithNumberOfTransactions> clientsWithTransactions = FXCollections.observableArrayList();
            clientsWithTransactions.addAll(serviceTransaction.getClientNumberCardOrderedByNumberOfTransactions());
            tblClientWithTransactions.setItems(clientsWithTransactions);
        });
    }
}