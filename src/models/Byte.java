package models;

public class Byte {
    private Integer address;
    private String content;

    public Byte(Integer address, String content) {
        this.content = content;
        this.address = address;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
