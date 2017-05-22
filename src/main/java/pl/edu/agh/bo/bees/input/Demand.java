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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Demand demand = (Demand) o;

        if (getGoodAmount() != demand.getGoodAmount()) return false;
        if (getFactory() != null ? !getFactory().equals(demand.getFactory()) : demand.getFactory() != null)
            return false;
        return getGoodName() != null ? getGoodName().equals(demand.getGoodName()) : demand.getGoodName() == null;
    }

    @Override
    public int hashCode() {
        int result = getFactory() != null ? getFactory().hashCode() : 0;
        result = 31 * result + (getGoodName() != null ? getGoodName().hashCode() : 0);
        result = 31 * result + getGoodAmount();
        return result;
    }
}
