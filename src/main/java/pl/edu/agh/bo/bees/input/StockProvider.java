package pl.edu.agh.bo.bees.input;


public class StockProvider {

    private Position position;
    private int size;
    private String goodName;

    public StockProvider(int size, Position position, String goodName) {
        this.size = size;
        this.position = position;
        this.goodName = goodName;
    }

    public Position getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public String getGoodName() {
        return goodName;
    }

    @Override
    public String toString() {
        String result = String.format("\tPosition: (%d, %d)\n", position.getX(), position.getY());
        result += String.format("\tSize: %d\n", size);
        result += String.format("\tGood: %s\n", goodName)
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockProvider that = (StockProvider) o;

        if (getSize() != that.getSize()) return false;
        return getPosition() != null ? getPosition().equals(that.getPosition()) : that.getPosition() == null;
    }

    @Override
    public int hashCode() {
        int result = getPosition() != null ? getPosition().hashCode() : 0;
        result = 31 * result + getSize();
        return result;
    }
}
