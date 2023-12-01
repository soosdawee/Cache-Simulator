package models;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private Integer size;
    private Integer tag;
    private Integer v;
    private List<MyByte> content;
    private Boolean isDirty;
    private Boolean wasUsed;

    public Block(Integer size) {
        this.size = size;
        this.content = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            content.add(new MyByte());
        }

        this.tag = -1;
        this.v = -1;
        this.isDirty = false;
        this.wasUsed = false;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Boolean getDirty() {
        return isDirty;
    }

    public void setDirty(Boolean dirty) {
        isDirty = dirty;
    }

    public List<MyByte> getContent() {
        return content;
    }

    public void setContent(List<MyByte> content) {
        this.content = content;
    }

    public Boolean getWasUsed() {
        return wasUsed;
    }

    public void setWasUsed(Boolean wasUsed) {
        this.wasUsed = wasUsed;
    }

    public Object[] returnDataAsObject() {
        Object[] objects = new Object[3 + size];
        if (!isDirty) {
            objects[0] = "0";
        } else {
            objects[0] = "1";
        }
        if (v == -1 || v == 0) {
            objects[1] = "0";
        } else {
            objects[1] = "1";
        }
        if (tag == -1) {
            objects[2] = "";
        } else {
            objects[2] = Integer.toString(tag);
        }
        int i = 3;
        for (MyByte b : content) {
            if (b.getContent() == null) {
                objects[i] = "";
            } else {
                objects[i] = b.getContent();
            }
            i++;
        }
        return objects;
    }

    public void addBlock(Integer tag, List<MyByte> newContent) {
        this.tag = tag;
        this.content = newContent;
        this.v = 1;
        this.wasUsed = true;
    }
}
