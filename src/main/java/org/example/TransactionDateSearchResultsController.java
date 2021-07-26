package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.domain.Entity;
import org.example.domain.Transaction;
import org.example.service.ServiceTransaction;

public class TransactionDateSearchResultsController {

    public TableView tblTransactions;
    public TextField txtDateHourStart;
    public TextField txtDateHourEnd;

    private ServiceTransaction serviceTransaction;
    ObservableList<Transaction> transactionsListResults = FXCollections.observableArrayList();

    public void setTransactionService(ServiceTransaction serviceTransaction ) {
        this.serviceTransaction = serviceTransaction;
    }

    public void btnTransactionDateHourSearchClick(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(txtDateHourStart.getText(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(txtDateHourEnd.getText(), formatter);

        List<Transaction> transactionListWithinRangeOfDays = this.serviceTransaction.displaysTransactionsBetweenTwoDateAndTimes(startDate, endDate);        transactionsListResults.addAll(transactionListWithinRangeOfDays);
        tblTransactions.setItems(transactionsListResults);
    }
}