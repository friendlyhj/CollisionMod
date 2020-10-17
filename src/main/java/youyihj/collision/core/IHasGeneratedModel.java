package youyihj.collision.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.io.FileUtils;
import youyihj.collision.Collision;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public interface IHasGeneratedModel extends IHasModel {
    String DIR = "resources/" + Collision.MODID + "/blockstates/";

    default boolean isAlwaysOverrideModelFile() {
        return false;
    }

    default boolean isGenerating() {
        return true;
    }

    Class<?> getRegistryEntryType();

    default void generate() {
        if (!isGenerating()) return;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        this.getModelRLs().forEach((meta, modelRL) -> {
            File file = new File(DIR + modelRL.getResourcePath() + ".json");
            if (isAlwaysOverrideModelFile() || !file.exists()) {
                JsonObject all = new JsonObject();
                if (Item.class.isAssignableFrom(getRegistryEntryType())) {
                    all.addProperty("forge_marker", 1);
                    JsonObject defaults = new JsonObject();
                    defaults.addProperty("model", "minecraft:builtin/generated");
                    JsonObject textures = new JsonObject();
                    textures.addProperty("layer0", Collision.MODID + ":items/" + modelRL.getResourcePath());
                    defaults.add("textures", textures);
                    all.add("defaults", defaults);
                    JsonObject variants = new JsonObject();
                    JsonArray inventory = new JsonArray();
                    JsonObject transform = new JsonObject();
                    transform.addProperty("transform", "forge:default-item");
                    inventory.add(transform);
                    variants.add("inventory", inventory);
                    all.add("variants", variants);
                }

                if (Block.class.isAssignableFrom(getRegistryEntryType())) {
                    all.addProperty("forge_marker", 1);
                    JsonObject defaults = new JsonObject();
                    JsonObject textures = new JsonObject();
                    textures.addProperty("all", Collision.MODID + ":blocks/" + modelRL.getResourcePath());
                    textures.addProperty("particle", Collision.MODID + ":blocks/" + modelRL.getResourcePath());
                    defaults.add("textures", textures);
                    defaults.addProperty("model", "minecraft:cube_all");
                    defaults.addProperty("uvlock", true);
                    all.add("defaults", defaults);
                    JsonArray jsonArray = new JsonArray();
                    jsonArray.add(new JsonObject());
                    JsonObject variants = new JsonObject();
                    variants.add("normal", jsonArray);
                    variants.add("inventory", jsonArray);
                    all.add("variants", variants);
                }

                if (!all.has("forge_marker"))
                    throw new UnsupportedGeneratedTypeException(modelRL + "is linked to an unsupported generated type:" + getRegistryEntryType().getName());

                try {
                    FileUtils.writeStringToFile(file, gson.toJson(all), StandardCharsets.UTF_8, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
