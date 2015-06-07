package mytown.protection.segment;

import mytown.entities.flag.FlagType;
import mytown.protection.segment.enums.BlockType;
import mytown.protection.segment.getter.Getters;

/**
 * Offers protection for blocks
 */
public class SegmentBlock extends Segment {
    private final int meta;
    private final BlockType type;

    public SegmentBlock(Class<?> theClass, Getters getters, FlagType flag, Object denialValue, String conditionString, BlockType blockType, int meta) {
        super(theClass, getters, flag, denialValue, conditionString);
        this.meta = meta;
        this.type = blockType;
    }

    public int getMeta() {
        return meta;
    }

    public BlockType getType() {
        return type;
    }
}
