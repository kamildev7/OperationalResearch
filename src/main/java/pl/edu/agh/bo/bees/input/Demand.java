package pl.edu.agh.bo.bees.input;


public class Demand {

    private Factory factory;
    private String goodName;
    private int goodAmount;

    public Demand(Factory factory, String goodName, int goodAmount) {
        this.factory = factory;
        this.goodName = goodName;
        this.goodAmount = goodAmount;
    }

    public int getGoodAmount() {
        return goodAmount;
    }

    public String getGoodName() {
        return goodName;
    }

    public Factory getFactory() {
        return factory;
    }

    @Override
    public String toString() {
        String result = "";
        result += String.format("Demand of factory %s:\n", factory.toString());
        result += String.format("\tGood: %s\n\tAmount: %d\n", goodName, goodAmount);

        return result;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Demand) {
            Demand other = (Demand) object;
            return this.factory.equals(other.getFactory()) &&
                    this.goodName.equals(other.getGoodName()) &&
                    this.goodAmount == other.getGoodAmount();
        } else return false;
    }

}
