package roundaround.mcmods.glacios.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockRazerGrass extends Block implements IPlantable, IGrowable {

    public BlockRazerGrass() {
        super(Material.field_151592_s);
        this.func_149676_a(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
        this.func_149647_a(CreativeTabs.tabDecorations);
        this.func_149675_a(true);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return null;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean var5) {
        return false;
    }

    @Override
    public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
        return false;
    }

    @Override
    public void func_149853_b(World world, Random rand, int x, int y, int z) {
    }

    @Override
    public AxisAlignedBB func_149668_a(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @Override
    public boolean func_149662_c() {
        return false;
    }

    @Override
    public boolean func_149686_d() {
        return false;
    }

    @Override
    public boolean func_149742_c(World world, int x, int y, int z) {
        return true;
    }

}
