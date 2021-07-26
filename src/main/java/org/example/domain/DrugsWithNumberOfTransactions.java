package org.example.domain;

public class DrugsWithNumberOfTransactions {
    private final int idDrug;
    private final String drugName;
    private final int numberOfTransactions;

    public DrugsWithNumberOfTransactions(int idDrug, String drugName, int numberOfTransactions) {
        this.idDrug = idDrug;
        this.drugName = drugName;
        this.numberOfTransactions = numberOfTransactions;
    }

    public int getIdDrug() {
        return idDrug;
    }

    public String getDrugName() {
        return drugName;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    @Override
    public String toString() {
        return "DrugsWithNumberOfTransactions{" +
                "idDrug=" + idDrug +
                ", drugName='" + drugName + '\'' +
                ", numberOfTransactions=" + numberOfTransactions +
                '}';
    }
}
