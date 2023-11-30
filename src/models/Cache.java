package models;

import utils.CacheType;
import utils.ReplacementMechanism;
import utils.WriteMechanism;

public class Cache {
    private CacheType cacheType;
    private WriteMechanism writeMechanism;
    private ReplacementMechanism replacementMechanism;
    private Integer numberOfSets;
    private Set[] sets;
    private Integer blockSize;

    public Cache(CacheType cacheType, WriteMechanism writeMechanism, ReplacementMechanism replacementMechanism, Integer numberOfSets, Integer blockSize) {
        this.numberOfSets = numberOfSets;
        this.writeMechanism = writeMechanism;
        this.sets = new Set[numberOfSets];
        this.cacheType = cacheType;
        this.replacementMechanism = replacementMechanism;
        this.blockSize = blockSize;
    }
}
