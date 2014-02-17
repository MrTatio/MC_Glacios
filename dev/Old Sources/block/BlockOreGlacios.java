package glacios.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOreGlacios extends Block {

    private int dropID;

    public BlockOreGlacios(int id, int drop) {
        super(id, Material.rock);
        setCreativeTab(CreativeTabs.tabBlock);
        dropID = drop;
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
    public int idDropped(int par1, Random par2Random, int par3) {
        return dropID == -1 ? blockID : dropID;
    }
}
