package org.example.service;

import org.example.domain.*;
import org.example.repository.IRepository;
import org.example.repository.RepositoryException;

import java.util.*;

public class ServiceDrug {
    private final IRepository<Drug> drugRepository;
    private final IRepository<Transaction> transactionRepository;
    private UndoRedoManager undoRedoManager;

    public ServiceDrug(IRepository<Drug> drugRepository, IRepository<Transaction> transactionRepository, UndoRedoManager undoRedoManager) {
        this.drugRepository = drugRepository;
        this.transactionRepository = transactionRepository;
        this.undoRedoManager = undoRedoManager;
    }

    /**
     * Adding new drug to repository;
     * @param id the id of drug must be unique;
     * @param drugName the name of the drug;
     * @param drugProducer the producer of the drug;
     * @param drugPrice the price of the drug, must be > 0;
     * @param withoutRecipe the release restrictions of the drug must be boolean type;
     * @param drugStock the stock of the drug;
     * @throws RepositoryException if there is any validation errors;
     */
    public void addDrug(int id, String drugName, String drugProducer, float drugPrice, boolean withoutRecipe, int drugStock) throws RepositoryException {
        Drug drugToAdd = new Drug(id, drugName, drugProducer, drugPrice, withoutRecipe, drugStock);
        this.drugRepository.create(drugToAdd);
        undoRedoManager.addToUndo(new UndoRedoAddOperation<>(this.drugRepository, drugToAdd));
    }

    /**
     * Updating an existing drug to repository;
     * @param id the id of drug must be unique;
     * @param drugName the name of the drug to update;
     * @param drugProducer the producer of the drug to update;
     * @param drugPrice the price of the drug to update, must be > 0;
     * @param withoutRecipe the release restrictions of the drug to update;
     * @param drugStock the stock of the drug to update;
     *  @throws RepositoryException if there is any validation errors;
     */
    public void updateDrug(int id, String drugName, String drugProducer, float drugPrice, boolean withoutRecipe, int drugStock) throws RepositoryException {
        Drug drugToUpdate = new Drug(id, drugName, drugProducer, drugPrice, withoutRecipe, drugStock);
        this.drugRepository.update(drugToUpdate);

        // TODO: add update undo redo operation to undoRedoManager
    }

    /**
     * Deleting drugs by id;
     * @param drugId the id of the drug to be deleted;
     * @throws RepositoryException if there is any validation errors;
     */
    public void deleteDrug(int drugId, String drugName, String drugProducer, float drugPrice, boolean withoutRecipe, int drugStock) throws RepositoryException{
        Drug drugToDelete = new Drug(drugId, drugName, drugProducer, drugPrice, withoutRecipe, drugStock);
        this.drugRepository.delete(drugId);
        undoRedoManager.addToUndo(new UndoRedoDeleteOperation<>(this.drugRepository, drugToDelete));
    }

    /**
     * Returns all drugs;
     * @return all drugs;
     */
    public List<Drug> getAll() {
        return this.drugRepository.read();
    }

    /**
     * Returns a drug based on the ID entered by the user.
     * @param idEntity of the drug.
     * @return drug details based on the searched ID.
     * @throws Exception
     */
    public Drug returnOneDrug (int idEntity) throws Exception {
        Drug drug = this.drugRepository.readOne(idEntity);
        return drug;
    }

    /**
     * Return all entities by a string field;
     * @param drugFieldName is the string field after which it is displayed all the entities;
     * @return an list with all entities by a string field;
     */
    public List<Drug> getAllEntitiesByStringField(String drugFieldName) {
        List<Drug> getAllEntities = new ArrayList<Drug>();
        for (Drug drug : this.getAll()) {
            if (drug.getDrugName().equalsIgnoreCase(drugFieldName) || drug.getDrugProducer().equalsIgnoreCase(drugFieldName)) {
                getAllEntities.add(drugRepository.readOne(drug.getIdEntity()));
            }
        }
        return getAllEntities;
    }

    /**
     * Return all drugs ordered by number of transactions;
     * @return all drugs ordered by number of transactions;
     */
    public List<DrugsWithNumberOfTransactions> getDrugsOrderedByNumberOfTransactions() {
        Map<Integer, Integer> drugsWithNumberOfTransactions = new HashMap<>();
        for (Transaction t : this.transactionRepository.read()) {
            int drugId = t.getDrugId();
            if(!drugsWithNumberOfTransactions.containsKey(drugId)) {
                drugsWithNumberOfTransactions.put(drugId, 1);
            } else {
                drugsWithNumberOfTransactions.put(drugId, drugsWithNumberOfTransactions.get(drugId) + 1);
            }
        }
        List<DrugsWithNumberOfTransactions> allDrugs = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : drugsWithNumberOfTransactions.entrySet()) {
            int drugId = entry.getKey();
            int numberOfTransactions = entry.getValue();

            Drug drug = this.drugRepository.readOne(drugId);
            allDrugs.add(new DrugsWithNumberOfTransactions(drugId, drug.getDrugName(), numberOfTransactions));
        }
        allDrugs.sort(Comparator.comparing(DrugsWithNumberOfTransactions::getNumberOfTransactions).reversed());
        // allDrugs.sort((x,y) -> x.getNumberOfTransactions() - y.getNumberOfTransactions());
        return allDrugs;
    }

    /**
     * Increase drug price with a given percentage if drug price is less then a given drug price
     * @param percentageToIncrease is the percentage of price to increase
     * @param minimumDrugPriceValue is the given drug price comparison
     */
    public void increasePrice(float percentageToIncrease, float minimumDrugPriceValue) {
        for(Drug drug : this.getAll()) {
            if(drug.getDrugPrice() <= minimumDrugPriceValue) {
                float amount = (percentageToIncrease / 100) * drug.getDrugPrice();
                drug.setDrugPrice(drug.getDrugPrice() + amount);
            }
        }
    }
}
