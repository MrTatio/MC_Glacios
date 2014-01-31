package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import roundaround.mcmods.glacios.Glacios;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRazerGrass extends BlockTallGrass {

    private IIcon icon;

    public BlockRazerGrass() {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
        return icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149651_a(IIconRegister iconRegister) {
        this.icon = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5));
    }
}
