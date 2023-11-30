package models;

public class Block {
    private  Integer size;
    private MyByte[] content;
    private Boolean isDirty;
    private Boolean replacement;

    public Block(Integer size) {
        this.content = new MyByte[size];
        this.isDirty = false;
        this.replacement = false;
    }
}
