package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import roundaround.mcmods.glacios.Glacios;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLifeLog extends BlockLog {

    private IIcon iconTop;
    private IIcon iconSide;

    public BlockLifeLog() {
        super();
    }

    @Override
    public void func_149651_a(IIconRegister iconRegister) {
        iconTop = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5) + "Top");
        iconSide = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5) + "Side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon func_150163_b(int p_150163_1_) {
        return iconSide;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon func_150161_d(int p_150161_1_) {
        return iconTop;
    }

}
