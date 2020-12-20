package youyihj.collision.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import youyihj.collision.block.absorber.Absorber;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class ColliderRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ColliderRecipe> {
    public static final ColliderRecipeSerializer INSTANCE = new ColliderRecipeSerializer();

    @Override
    public ColliderRecipe read(ResourceLocation recipeId, JsonObject json) {
        int level = json.getAsJsonPrimitive("level").getAsInt();
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
        int[] intArray = buffer.readVarIntArray();
        Absorber.Type[][] temp = new Absorber.Type[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int a = intArray[i + j * 3];
                if (a == 0) {
                    temp[i][j] = null;
                } else if (a == 2) {
                    temp[i][j] = Absorber.Type.PROTON;
                } else if (a == 3) {
                    temp[i][j] = Absorber.Type.NEUTRON;
                }
            }
        }
        return new ColliderRecipe(recipeId, buffer.readInt(), buffer.readItemStack(), temp);
    }

    @Override
    public void write(PacketBuffer buffer, ColliderRecipe recipe) {
        buffer.writeItemStack(recipe.getRecipeOutput());
        buffer.writeVarInt(recipe.getLevel());
        buffer.writeVarIntArray(recipe.inSerial());
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
