package pl.edu.agh.bo.bees.input;

public class Factory {

    private Position position;

    public Factory(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {

        return String.format("(%d, %d)", position.getX(), position.getY());
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Factory) {
            Factory other = (Factory) object;
            return this.position.equals(other.getPosition());
        } else return false;
    }

}
