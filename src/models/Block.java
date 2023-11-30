package models;

public class Block {
    private final Integer SIZE = 16;
    private Byte[] content;
    private Boolean isDirty;
    private Boolean lru;

    public Block() {
        this.content = new Byte[SIZE];
        this.isDirty = false;
        this.lru = false;
    }
}
