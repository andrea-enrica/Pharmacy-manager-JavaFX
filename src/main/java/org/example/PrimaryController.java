package org.example;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Drug;
import org.example.domain.Transaction;
import org.example.service.ServiceDrug;
import org.example.service.ServiceTransaction;
import org.example.service.UndoRedoManager;

public class PrimaryController {
    public TextField txtDrugId;
    public TextField txtDrugName;
    public TextField txtDrugProducer;
    public TextField txtDrugPrice;
    public TextField txtDrugStock;
    public CheckBox chkWithoutRecipe;
    public TableView tblDrugs;
    public TableColumn colDrugId;
    public TableColumn colDrugName;
    public TableColumn colDrugProducer;
    public TableColumn colDrugPrice;
    public TableColumn colDrugStock;
    public TableColumn colWithoutRecipe;
    public Button btnAddDrug;
    public Button btnUpdateDrug;
    public Button btnDeleteSelected;
    public TableView tblTransactions;
    public TableColumn colIdTransaction;
    public TableColumn colIdDrug;
    public TableColumn colClientCard;
    public TableColumn colNumberOfPieces;
    public TableColumn colDateAndHour;
    public TextField txtTransactionId;
    public TextField txtIdDrug;
    public TextField txtClientCard;
    public TextField txtNumberOfPieces;
    public TextField txtDateAndHour;
    public Button btnAddTransaction;
    public Button btnUpdateTransaction;
    public Button btnDeleteSelectedTransaction;
    public TextField txtSearchText;
    public Button btnFullTextSearch;
    public TextField txtDateHourStart;
    public TextField txtDateHourEnd;
    public Button btnTransactionDateHourSearch;

    private ServiceDrug drugService;
    private ServiceTransaction transactionService;

    private ObservableList<Drug> drugs = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private UndoRedoManager undoRedoManager;


    @FXML
    private void initialize() {
        //initialize se apeleaza cand se creeaza controllerul
        Platform.runLater(() -> {
            drugs.addAll(drugService.getAll());
            transactions.addAll(transactionService.getAll());
            //ii spun la table drugs sa-si ia itemurile cu setItems din acel ObservableList
            tblDrugs.setItems(drugs);
            tblTransactions.setItems(transactions);
        });
    }

    public void setServices(ServiceDrug drugService, ServiceTransaction transactionService, UndoRedoManager undoRedoManager) {
        this.drugService = drugService;
        this.transactionService = transactionService;
        this.undoRedoManager = undoRedoManager;
    }

