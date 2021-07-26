package org.example.domain;

import org.example.repository.IRepository;
import org.example.repository.RepositoryValidatorException;

public class TransactionValidator {
    /**
     * Validates a transaction
     * @param transaction the transaction to validate
     * @param drugRepository the drug repository, for validating the transaction's drug id.
     * @throws RepositoryValidatorException if there are any validation errors.
     */
    public void validate(Transaction transaction, IRepository<Drug> drugRepository) throws RepositoryValidatorException {
        Drug givenDrug = drugRepository.readOne(transaction.getDrugId());
        // StringBuilder errorMessages = new StringBuilder();
        if (givenDrug == null) {
            throw new RepositoryValidatorException("There is no drug with the given id!");
        }
        if (transaction.getPiecesOfDrug() > givenDrug.getDrugStock()) {
            throw new RepositoryValidatorException("Cannot add transaction unless number of pieces < drug's stock");
        }
    }
}

