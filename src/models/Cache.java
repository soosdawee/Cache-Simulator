package models;

import utils.CacheType;
import utils.ReplacementMechanism;
import utils.WriteMechanism;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Cache {
    private CacheType cacheType;
    private Integer cacheSize;
    private WriteMechanism writeMechanism;
    private ReplacementMechanism replacementMechanism;
    private Integer numberOfSets;
    private List<Set> sets;
    private Integer blockSize;
    private static Integer timeStamp = 0;

    public Cache(CacheType cacheType, Integer cacheSize, WriteMechanism writeMechanism, ReplacementMechanism replacementMechanism, Integer numberOfSets, Integer blockSize) {
        this.numberOfSets = numberOfSets;
        this.writeMechanism = writeMechanism;
        this.cacheSize = cacheSize;
        this.cacheType = cacheType;
        this.replacementMechanism = replacementMechanism;
        this.blockSize = blockSize;

        this.sets = new ArrayList<>(numberOfSets);

        for (int i = 0; i < numberOfSets; i++) {
            sets.add(new Set(cacheSize / (numberOfSets * blockSize), blockSize));
        }
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    public void printCacheContent() {
        for (Set s : sets) {
            s.printContent();
        }
    }

    public Boolean isHit(Integer tag, Integer index){
        switch (cacheType) {
            case DIRECT:
                if (sets.get(index).isInSet(tag)) {
                    return true;
                } else {
                    return false;
                }
            case ASSOCIATIVE:
                return isHit(tag);
            default: return false;
        }
    }

    public void putInCache(Integer tag) {
        sets.get(0).addBlockDirect(tag, new ArrayList<>());
    }

    private Boolean isHit(Integer tag){
        for (Set s : sets) {
            for (Block b : s.getBlocks()) {
                if (Objects.equals(b.getTag(), tag)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void add(Integer tag, Integer index, List<MyByte> content) {
        switch (cacheType) {
            case DIRECT:
                sets.get(index).addBlockDirect(tag, content);
                break;
            case ASSOCIATIVE:
                Block block = findEmptySpot();

                if (block == null) {
                    block = getBlockToModify();
                }

                block.addBlock(tag, content, timeStamp++);
                break;
        }
    }

    private Block findEmptySpot() {
        for (Set s : sets) {
            for (Block b : s.getBlocks()) {
                if (b.getTag() == -1) {
                    return b;
                }
            }
        }
        return null;
    }

    private Block getBlockToModify() {
        return sets.get(0).findMinTimeStamp();
    }

    public Integer getIndexToModify() {
        return sets.get(0).findMinTimeStampIndex();
    }

    public void setDirty(Integer index) {
        if (cacheType == CacheType.DIRECT) {
            sets.get(index).setDirty();
        } else {
            sets.get(0).setDirty(index);
        }

    }

    public void changeOneByte(Integer index, Integer offset, Character changed, Integer tag) {
        switch (cacheType) {
            case DIRECT:
                sets.get(index).changeByteDirect(offset, changed);
                break;
            case ASSOCIATIVE:
                if (replacementMechanism == ReplacementMechanism.LRU) {
                    sets.get(0).changeByteAssociative(tag, offset, changed, timeStamp++);
                } else {
                    sets.get(0).changeByteAssociative(tag, offset, changed);
                }

        }
    }

    public void flush() {
        if (writeMechanism == WriteMechanism.WRITEBACK) {
            for (Set s : sets) {
                for (Block b : s.getBlocks()) {
                    b.makeNull();
                }
            }
        }
    }

    public Boolean checkIsDirty(Integer index) {
        if (cacheType == CacheType.DIRECT) {
            return sets.get(index).isDirtyCheck();
        } else {
            return sets.get(0).isDirtyCheck(index);
        }

    }

    public List<MyByte> getContent(Integer index) {
        if (cacheType == CacheType.DIRECT) {
            return sets.get(index).getContent();
        } else {
            return sets.get(0).getContent(index);
        }
    }

    public Integer getTag(Integer index) {
        if (cacheType == CacheType.DIRECT) {
            return sets.get(index).getTag();
        } else {
            return sets.get(0).getTag(index);
        }
    }

    public Integer getTagByIndex(Integer tag) {
        return sets.get(0).findIndexByTag(tag);
    }

    public Object[][] returnCacheContent() {
        Object[][] objects = new Object[numberOfSets * (cacheSize / (numberOfSets * blockSize))][3 + blockSize];
        int i = 0;
        for (Set s : sets) {
            int j = 0;
            for (Block b : s.getBlocks()) {
                objects[i++] = s.returnContent(j++);
            }
        }
        if (writeMechanism == WriteMechanism.WRITETHROUGH) {
            objects = removeFirstColumn(objects);
        }
        return objects;
    }

    private static Object[][] removeFirstColumn(Object[][] inputArray) {
        int numRows = inputArray.length;
        int numCols = inputArray[0].length - 1; // Reduced by 1 for the removed column

        Object[][] newArray = new Object[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            System.arraycopy(inputArray[i], 1, newArray[i], 0, numCols);
        }

        return newArray;
    }

    private void printObjects(Object[] objects, int dimension) {
        for (int i =0; i < dimension; i++) {
            System.out.println(objects[i]);
        }
        System.out.println(" ");
    }
}
