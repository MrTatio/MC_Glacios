package glacios.block;

import glacios.item.GlaciosItems;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlacite extends BlockOreGlacios {

    public BlockGlacite(int id) {
        super(id, GlaciosItems.glaciteCrystal.itemID);
        slipperiness = 0.78F;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return super.idDropped(par1, par2Random, par3);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return blockAccess.getBlockId(x, y, z) == blockID ? false : super.shouldSideBeRendered(blockAccess, x, y, z, 1 - side);
    }

    @Override
    public boolean onBlockActivated(World worldObj, int par2, int par3, int par4, EntityPlayer entity, int par6, float par7, float par8, float par9) {
        return super.onBlockActivated(worldObj, par2, par3, par4, entity, par6, par7, par8, par9);
    }

}
