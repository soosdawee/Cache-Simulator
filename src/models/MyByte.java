package models;

public class MyByte {
    private Integer address;
    private Character content;

    public MyByte(Integer address, Character content) {
        this.content = content;
        this.address = address;
    }

    public MyByte() {

    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Character getContent() {
        return content;
    }

    public void setContent(Character content) {
        this.content = content;
    }
}
