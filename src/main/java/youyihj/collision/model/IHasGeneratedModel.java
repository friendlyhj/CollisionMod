package youyihj.collision.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import youyihj.collision.util.Utils;

public interface IHasGeneratedModel extends IHasModel {
    default String getModelDir(ModelResourceLocation location) {
        return "../src/main/resources/assets/" + location.getResourceDomain() + "/blockstates/";
    }

    default boolean isAlwaysOverrideModelFile() {
        return false;
    }

    default boolean isGeneratingModel() {
        return Utils.isDevEnvironment();
    }
}
