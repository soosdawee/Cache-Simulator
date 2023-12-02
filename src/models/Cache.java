package models;

import utils.CacheType;
import utils.ReplacementMechanism;
import utils.WriteMechanism;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cache {
    private CacheType cacheType;
    private Integer cacheSize;
    private WriteMechanism writeMechanism;
    private ReplacementMechanism replacementMechanism;
    private Integer numberOfSets;
    private List<Set> sets;
    private Integer blockSize;

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

    public void printCacheContent() {
        for (Set s : sets) {
            s.printContent();
        }
    }

    public Boolean isHit(Integer tag, Integer index){
        if (sets.get(index).isInSet(tag)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isHit(Integer tag){
        //TODO
        return false;
    }

    public void add(Integer tag, Integer index, List<MyByte> content) {
        switch (cacheType) {
            case DIRECT:
                sets.get(index).addBlockDirect(tag, content);
                break;
        }
    }

    public void setDirty(Integer index) {
        sets.get(index).setDirty();
    }

    public void changeOneByte(Integer index, Integer offset, Character changed) {
        switch (cacheType) {
            case DIRECT:
                sets.get(index).changeByteDirect(offset, changed);
        }
    }

    public Boolean checkIsDirty(Integer index) {
        return sets.get(index).isDirtyCheck();
    }

    public List<MyByte> getContent(Integer index) {
        return sets.get(index).getContent();
    }

    public Integer getTag(Integer index) {
        return sets.get(index).getTag();
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
