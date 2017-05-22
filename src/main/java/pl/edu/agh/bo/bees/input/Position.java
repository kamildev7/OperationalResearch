package pl.edu.agh.bo.bees.input;


public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDistance(Position position) {
        return Math.sqrt(Math.pow(x - position.x, 2) + Math.pow(y - position.y, 2));
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Position) {
            Position other = (Position) object;
            return this.x == other.getX() &&
                    this.y == other.getY();
        } else return false;
    }

}
