package glacios.block;

import glacios.core.Glacios;

import java.util.Random;

import net.minecraft.block.BlockVine;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIceVine extends BlockVine implements IShearable {

    public BlockIceVine(int id) {
        super(id);
    }

    /*
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 16777215;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /*
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1) {
        return 16777215;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /*
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return 16777215;
    }

    /*
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int posX, int posY, int posZ, Random rand) {
        if (rand.nextInt(4) != 0)
            return;

        int i = rand.nextInt(4);
        double x = posX + rand.nextFloat();
        double y = (float) posY - 1 + rand.nextFloat();
        double z = posZ + rand.nextFloat();
        // double motX = ((double) rand.nextFloat() - 0.5D) * 0.5D;
        // double motY = ((double) rand.nextFloat() - 0.5D) * 0.5D;
        // double motZ = ((double) rand.nextFloat() - 0.5D) * 0.5D;

        switch (i) {
        case 0:
            x += 0.5F;
            break;
        case 1:
            z += 0.5F;
            break;
        case 2:
            x -= 0.5F;
            break;
        case 3:
            z -= 0.5F;
            break;
        }

        EntityFX entityFX = new EntityPortalFX(world, x, y, z, 0, -0.2 - rand.nextDouble() * 0.8, 0);
        float f = rand.nextFloat() * 0.6F + 0.4F;
        entityFX.setRBGColorF(1.0F * f, 1.0F * f, 1.0F * f);
        Glacios.mc.effectRenderer.addEffect(entityFX);
    }

}
