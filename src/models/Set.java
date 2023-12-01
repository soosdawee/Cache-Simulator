package models;

import java.util.ArrayList;
import java.util.List;

public class Set {
    private List<Block> blocks;

    public Set(Integer numberOfSets, Integer blockSize) {
        this.blocks = new ArrayList<>(numberOfSets);
        for (int i = 0; i < numberOfSets; i++) {
            blocks.add(new Block(blockSize));
        }
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Boolean isInSet(Integer tag) {
        for (Block b : blocks) {
            if (b.getTag() == null) {
                continue;
            }
            if (b.getTag().equals(tag)) {
                return true;
            }
        }
        return false;
    }

    public void printContent() {
        for (Block b : blocks) {

            if (b.getContent().get(0).getContent() == null) {
                System.out.println("null");
            } else {
                System.out.println(b.getContent().get(0));
            }
        }
    }

    public Object[] returnContent(Integer index) {
        return blocks.get(index).returnDataAsObject();
    }

    public void addBlockDirect(Integer tag, List<MyByte> content) {
        blocks.get(0).addBlock(tag, content);
    }

    public Boolean isDirtyCheck(Integer tag) {
        return blocks.get(0).getDirty() || blocks.get(0).getWasUsed();
    }

    public List<MyByte> getContent() {
        return blocks.get(0).getContent();
    }

    public Integer getTag() {
        return blocks.get(0).getTag();
    }
}
