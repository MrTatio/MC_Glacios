package roundaround.mcmods.glacios.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import roundaround.mcmods.glacios.client.renderer.tileentity.RendererPrismShard;
import roundaround.mcmods.glacios.tileentity.TileEntityPrismShard;

public class BlockPrismShard extends Block implements ITileEntityProvider {

    public BlockPrismShard() {
        super(Material.glass);
        this.setLightLevel(0.5F);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeGlass);
        this.setCreativeTab(CreativeTabs.tabBlock);
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
        int dir = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        
        if (dir == 0)
            dir = 2;
        else if (dir == 2)
            dir = 0;
        
        if (dir < 4)
            world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    }

}
