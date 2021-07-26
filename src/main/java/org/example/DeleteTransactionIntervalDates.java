package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.domain.Transaction;
import org.example.service.ServiceTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DeleteTransactionIntervalDates {
    public TextField txtDateStart;
    public TextField txtDateEnd;
    public TableView tblTransactionsDelete;

    private ServiceTransaction serviceTransaction;
    ObservableList<Transaction> transactionsListResults = FXCollections.observableArrayList();

    public void setServiceTransactions(ServiceTransaction serviceTransaction) {
        this.serviceTransaction = serviceTransaction;
    }

    public void btnTransactionDateDeleteClick(ActionEvent actionEvent) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = formatter.parse(txtDateStart.getText());
        Date endDate = formatter.parse(txtDateEnd.getText());

        this.serviceTransaction.deleteTransactionsBetweenTwoDates(startDate, endDate);
        List<Transaction> transactionListWithinRangeOfDays = this.serviceTransaction.getAll();
        transactionsListResults.addAll(transactionListWithinRangeOfDays);
        tblTransactionsDelete.setItems(transactionsListResults);
    }
}