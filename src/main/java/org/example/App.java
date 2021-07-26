package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.domain.Drug;
import org.example.domain.Transaction;
import org.example.domain.TransactionValidator;
import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;
import org.example.service.ServiceDrug;
import org.example.service.ServiceTransaction;
import org.example.service.UndoRedoManager;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //metoda care se apeleaza la pornirea aplicatiei
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Parent parentFxml = fxmlLoader.load();
        this.scene = new Scene(parentFxml, 640, 480);

        //am nevoie sa am acces la controller ca sa ii trimit service.urile mele

        IRepository<Drug> drugRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        UndoRedoManager undoRedoManager=new UndoRedoManager();
        ServiceDrug serviceDrug = new ServiceDrug(drugRepository, transactionRepository, undoRedoManager);
        ServiceTransaction serviceTransaction = new ServiceTransaction(transactionRepository, drugRepository, transactionValidator, undoRedoManager);

        serviceDrug.addDrug(1, "Algocalmin", "Terapia", (float) 10.35, true, 145);
        serviceDrug.addDrug(2, "Paracetamol", "KRKA", (float) 8.25, false, 345);
        serviceDrug.addDrug(3, "Aulin", "GSK", (float) 4.40, true, 10);
        serviceDrug.addDrug(4, "Stomachon", "Glenmark", (float) 45.40, false, 5);
        serviceDrug.addDrug(5, "Algocalmin", "ATB Iasi", (float) 8.90, true, 286);
        serviceDrug.addDrug(6, "Trachisan", "Terapia", (float) 10.35, false, 20);
        serviceDrug.addDrug(7, "Concor", "AstraZeneca", (float) 25.20, true, 50);
        serviceDrug.addDrug(8, "Aulin", "Novartis", (float) 12.35, true, 100);


        serviceTransaction.addTransaction(1, 2, 10, 3, "30.01.2021 15:45");
        serviceTransaction.addTransaction(2, 5, 20, 5, "05.03.2021 11:00");
        serviceTransaction.addTransaction(3, 7, 30, 7, "15.02.2021 18:25");
        serviceTransaction.addTransaction(4, 1, 30, 2, "20.02.2021 18:25");
        serviceTransaction.addTransaction(5, 3, 30, 1, "10.02.2021 18:25");
        serviceTransaction.addTransaction(6, 5, 10, 1, "11.02.2021 18:25");
        serviceTransaction.addTransaction(7, 3, 250, 2, "13.03.2021 17:25");
        serviceTransaction.addTransaction(8, 5, 780, 1, "10.03.2021 16:25");

        PrimaryController primaryController = fxmlLoader.getController();
        primaryController.setServices(serviceDrug, serviceTransaction, undoRedoManager);

        stage.setTitle("Pharmacy manager");
        //seteaza scena, adica ce este specific unui xml
        stage.setScene(this.scene);
        stage.show();
    }

    public static void main(String[] args) {
        //lanseaza aplicatia
        launch();
    }

}