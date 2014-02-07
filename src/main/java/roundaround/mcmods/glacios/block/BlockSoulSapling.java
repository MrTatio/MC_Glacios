package roundaround.mcmods.glacios.block;

import java.util.List;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import roundaround.mcmods.glacios.Glacios;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulSapling extends BlockSapling {
    
    private IIcon icon;

    public BlockSoulSapling() {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
        return this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149651_a(IIconRegister iconRegister) {
        this.icon = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149666_a(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

}
