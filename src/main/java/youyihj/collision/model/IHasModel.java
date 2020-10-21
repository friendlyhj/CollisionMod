package youyihj.collision.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import java.util.HashMap;

public interface IHasModel {
    HashMap<Integer, ModelResourceLocation> getModelRLs();

    ModelType getModelType();
}
