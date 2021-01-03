package youyihj.collision.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import youyihj.collision.block.absorber.Absorber;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class ColliderRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ColliderRecipe> {
    public static final ColliderRecipeSerializer INSTANCE = new ColliderRecipeSerializer();

    private ColliderRecipeSerializer() {
        super();
    }

    @Override
    public ColliderRecipe read(ResourceLocation recipeId, JsonObject json) {
        int level = JSONUtils.getInt(json, "level", 1);
        Ingredient out = Ingredient.deserialize(json.get("result"));
        JsonArray jsonElements = json.getAsJsonArray("in");
        Absorber.Type[][] in = new Absorber.Type[3][3];
        for (int i = 0; i < 3; i++) {
            in[i] = outMapper(jsonElements.get(i).getAsString());
        }
        return new ColliderRecipe(recipeId, level, out.getMatchingStacks()[0], in);
    }

    @Nullable
    @Override
    public ColliderRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        int intSerialValue = buffer.readVarInt();
        Absorber.Type[][] temp = new Absorber.Type[3][3];
        for (int i = 8; i >= 0; i--) {
            switch (intSerialValue % 4) {
                case 2:
                    temp[i / 3][i % 3] = Absorber.Type.PROTON;
                    break;
                case 3:
                    temp[i / 3][i % 3] = Absorber.Type.NEUTRON;
                    break;
                default:
                    temp[i / 3][i % 3] = null;
            }
            intSerialValue >>= 2;
        }
        return new ColliderRecipe(recipeId, buffer.readVarInt(), buffer.readItemStack(), temp);
    }

    @Override
    public void write(PacketBuffer buffer, ColliderRecipe recipe) {
        buffer.writeVarInt(recipe.inSerial());
        buffer.writeVarInt(recipe.getLevel());
        buffer.writeItemStack(recipe.getRecipeOutput());
    }

    private static Absorber.Type[] outMapper(String s) {
        if (s.length() != 3) {
            throw new JsonSyntaxException("in length must be 3");
        }
        Absorber.Type[] temp = new Absorber.Type[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = outMapperSingle(s.charAt(i));
        }
        return temp;
    }

    private static Absorber.Type outMapperSingle(char s) {
        switch (Character.toUpperCase(s)) {
            case 'N':
                return Absorber.Type.NEUTRON;
            case 'P':
                return Absorber.Type.PROTON;
            default:
                return null;
        }
    }
}
