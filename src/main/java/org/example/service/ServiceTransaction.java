package org.example.service;

import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.repository.RepositoryException;
import org.example.repository.RepositoryValidatorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ServiceTransaction {
    private final IRepository<Transaction> transactionRepository;
    private final IRepository<Drug> drugRepository;
    private final TransactionValidator transactionValidator;
    private final UndoRedoManager undoRedoManager;

    public ServiceTransaction(IRepository<Transaction> transactionRepository, IRepository<Drug> drugRepository, TransactionValidator transactionValidator, UndoRedoManager undoRedoManager) {
        this.transactionRepository = transactionRepository;
        this.drugRepository = drugRepository;
        this.transactionValidator = transactionValidator;
        this.undoRedoManager = undoRedoManager;
    }

    /**
     * Adding new transaction to repository; the transaction can be done if:
     * piecesOfDrug < drugStock;
     * @param id the id of the transaction must be unique;
     * @param drugId the id of the drug, must exists and must be unique;
     * @param clientCardNumber the number card of the client, must be int;
     * @param piecesOfDrug the pieces of drug that are traded;
     * @param dateAndHour the date and hour of the transaction;
     * @throws RepositoryValidatorException if there are any validation errors;
     */
    public void addTransaction(int id, int drugId, int clientCardNumber, int piecesOfDrug, String dateAndHour) throws RepositoryValidatorException, RepositoryException {
        Transaction transactionToAdd = new Transaction(id, drugId, clientCardNumber, piecesOfDrug, dateAndHour);
        if(drugRepository.readOne(transactionToAdd.getDrugId()) != null){
            this.transactionValidator.validate(transactionToAdd, this.drugRepository);
            this.transactionRepository.create(transactionToAdd);
            this.undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.transactionRepository, transactionToAdd));
        }
    }

    /**
     * Updating transactions by Id;
     * @param id the id of the transaction must be unique;
     * @param drugId the id of the drug, must exists and must be unique;
     * @param clientCardNumber the number card of the client, must be int;
     * @param piecesOfDrug the pieces of drug that are traded;
     * @param dateAndHour the date and hour of the transaction;
     * @throws RepositoryException if there are any validation errors;
     */
    public void updateTransaction(int id, int drugId, int clientCardNumber, int piecesOfDrug, String dateAndHour) throws RepositoryException {
        Transaction transactionToUpdate = new Transaction(id, drugId, clientCardNumber, piecesOfDrug, dateAndHour);
        if(drugRepository.readOne(transactionToUpdate.getDrugId()) == null) {
            throw new RepositoryException("The drug ID must exists!");
        }
        transactionRepository.update(transactionToUpdate);
        // TODO: undoRedoManager
    }

    /**
     * Deleting transaction by id;
     * @param transactionId the id of the transaction to be deleted;
     * @throws RepositoryException if there is any validation errors;
     */
    public void deleteTransaction(int transactionId, int drugId, int clientCardNumber, int piecesOfDrugs, String dateAndHour) throws RepositoryException {
        Transaction transactionToDelete = new Transaction(transactionId, drugId, clientCardNumber, piecesOfDrugs, dateAndHour);
        this.transactionRepository.delete(transactionId);
        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.transactionRepository, transactionToDelete));
    }

    /**
     * Returns a transaction based on the ID entered by the user;
     * @param idEntity of the transaction;
     * @return transaction details based on the searched ID;
     */
    public Transaction returnOneTransaction (int idEntity) {
        return this.transactionRepository.readOne(idEntity);
    }

    /**
     * Returns all transactions;
     * @return all transactions;
     */
    public List<Transaction> getAll() {
        return this.transactionRepository.read();
    }

    /**
     * Search for drugs and transactions across all fields by a string input
     * @param stringInput is the string after which the search is made
     * @return all entities that match the string input
     */
    public List<Entity> searchEntitiesByStringInput(String stringInput){
        List<Entity> getAllEntitiesByStringInput = new ArrayList<>();

        for (Drug drug : this.drugRepository.read()) {
            if (drug.getDrugName().contains(stringInput)) {
                getAllEntitiesByStringInput.add(drugRepository.readOne(drug.getIdEntity()));
            } else if (drug.getDrugProducer().contains(stringInput)){
                getAllEntitiesByStringInput.add(drugRepository.readOne(drug.getIdEntity()));
            } else if (String.valueOf(drug.getDrugPrice()).contains(stringInput)) {
                getAllEntitiesByStringInput.add(drugRepository.readOne(drug.getIdEntity()));
            } else if (String.valueOf(drug.getDrugStock()).contains(stringInput)) {
                getAllEntitiesByStringInput.add(drugRepository.readOne(drug.getIdEntity()));
            }
        }

        for(Transaction transaction : this.getAll()) {
            if (String.valueOf(transaction.getClientCardNumber()).contains(stringInput)) {
                getAllEntitiesByStringInput.add(transactionRepository.readOne(transaction.getIdEntity()));
            } else if (String.valueOf(transaction.getPiecesOfDrug()).contains(stringInput)) {
                getAllEntitiesByStringInput.add(transactionRepository.readOne(transaction.getIdEntity()));
            } else if (transaction.getDateAndHour().contains(stringInput)) {
                getAllEntitiesByStringInput.add(transactionRepository.readOne(transaction.getIdEntity()));
            }
        }
        return getAllEntitiesByStringInput;
    }

    /**
     * Display all transactions between 2 dates;
     * @param start is the start date interval;
     * @param end is the end date interval;
     * @return all transactions between 2 date;
     */
    public List<Transaction> displaysTransactionsBetweenTwoDateAndTimes(LocalDateTime start, LocalDateTime end) {
        List<Transaction> getAllTransaction = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for ( Transaction transaction : this.getAll()) {
            String stringDate = transaction.getDateAndHour();
            LocalDateTime typedDate = LocalDateTime.parse(stringDate, formatter);
            if(start.isBefore(typedDate) && typedDate.isBefore(end)) {
                getAllTransaction.add(transaction);
            }
        }
        return getAllTransaction;
    }

    /**
     * Return all client Card Number ordered by number of transactions;
     * @return all client Card Number ordered by number of transactions;
     */
    public List<ClientCardNumberCardWithNumberOfTransactions> getClientNumberCardOrderedByNumberOfTransactions() {
        Map<Integer, Integer> clientCardNumberCardWithNumberOfTransactions = new HashMap<>();
        for(Transaction t : this.getAll()) {
            int clientNumberCard = t.getClientCardNumber();
            if(!clientCardNumberCardWithNumberOfTransactions.containsKey(clientNumberCard)) {
                clientCardNumberCardWithNumberOfTransactions.put(clientNumberCard, 1);
            } else {
                clientCardNumberCardWithNumberOfTransactions.put(clientNumberCard, clientCardNumberCardWithNumberOfTransactions.get(clientNumberCard) + 1);
            }
        }

        List<ClientCardNumberCardWithNumberOfTransactions> allClientNumberCards = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : clientCardNumberCardWithNumberOfTransactions.entrySet()) {
            int clientCardNumber = entry.getKey();
            int numberOfTransactions = entry.getValue();

            allClientNumberCards.add(new ClientCardNumberCardWithNumberOfTransactions(clientCardNumber, numberOfTransactions));
        }
        allClientNumberCards.sort(Comparator.comparing(ClientCardNumberCardWithNumberOfTransactions::getNumberOfTransactions).reversed());
        return allClientNumberCards;
    }

    /**
     * Deleting all transactions between two days interval;
     * @param start the start date interval;
     * @param end the end date interval;
     */
    public void deleteTransactionsBetweenTwoDates(Date start, Date end) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        for ( Transaction transaction : this.getAll()) {
            String stringDate = transaction.getDateAndHour();
            Date typedDate = format.parse(stringDate);
            if(start.before(typedDate) && typedDate.before(end)) {
               this.transactionRepository.delete(transaction.getIdEntity());
            }
        }
    }
}

