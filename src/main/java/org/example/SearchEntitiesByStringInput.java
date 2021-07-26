package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.domain.Entity;
import org.example.service.ServiceTransaction;

import java.util.List;

public class SearchEntitiesByStringInput {
    public TableView tblDrugs;
    public TableView tblTransactions;
    public TextField txtSearchText;

    private ServiceTransaction serviceTransaction;
    ObservableList<Entity> listResults = FXCollections.observableArrayList();

    public void setTransactionService(ServiceTransaction serviceTransaction) {
        this.serviceTransaction = serviceTransaction;
    }

    public void btnFullTextSearchClick(ActionEvent actionEvent) {
        List<Entity> entitiesResults = serviceTransaction.searchEntitiesByStringInput(txtSearchText.getText());
        listResults.addAll(entitiesResults);
        tblDrugs.setItems(listResults);
        tblTransactions.setItems(listResults);
    }
}
