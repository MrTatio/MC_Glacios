package roundaround.mcmods.glacios.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenGlaciosSoulTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulSapling extends BlockSapling {

    private IIcon icon;

    public BlockSoulSapling() {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icon = iconRegister.registerIcon(Glacios.MODID + ":" + this.getUnlocalizedName().substring(5));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

    @Override
    public void func_149878_d(World world, int x, int y, int z, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z))
            return;

        world.setBlock(x, y, z, Blocks.air, 0, 4);
        WorldGenerator gen = new WorldGenGlaciosSoulTree(true, rand.nextInt(10) == 0);

        if (!gen.generate(world, rand, x, y, z)) {
            world.setBlock(x, y, z, this, 0, 4);
        }
    }

    public boolean isSupportedByBlock(Block block) {
        return block == Blocks.grass || block == Blocks.dirt || block == Blocks.stone || block == Blocks.packed_ice || block == GlaciosBlocks.ash || block == GlaciosBlocks.gelisol
                || block == GlaciosBlocks.gelisolFrost || block == GlaciosBlocks.slate;
    }

}
