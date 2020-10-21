package youyihj.collision.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.collision.block.CollisionBlock;
import youyihj.collision.fluid.CollisionFluid;
import youyihj.collision.fluid.FluidRegistrar;
import youyihj.collision.item.CollisionItem;
import youyihj.collision.item.ItemRegistryHandler;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class ModelRegistryHandler {
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        for (CollisionItem item : ItemRegistryHandler.items) {
            item.getModelRLs().forEach((meta, modelResourceLocation) ->
                    ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation));
        }
        for (ItemBlock itemBlock : ItemRegistryHandler.itemBlocks) {
            ((CollisionBlock) itemBlock.getBlock()).getModelRLs().forEach((meta, modelRL) ->
                    ModelLoader.setCustomModelResourceLocation(itemBlock, meta, modelRL));
        }
        for (CollisionFluid fluid : FluidRegistrar.fluids) {
            fluid.getModelRLs().forEach((meta, model) ->
                    ModelLoader.setCustomStateMapper(fluid.getBlock(), new StateMapperBase() {
                        @Override
                        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                            return model;
                        }
                    }));
        }
    }
}
