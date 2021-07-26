package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.domain.DrugsWithNumberOfTransactions;
import org.example.service.ServiceDrug;


public class DrugsOrderedByTransactions {
    public TableView tblDrugsWithTransactions;
    private ServiceDrug serviceDrug;

    public void setServiceDrugs(ServiceDrug drugService) {
        this.serviceDrug = drugService;
    }

    @FXML
    private void initialize() {
        //initialize method is called when controller is create
        Platform.runLater(() -> {
            ObservableList<DrugsWithNumberOfTransactions> drugsWithTransactions = FXCollections.observableArrayList();
            drugsWithTransactions.addAll(serviceDrug.getDrugsOrderedByNumberOfTransactions());
            //i tell to the table drugs to get his items with setItems from that observable list
            tblDrugsWithTransactions.setItems(drugsWithTransactions);
        });
    }
}
