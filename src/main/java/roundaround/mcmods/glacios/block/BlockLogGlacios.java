package roundaround.mcmods.glacios.block;

import java.util.List;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogGlacios extends BlockLog {

    public static final int soul = 0;
    public static final int taigaGiant = 1;

    public static final String[] names = new String[] { "soul", "taigaGiant" };

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < names.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return MathHelper.clamp_int(meta & 3, 0, names.length - 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.field_150167_a = new IIcon[names.length];
        this.field_150166_b = new IIcon[names.length];

        for (int i = 0; i < this.names.length; ++i) {
            this.field_150167_a[i] = iconRegister.registerIcon(this.getTextureName() + "_" + names[i] + "_side");
            this.field_150166_b[i] = iconRegister.registerIcon(this.getTextureName() + "_" + names[i] + "_top");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int meta) {
        return this.field_150167_a[meta % this.field_150167_a.length];
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int meta) {
        return this.field_150166_b[meta % this.field_150166_b.length];
    }

}
