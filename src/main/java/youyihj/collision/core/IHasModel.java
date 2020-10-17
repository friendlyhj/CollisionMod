package youyihj.collision.core;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import java.util.HashMap;

public interface IHasModel {
    HashMap<Integer, ModelResourceLocation> getModelRLs();
}