    public void btnAddDrugClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtDrugId.getText());
            String name = txtDrugName.getText();
            String producer = txtDrugProducer.getText();
            float price = Float.parseFloat(txtDrugPrice.getText());
            boolean recipe = chkWithoutRecipe.isSelected();
            int stock = Integer.parseInt(txtDrugStock.getText());

            drugService.addDrug(id, name, producer, price,recipe,stock);

            refreshDrugList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnUpdateDrugClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtDrugId.getText());
            String name = txtDrugName.getText();
            String producer = txtDrugProducer.getText();
            float price = Float.parseFloat(txtDrugPrice.getText());
            boolean recipe = chkWithoutRecipe.isSelected();
            int stock = Integer.parseInt(txtDrugStock.getText());

            drugService.updateDrug(id, name, producer, price,recipe,stock);

            refreshDrugList();
        } catch (RuntimeException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnDeleteSelectedDrugClick(ActionEvent actionEvent) {
        Drug selectedDrug = (Drug) tblDrugs.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            drugService.deleteDrug(selectedDrug.getIdEntity(), selectedDrug.getDrugName(), selectedDrug.getDrugProducer(), selectedDrug.getDrugPrice(), selectedDrug.isWithoutRecipe(), selectedDrug.getDrugStock());
            refreshDrugList();
        }
    }

    public void btnAddTransactionClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtTransactionId.getText());
            int idDrug = Integer.parseInt(txtIdDrug.getText());
            int clientCard = Integer.parseInt(txtClientCard.getText());
            int numberOfPieces = Integer.parseInt(txtNumberOfPieces.getText());
            String dateAndHour = txtDateAndHour.getText();

            transactionService.addTransaction(id, idDrug, clientCard, numberOfPieces, dateAndHour);

            refreshTransactionList();
        } catch (Exception ex) {
            Common.showErrorMessage(ex.getMessage());
        }
    }

    public void btnUpdateTransactionClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtTransactionId.getText());
            int idDrug = Integer.parseInt(txtIdDrug.getText());
            int clientCard = Integer.parseInt(txtClientCard.getText());
            int numberOfPieces = Integer.parseInt(txtNumberOfPieces.getText());
            String dateAndHour = txtDateAndHour.getText();

            transactionService.updateTransaction(id, idDrug, clientCard, numberOfPieces, dateAndHour);

            refreshTransactionList();
        } catch (Exception ex) {
            Common.showErrorMessage(ex.getMessage());
        }
    }

    public void btnDeleteSelectedTransactionClick(ActionEvent actionEvent) {
        Transaction selectedTransaction = (Transaction) tblTransactions.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            transactionService.deleteTransaction(selectedTransaction.getIdEntity(), selectedTransaction.getDrugId(), selectedTransaction.getClientCardNumber(), selectedTransaction.getPiecesOfDrug(), selectedTransaction.getDateAndHour());
            refreshTransactionList();
        }
    }

    //TODO: separate drug results from transaction results
    public void btnFullTextSearchClick(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("searchEntitiesByStringInput.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            //ii dam acces la controller
            SearchEntitiesByStringInput resultsController = fxmlLoader.getController();
            resultsController.setTransactionService(this.transactionService);

            stage.setTitle("Entities display by string input");
            stage.setScene(scene);
            stage.showAndWait();    //ca sa nu se poata lucra cu ambele ferestre in acelasi timp

        } catch (RuntimeException | IOException rex) {
            Common.showErrorMessage(rex.getMessage());
        }

    }

    public void btnTransactionDateHourSearchClick(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("transactionDateSearchResults.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            //ii dam acces la controller
            TransactionDateSearchResultsController resultsController = fxmlLoader.getController();
            resultsController.setTransactionService(this.transactionService);

            stage.setTitle("Transaction date search results");
            stage.setScene(scene);
            stage.showAndWait(); //ca sa nu se poata lucra cu ambele ferestre in acelasi timp

        } catch (RuntimeException | IOException rex) {
            Common.showErrorMessage(rex.getMessage());
        }
    }

    public void btnShowDrugsWithNumberOfTransactionsClick(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("drugsWithNumberOfTransactions.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            //ii dam acces la controller
            DrugsOrderedByTransactions resultsController = fxmlLoader.getController();
            resultsController.setServiceDrugs(this.drugService);

            stage.setTitle("Drug with transaction results");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException iex) {
            Common.showErrorMessage(iex.getMessage());        }
    }

    public void btnShowClientCardWithNumberOfTransactionsClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("clientsOrderedByTransactions.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            //ii dam acces la controller
            ClientsOrderedByTransactions resultsController = fxmlLoader.getController();
            resultsController.setServiceTransactions(this.transactionService);

            stage.setTitle("Client Card with transaction results");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException iex) {
            Common.showErrorMessage(iex.getMessage());        }
    }

    public void btnDeleteTransactionDateSearch(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("deleteTransactionIntervalDates.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            //ii dam acces la controller
            DeleteTransactionIntervalDates resultsController = fxmlLoader.getController();
            resultsController.setServiceTransactions(this.transactionService);

            stage.setTitle("Transaction results after delete option:");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException iex) {
            Common.showErrorMessage(iex.getMessage());        }
    }

    public void btnIncreasePrice(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("increaseDrugPrice.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();

            //ii dam acces la controller
            IncreaseDrugPrice resultsController = fxmlLoader.getController();
            resultsController.setServiceDrugs(this.drugService);

            stage.setTitle("Drug results after increased price:");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException iex) {
            Common.showErrorMessage(iex.getMessage());        }
    }

    private void refreshDrugList() {
        drugs.clear();
        drugs.addAll(drugService.getAll());

        txtDrugId.clear();
        txtDrugName.clear();
        txtDrugProducer.clear();
        txtDrugPrice.clear();
        txtDrugStock.clear();
        chkWithoutRecipe.setSelected(false);
    }

    private void refreshTransactionList() {
        transactions.clear();
        transactions.addAll(transactionService.getAll());

        txtTransactionId.clear();
        txtIdDrug.clear();
        txtClientCard.clear();
        txtNumberOfPieces.clear();
        txtDateAndHour.clear();
    }

    public void btnUndoClick(ActionEvent actionEvent) throws Exception {
        this.undoRedoManager.doUndo();
        this.refreshDrugList();
        this.refreshTransactionList();
    }

    public void btnRedoClick(ActionEvent actionEvent) throws Exception {
        this.undoRedoManager.doRedo();

        this.refreshDrugList();
        this.refreshTransactionList();
    }
}
