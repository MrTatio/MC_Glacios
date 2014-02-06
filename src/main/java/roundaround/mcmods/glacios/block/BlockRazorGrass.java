package roundaround.mcmods.glacios.block;

import java.util.List;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import roundaround.mcmods.glacios.Glacios;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRazorGrass extends BlockTallGrass {

    private IIcon[] icons = new IIcon[2];

    public BlockRazorGrass() {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
        if (meta >= this.icons.length)
            meta = 0;
        return this.icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149651_a(IIconRegister iconRegister) {
        this.icons[0] = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5));
        this.icons[1] = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5) + "Tall");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149666_a(Item item, CreativeTabs tab, List list) {
        for (int i = 1; i < icons.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
