package youyihj.collision.tile;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * @author youyihj
 */
public class TileEntityBound {
    private final Block block;
    private final Class<? extends TileEntity> tileEntity;
    private final String tileEntityName;

    public TileEntityBound(Block block, Class<? extends TileEntity> tileEntity, String tileEntityName) {
        this.block = block;
        this.tileEntity = tileEntity;
        this.tileEntityName = tileEntityName;
    }

    public Block getBlock() {
        return block;
    }

    public Class<? extends TileEntity> getTileEntity() {
        return tileEntity;
    }

    public String getTileEntityName() {
        return tileEntityName;
    }
}
