package roundaround.mcmods.glacios.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import roundaround.mcmods.glacios.client.renderer.tileentity.RendererPrismShard;
import roundaround.mcmods.glacios.tileentity.TileEntityPrismShard;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPrismShard extends Block implements ITileEntityProvider {

    public static final float[][] colors = {
        {  80F / 255F, 220F / 255F, 210F / 255F, 0.9F }, // Teal
        { 220F / 255F,  80F / 255F, 160F / 255F, 0.9F }, // Pink
        { 120F / 255F,  80F / 255F, 220F / 255F, 0.9F }  // Lavender
    };

    public BlockPrismShard() {
        super(Material.glass);
        this.setLightLevel(0.5F);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeGlass);
        this.setCreativeTab(CreativeTabs.tabBlock);
        
        float radius = 0.4F;
        this.setBlockBounds(0.5F - radius, 0.0F, 0.5F - radius, 0.5F + radius, 0.8F, 0.5F + radius);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int uhh) {
        return new TileEntityPrismShard();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RendererPrismShard.renderID;
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata >> 2;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
        int dir = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int color = itemStack.getItemDamage() << 2;

        if (dir == 0)
            dir = 2;
        else if (dir == 2)
            dir = 0;

        world.setBlockMetadataWithNotify(x, y, z, color | dir, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemStack, CreativeTabs tab, List subBlocks) {
        for (int i = 0; i < colors.length; i++) {
            subBlocks.add(new ItemStack(itemStack, 1, i));
        }
    }

}
