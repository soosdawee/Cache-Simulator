package models;

public class Cache {
    private int numberOfSets;
    private WriteOperation writeOperation;
    private Set[] sets;

    public Cache(int numberOfSets, WriteOperation writeOperation) {
        this.numberOfSets = numberOfSets;
        this.writeOperation = writeOperation;
        this.sets = new Set[numberOfSets];
    }
}
