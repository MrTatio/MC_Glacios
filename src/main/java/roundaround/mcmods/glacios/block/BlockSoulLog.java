package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import roundaround.mcmods.glacios.Glacios;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSoulLog extends BlockLog {

    private IIcon iconTop;
    private IIcon iconSide;

    public BlockSoulLog() {
        super();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        iconTop = iconRegister.registerIcon(Glacios.MODID + ":" + this.getUnlocalizedName().substring(5) + "Top");
        iconSide = iconRegister.registerIcon(Glacios.MODID + ":" + this.getUnlocalizedName().substring(5) + "Side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int meta) {
        return iconSide;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int meta) {
        return iconTop;
    }

}
