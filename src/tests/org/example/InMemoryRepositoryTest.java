package org.example;

import org.example.domain.Drug;
import org.example.domain.Transaction;
import org.example.repository.IRepository;
import org.example.repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {

    @org.junit.jupiter.api.Test
    void createShouldValidateTheIdAndTheObject() throws Exception {
        //setup(arrange)
        IRepository<Drug> drugRepository = new InMemoryRepository<>();
        Drug drug1 = new Drug(1, "a", "b", 5, true, 10);
        Drug drug2 = new Drug(2, "a", "b", 5, true, 10);
        Drug drug3 = new Drug(1, "a", "b", 5, true, 10);

        IRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        Transaction transaction1 = new Transaction(1, 1, 1, 5,  "10.02.2021 10:00");
        Transaction transaction2 = new Transaction(2, 2, 1, 5, "10.02.2021 10:00");
        Transaction transaction3 = new Transaction(1, 1, 1, 5, "10.02.2021 10:00");

        //act
        drugRepository.create(drug1);
        transactionRepository.create(transaction1);

        //assert
        assertEquals(1, drugRepository.read().size(), "After added a drug, read().size() != 1!");
        assertEquals(drug1.getIdEntity(), drugRepository.read().get(0).getIdEntity());

        assertEquals(1, transactionRepository.read().size(), "After added a transaction, read().size() != 1!" );
        assertEquals(transaction1.getIdEntity(), transactionRepository.read().get(0).getIdEntity());

        drugRepository.create(drug2);
        assertEquals(2,drugRepository.read().size(), "After added 2 drugs, read().size() != 2!");

        transactionRepository.create(transaction2);
        assertEquals(2, transactionRepository.read().size(), "After added 2 transactions, read().size() != 2!");

        //assertThrows
        try{
            drugRepository.create(drug3);
            transactionRepository.create(transaction3);
            fail("Adding a drug with existing id does not throw an exception!");
            fail("Adding a transaction with existing id does not throw an exception!");
        }catch (Exception ex){
            assertEquals(2, drugRepository.read().size(), "It was added a drug with existed id!");
            assertEquals(2, transactionRepository.read().size(), "It waas added a transaction with existed id");
        }
    }

    @org.junit.jupiter.api.Test
    void readOneShouldCorrectlyReturnEntities() throws Exception {
        IRepository<Drug> drugIRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionIRepository = new InMemoryRepository<>();
        List<Drug> drugs = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Drug drug = new Drug(i, "a", "b", 5, true, 100);
            Transaction transaction = new Transaction(i, 5, 10, 1, "10.02.2021 10:00");
            drugs.add(drug);
            transactions.add(transaction);
        }

        for(Drug drug : drugs) {
            drugIRepository.create(drug);
        }

        for(Transaction transaction : transactions) {
            transactionIRepository.create(transaction);
        }

        for(int i = 0; i < 20; i++) {
            Drug fooundById = drugIRepository.readOne(i);
            Transaction foundById2 = transactionIRepository.readOne(i);
            assertEquals(i, fooundById.getIdEntity());
            assertEquals(i, foundById2.getIdEntity());
        }

        assertNull(drugIRepository.readOne(100));
        assertNull(transactionIRepository.readOne(100));
    }

    @org.junit.jupiter.api.Test
    void readShouldCorrectlyReturnAllEntities() {
        IRepository<Drug> drugIRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionIRepository = new InMemoryRepository<>();
        List<Drug> drugs = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Drug drug = new Drug(i, "a", "b", 5, true, 100);
            Transaction transaction = new Transaction(i, 5, 10, 1, "10.02.2021 10:00");
            drugs.add(drug);
            transactions.add(transaction);
        }

        for(Drug drug : drugs) {
            drugIRepository.create(drug);
        }

        for(Transaction transaction : transactions) {
            transactionIRepository.create(transaction);
        }

        for(int i = 0; i < 20; i++) {
            List<Drug> drugsFoundById = drugIRepository.read();
            List<Transaction> transactionsFoundById = transactionIRepository.read();
            assertEquals(20, drugsFoundById.size());
            assertEquals(20, transactionsFoundById.size());
        }
    }

    @org.junit.jupiter.api.Test
    void updateShouldValidateTheIdAndUpdateTheEntity() throws Exception{
        IRepository<Drug> drugIRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionIRepository = new InMemoryRepository<>();
        List<Drug> drugs = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Drug drug = new Drug(i, "a", "b", 5, true, 100);
            Transaction transaction = new Transaction(i, 5, 10, 1, "10.02.2021 10:00");
            drugs.add(drug);
            transactions.add(transaction);
        }

        for(Drug drug : drugs) {
            drugIRepository.create(drug);
        }

        for(Transaction transaction : transactions) {
            transactionIRepository.create(transaction);
        }

        for(int i = 0; i < 20; i++) {
            Drug drugToUpdate = new Drug(i, "c", "d", 7, false, 50);
            drugIRepository.update(drugToUpdate);
            Transaction transactionToUpdate = new Transaction(i, 10, 5, 5, "20.12.2021 9:00");
            transactionIRepository.update(transactionToUpdate);
            assertEquals(drugToUpdate, drugIRepository.readOne(i));
            assertEquals(transactionToUpdate, transactionIRepository.readOne(i));
        }

        try{
            Drug drugToUpdate = new Drug(21, "c", "d", 7, false, 50);
            Transaction transactionToUpdate = new Transaction(21, 10, 5, 5, "20.12.2021 9:00");
            drugIRepository.update(drugToUpdate);
            transactionIRepository.update(transactionToUpdate);
            fail("Updating an entity without existed id does not throw an exception!");
        }catch (Exception ex) {
            assertNull(drugIRepository.readOne(21));
            assertNull(transactionIRepository.readOne(21));
        }
    }

    @org.junit.jupiter.api.Test
    void deleteShouldValidateTheIdAndRemoveTheObject() throws Exception {
        IRepository<Drug> drugIRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionIRepository = new InMemoryRepository<>();
        List<Drug> drugs = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Drug drug = new Drug(i, "a", "b", 5, true, 100);
            Transaction transaction = new Transaction(i, 5, 10, 1, "10.02.2021 10:00");
            drugs.add(drug);
            transactions.add(transaction);
        }

        for(Drug drug : drugs) {
            drugIRepository.create(drug);
        }

        for(Transaction transaction : transactions) {
            transactionIRepository.create(transaction);
        }

        for(int i = 0; i < 20; i++) {
            drugIRepository.delete(i);
            transactionIRepository.delete(i);
            Drug foundById = drugIRepository.readOne(i);
            Transaction foundById2 = transactionIRepository.readOne(i);
            assertNull(foundById);
            assertNull(foundById2);
            assertEquals(20 - i - 1, drugIRepository.read().size());
            assertEquals(20 - i - 1, transactionIRepository.read().size());
        }

        try{
             drugIRepository.delete(5);
             transactionIRepository.delete(5);
             fail("Deleting an entity with existed id does not throw an exception!");
        }catch (Exception ex) {
            assertEquals(0, drugIRepository.read().size(), "There are undeleted drugs!");
            assertEquals(0, transactionIRepository.read().size(), "There are undeleted transactions!");
        }
    }
}