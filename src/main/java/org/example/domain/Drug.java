package org.example.domain;

public class Drug extends Entity {
    private final String drugName;
    private final String drugProducer;
    private float drugPrice;
    private final boolean withoutRecipe;
    private final int drugStock;

    public Drug (int idEntity, String drugName, String drugProducer, float drugPrice, boolean withoutRecipe, int drugStock) {
        super(idEntity);
        this.drugName = drugName;
        this.drugProducer = drugProducer;
        this.drugPrice = drugPrice;
        this.withoutRecipe = withoutRecipe;
        this.drugStock = drugStock;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDrugProducer() {
        return drugProducer;
    }

    public float getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(float drugPrice) {
        this.drugPrice = drugPrice;
    }

    public boolean isWithoutRecipe() {
        return withoutRecipe;
    }

    public int getDrugStock() {
        return drugStock;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "id=" + getIdEntity() +
                ", drugName='" + drugName + '\'' +
                ", drugProducer='" + drugProducer + '\'' +
                ", drugPrice=" + drugPrice +
                ", withoutRecipe=" + withoutRecipe +
                ", drugStock=" + drugStock +
                '}';
    }
}
