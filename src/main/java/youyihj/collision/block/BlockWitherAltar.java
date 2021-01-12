package youyihj.collision.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import youyihj.collision.data.DisableModelGenerator;

/**
 * @author youyihj
 */
@DisableModelGenerator
public class BlockWitherAltar extends BlockBase {
    private BlockWitherAltar() {
        super("wither_altar", AbstractBlock.Properties.create(Material.IRON));
    }

    public static final BlockWitherAltar INSTANCE = new BlockWitherAltar();
}
