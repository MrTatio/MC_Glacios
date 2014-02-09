package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockDirt;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.GlaciosBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGelisolFrost extends BlockDirt {

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    public BlockGelisolFrost() {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
        return side == 1 ? this.iconTop : (side == 0 ? GlaciosBlocks.gelisol.func_149733_h(side) : this.field_149761_L);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149673_e(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return side == 1 ? this.iconTop : (side == 0 ? GlaciosBlocks.gelisol.func_149733_h(side) : this.field_149761_L);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149651_a(IIconRegister register) {
        this.field_149761_L = register.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5) + "Side");
        this.iconTop = register.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5) + "Top");
    }

}
