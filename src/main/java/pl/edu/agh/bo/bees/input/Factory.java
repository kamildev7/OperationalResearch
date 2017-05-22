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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Factory factory = (Factory) o;

        return getPosition() != null ? getPosition().equals(factory.getPosition()) : factory.getPosition() == null;
    }

    @Override
    public int hashCode() {
        return getPosition() != null ? getPosition().hashCode() : 0;
    }
}
