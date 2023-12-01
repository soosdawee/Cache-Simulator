package controllers;

import models.Cache;
import models.MyByte;
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
                        int i = 0;
                        if (writeMechanism == WriteMechanism.WRITEBACK) {
                            i = 1;
                        }
                        cacheView.updateCacheTable(cache.returnCacheContent(), setNumber * (cacheSize / (setNumber * blockSize)), i + 2 + blockSize);
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
        cacheView.setCacheTable(cacheSize / blockSize, columns.toArray(new String[0]));
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
                System.out.println(tag + " " + index);

                switch (current[0]) {
                    case "w" :
                        break;
                    case "r":
                        if(!cache.isHit(tag, index)) {
                            if (cache.checkIsDirty(tag, index)) {
                                for (int i = 0; i < blockSize; i++) {
                                    memory.set(cache.getTag(index) * blockSize + i, cache.getContent(0).get(i));
                                }
                            }
                            List<MyByte> searched = memory.subList(tag * blockSize, tag * blockSize + blockSize);
                            cache.add(tag, index, searched);
                            int i = 0;
                            if (writeMechanism == WriteMechanism.WRITEBACK) {
                                i = 1;
                            }
                            getCacheView().updateCacheTable(cache.returnCacheContent(), setNumber * (cacheSize / (setNumber * blockSize)), i + 2 + blockSize);
                        }
                        break;
                    case "f":
                        break;
                }
            } catch (IndexOutOfBoundsException ex) {
                cacheView.showMessage("Hits and all");
                // ex.printStackTrace();
            }
        }

    }
}
