package controllers;

import models.Block;
import models.Cache;
import models.MyByte;
import models.Set;
import utils.*;
import views.CacheView;
import views.IntroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    private CacheView cacheView;
    private IntroView introView;
    private List<String[]> controls;
    private List<MyByte> memory;
    WriteMechanism writeMechanism;
    ReplacementMechanism replacementMechanism;
    CacheType cacheType;
    Integer cacheSize;
    Integer setNumber;
    Integer blockSize;
    private static Cache cache;
    Integer hits = 0;
    Integer totalAccesses = 0;

    public Controller (CacheView cacheView, IntroView introView) {
        this.cacheView = cacheView;
        this.introView = introView;
        this.memory = MemoryUtil.createRandomMemory(512);
        this.cacheView.addIterateListener(new IterateListener());

        this.introView.addStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controls = Parser.parseFile("input.txt");
                    cacheSize = introView.getCacheSize();
                    setNumber = introView.getSetNumber();
                    blockSize = introView.getBlockSize();

                    if (setNumber == 1) {
                        cacheType = CacheType.ASSOCIATIVE;
                    } else if (setNumber * blockSize == cacheSize) {
                        cacheType = CacheType.DIRECT;
                    } else {
                        cacheType = CacheType.SETASSOCIATIVE;
                    }
                    if (introView.getChooseWrite().equals("Write-back")) {
                        writeMechanism = WriteMechanism.WRITEBACK;
                    } else {
                        writeMechanism = WriteMechanism.WRITETHROUGH;
                    }
                    if (introView.getChooseReplacement().equals("LRU")) {
                        replacementMechanism = ReplacementMechanism.LRU;
                    } else {
                        replacementMechanism = ReplacementMechanism.FIFO;
                    }
                } catch (NumberFormatException exception) {
                    introView.showErrorMessage("Please give every detail!");
                    introView.setVisible(true);
                } finally {
                    if (cacheSize != null && setNumber != null && blockSize != null) {
                        introView.setVisible(false);
                        cache = new Cache(cacheType, cacheSize, writeMechanism, replacementMechanism, setNumber, blockSize);
                        buildTables();
                        if (writeMechanism == WriteMechanism.WRITETHROUGH) {
                            getCacheView().updateCacheTable(cache.returnCacheContent(), setNumber * (cacheSize / (setNumber * blockSize)), 2 + blockSize);
                        } else {
                            getCacheView().updateCacheTable(cache.returnCacheContent(), setNumber * (cacheSize / (setNumber * blockSize)), 3 + blockSize);
                        }
                        cacheView.setVisible(true);
                    }
                }
            }
        });
    }

    private void buildTables () {
        List<String> columns = new ArrayList<>(List.of("Dirty", "V", "Tag"));
        if (writeMechanism == WriteMechanism.WRITETHROUGH) {
            columns.remove(0);
        }

        for (int i = 0; i < blockSize; i++) {
            columns.add("Data");
        }
        cacheView.setCacheTable(cacheSize / blockSize, columns.toArray(new String[0]), setNumber, blockSize, cacheSize);
        cacheView.setMemoryTable(memory);
        cacheView.setNextLabel("Executing next: " + toString(controls.get(0)));
        cacheView.setQueueArea(buildQueue());
    }

    private String toString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : array) {
            stringBuilder.append(s + " ");
        }

        return stringBuilder.toString();
    }

    private String buildQueue() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Queued up:\n");

        for (String[] s : controls) {
            if (s != controls.get(0)) {
                stringBuilder.append(toString(s) + "\n");
            }
        }

        return stringBuilder.toString();
    }

    public CacheView getCacheView() {
        return cacheView;
    }

    class IterateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] current = controls.remove(0);
                cacheView.setNextLabel("Executing next: " + Controller.this.toString(controls.get(0)));
                cacheView.setQueueArea(buildQueue());
                Integer input = Integer.parseInt(current[1]);
                Integer tag = input / blockSize;
                Integer index = tag % blockSize;
                Integer offset = input % blockSize;

                switch (current[0]) {
                    case "w" :
                        if (cacheType == CacheType.DIRECT) {
                            readFromMemory(tag, index);
                            cache.changeOneByte(index, offset, current[2].charAt(0), tag);
                            if (writeMechanism == WriteMechanism.WRITEBACK) {
                                cache.setDirty(index);
                            } else {
                                cacheView.updateMemoryTable(memory, tag, blockSize);
                            }
                        } else if (cacheType == CacheType.ASSOCIATIVE ){
                            readFromMemoryAssociative(tag, index);
                            index = cache.getTagByIndex(tag);
                            cache.changeOneByte(index, offset, current[2].charAt(0), tag);
                            if (writeMechanism == WriteMechanism.WRITEBACK) {
                                cache.setDirty(index);
                            } else {
                                cacheView.updateMemoryTable(memory, tag, blockSize);
                            }
                        } else {
                            index = tag % setNumber;
                            readFromMemorySetAssociative(tag, index);
                            Integer blockIndex = cache.getTagByIndex(tag, index);
                            cache.changeOneByte(index, offset, current[2].charAt(0), tag);
                            if (writeMechanism == WriteMechanism.WRITEBACK) {
                                cache.setDirty(index, blockIndex);
                            } else {
                                cacheView.updateMemoryTable(memory, tag, blockSize);
                            }
                        }
                        updateTables();
                        break;
                    case "r":
                        if (cacheType == CacheType.DIRECT) {
                            readFromMemory(tag, index);
                        } else if (cacheType == CacheType.ASSOCIATIVE){
                            readFromMemoryAssociative(tag , index);
                        } else {
                            index = tag % setNumber;
                            readFromMemorySetAssociative(tag, index);
                        }
                        updateTables();
                        break;
                    case "flush":
                        for (Set s : cache.getSets()) {
                            for (Block b : s.getBlocks()) {
                                if (b.getTag() != -1) {
                                    for (int i = 0; i < blockSize; i++) {
                                        memory.set(b.getTag() * blockSize + i, b.getContent().get(i));
                                    }
                                    cacheView.updateMemoryTable(memory, b.getTag(), blockSize);
                                    b.makeNull();
                                    updateTables();
                                }
                            }
                        }

                        break;
                }
            } catch (IndexOutOfBoundsException ex) {
                cacheView.showMessage("End of simulation!");
                ex.printStackTrace();
            }
        }

        private void readFromMemory(Integer tag, Integer index) {
            if(!cache.isHit(tag, index)) {
                if (writeMechanism == WriteMechanism.WRITEBACK) {
                    if (cache.checkIsDirty(index)) {
                        for (int i = 0; i < blockSize; i++) {
                            memory.set(cache.getTag(index) * blockSize + i, cache.getContent(0).get(i));
                        }
                        cacheView.updateMemoryTable(memory, cache.getTag(index), blockSize);
                    }
                }
                List<MyByte> searched = memory.subList(tag * blockSize, tag * blockSize + blockSize);
                cache.add(tag, index, searched);
            }
        }

        private void readFromMemoryAssociative(Integer tag, Integer index) {
            if(!cache.isHit(tag, index)) {
                if (writeMechanism == WriteMechanism.WRITEBACK) {
                    index = cache.getIndexToModify();
                    if (cache.checkIsDirty(index)) {
                        for (int i = 0; i < blockSize; i++) {

                            memory.set(cache.getTag(index) * blockSize + i, cache.getContent(index).get(i));
                        }
                        cacheView.updateMemoryTable(memory, cache.getTag(index), blockSize);
                    }
                }
                List<MyByte> searched = memory.subList(tag * blockSize, tag * blockSize + blockSize);
                cache.add(tag, index, searched);
            } else {
                if (replacementMechanism == ReplacementMechanism.LRU) {
                    cache.modifyTimeStamp(tag, index);
                }
            }
        }

        private void readFromMemorySetAssociative(Integer tag, Integer index) {
            if(!cache.isHit(tag, index)) {
                if (writeMechanism == WriteMechanism.WRITEBACK) {
                    System.out.println("Searching for " + tag + " in " + index);
                    Block block = cache.getBlockToModify(index);
                    if (block == null) {
                        System.out.println("created new block");
                        block = new Block(blockSize);
                    }
                    System.out.println(block.getDirty());
                    if (block.getDirty()) {
                        for (int i = 0; i < blockSize; i++) {
                            System.out.println(i + " " + (block.getTag() * blockSize + i));
                            memory.set(block.getTag() * blockSize + i, block.getContent().get(i));
                        }
                        cacheView.updateMemoryTable(memory, block.getTag(), blockSize);
                    }
                }
                List<MyByte> searched = memory.subList(tag * blockSize, tag * blockSize + blockSize);
                cache.add(tag, index, searched);
            } else {
                if (replacementMechanism == ReplacementMechanism.LRU) {
                    cache.modifyTimeStamp(tag, index);
                }
            }
        }

        private void updateTables() {
            if (writeMechanism == WriteMechanism.WRITETHROUGH) {
                getCacheView().updateCacheTable(cache.returnCacheContent(), setNumber * (cacheSize / (setNumber * blockSize)), 2 + blockSize);
            } else {
                getCacheView().updateCacheTable(cache.returnCacheContent(), setNumber * (cacheSize / (setNumber * blockSize)), 3 + blockSize);
            }
        }

    }
}
