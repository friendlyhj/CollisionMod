package youyihj.collision.block.spawner;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import youyihj.collision.tile.BlockHasTileEntityBase;


/**
 * @author youyihj
 */
public abstract class Spawner extends BlockHasTileEntityBase {
    public Spawner(String name) {
        super(name, Properties.create(Material.ROCK)
                .hardnessAndResistance(8.0f, 30000.0f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
        );
    }

    @Override
    public boolean enableBlockLootGenerator() {
        return false;
    }
}
