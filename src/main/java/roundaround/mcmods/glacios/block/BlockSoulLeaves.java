package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import roundaround.mcmods.glacios.Glacios;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulLeaves extends BlockLeaves {

    private IIcon icon[] = new IIcon[2];

    public BlockSoulLeaves() {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149691_a(int side, int meta) {
        if (FMLClientHandler.instance().getClient().isFancyGraphicsEnabled())
            return this.icon[0];
        else
            return this.icon[1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149651_a(IIconRegister iconRegister) {
        this.icon[0] = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5));
        this.icon[1] = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5) + "Opaque");
    }

    @Override
    public String[] func_150125_e() {
        return null;
    }

}
