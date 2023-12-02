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

    public void changeByteDirect(Integer offset, Character changed) {
        blocks.get(0).changeByte(offset, changed);
    }

    public void changeByteAssociative(Integer tag, Integer offset, Character changed, Integer timeStamp) {
        findBlockWithTag(tag).changeByte(offset, changed, timeStamp);
    }

    public void changeByteAssociative(Integer tag, Integer offset, Character changed) {
        findBlockWithTag(tag).changeByte(offset, changed);
    }

    public void setDirty() {
        blocks.get(0).setDirty(true);
    }

    public void setDirty(Integer index) {
        blocks.get(index).setDirty(true);
    }

    public Boolean isDirtyCheck() {
        return blocks.get(0).getDirty();
    }

    public Boolean isDirtyCheck(Integer index) {
        return blocks.get(index).getDirty();
    }

    public List<MyByte> getContent() {
        return blocks.get(0).getContent();
    }

    public List<MyByte> getContent(Integer index) {
        return blocks.get(index).getContent();
    }

    public Integer getTag() {
        return blocks.get(0).getTag();
    }

    public Integer getTag(Integer index) {
        return blocks.get(index).getTag();
    }

    public Block findMinTimeStamp() {
        Block min = new Block();
        for (Block b : blocks) {
            if (b.getTimeStamp() < min.getTimeStamp()) {
                min = b;
                min.setTimeStamp(b.getTimeStamp());
            }
        }
        return min;
    }

    public Integer findMinTimeStampIndex() {
        Block min = new Block();
        Integer toReturn = 0;
        int iterator = 0;
        for (Block b : blocks) {
            iterator++;
            if (b.getTimeStamp() < min.getTimeStamp()) {
                min = b;
                min.setTimeStamp(b.getTimeStamp());
                toReturn = iterator - 1;
            }
        }
        return toReturn;
    }

    public Integer findIndexByTag(Integer tag) {
        Integer toReturn = 0;

        for (Block b : blocks) {
            toReturn++;
            if (b.getTag() == tag) {
                return (toReturn - 1);
            }
        }
        return -1;
    }

    public Block findBlockWithTag(Integer tag) {
        for (Block b : blocks) {
            if (b.getTag() == tag) {
                return b;
            }
        }

        return null;
    }
}
