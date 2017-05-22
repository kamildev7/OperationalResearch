package pl.edu.agh.bo.bees.input;


public class StockProvider {

    private Position position;
    private int size;

    public StockProvider(int size, Position position) {
        this.size = size;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String result = String.format("\tPosition: (%d, %d)\n", position.getX(), position.getY());
        result += String.format("\tSize: %d\n", size);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof StockProvider) {
            StockProvider other = (StockProvider) object;
            return this.position.getX() == other.getPosition().getX() &&
                    this.position.getY() == other.getPosition().getY() &&
                    this.size == other.getSize();
        } else return false;
    }

}
