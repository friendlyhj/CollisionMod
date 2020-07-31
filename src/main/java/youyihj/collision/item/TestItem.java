package youyihj.collision.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TestItem extends CollisionItem {
    public static final String ID = "test";
    public TestItem() {
        super(ID);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        player.sendStatusMessage(new TextComponentString("test"), true);
        return EnumActionResult.PASS;
    }
}
