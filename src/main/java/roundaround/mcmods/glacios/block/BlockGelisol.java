package roundaround.mcmods.glacios.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockGelisol extends Block {

    public BlockGelisol() {
        super(Material.ground);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) { // TODO: Add warmth detection.
//            world.setBlock(x, y, z, GlaciosBlocks.gelisolFrost);
        }
    }

}
