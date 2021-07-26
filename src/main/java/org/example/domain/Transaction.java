package org.example.domain;

public class Transaction extends Entity{
    private final int drugId;
    private final int clientCardNumber;
    private final int piecesOfDrug;
    private final String dateAndHour;

    public Transaction(int idEntity, int drugId, int clientCardNumber, int piecesOfDrug, String dateAndHour) {
        super(idEntity);
        this.drugId = drugId;
        this.clientCardNumber = clientCardNumber;
        this.piecesOfDrug = piecesOfDrug;
        this.dateAndHour = dateAndHour;
    }

    public int getDrugId() {
        return drugId;
    }

    public int getClientCardNumber() {
        return clientCardNumber;
    }

    public int getPiecesOfDrug() {
        return piecesOfDrug;
    }

    public String getDateAndHour() {
        return dateAndHour;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + getIdEntity() +
                ", drugId=" + drugId +
                ", clientCardNumber=" + clientCardNumber +
                ", piecesOfDrug=" + piecesOfDrug +
                ", dateAndHour='" + dateAndHour + '\'' +
                '}';
    }
}
