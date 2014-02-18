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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import roundaround.mcmods.glacios.GlaciosBlocks;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenSoulTree;
import roundaround.mcmods.glacios.world.gen.feature.WorldGenTaigaGiant;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSaplingGlacios extends BlockSapling {

    public static final int soul = 0;
    public static final int taigaGiant = 1;

    public static final String[] names = new String[] { "soul", "taigaGiant" };
    public static final IIcon[] icons = new IIcon[names.length];

    public BlockSaplingGlacios() {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icons[MathHelper.clamp_int(meta, 0, names.length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        for (int i = 0; i < names.length; i++) {
            this.icons[i] = iconRegister.registerIcon(this.getTextureName() + "_" + names[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < names.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return MathHelper.clamp_int(meta, 0, names.length - 1);
    }

    @Override
    public void func_149878_d(World world, int x, int y, int z, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z))
            return;

        int meta = world.getBlockMetadata(x, y, z);
        world.setBlock(x, y, z, Blocks.air, 0, 4);
        WorldGenerator gen = null;

        switch (meta) {
            case soul:
                gen = new WorldGenSoulTree(true);
                break;
            case taigaGiant:
                gen = new WorldGenTaigaGiant(true);
                break;
            default:
                break;
        }

        if (gen == null || !gen.generate(world, rand, x, y, z)) {
            world.setBlock(x, y, z, this, meta, 4);
        }
    }

    public static boolean isSupportedByBlock(Block block, int meta) {
        return block == Blocks.grass || block == Blocks.dirt || block == Blocks.packed_ice || block == GlaciosBlocks.ash || block == GlaciosBlocks.gelisol
                || block == GlaciosBlocks.gelisolFrost;
    }

}
