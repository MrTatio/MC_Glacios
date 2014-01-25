package roundaround.mcmods.glacios.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import roundaround.mcmods.glacios.Glacios;

public class BlockSlate extends Block {

    public BlockSlate() {
        super(Material.field_151576_e);
        this.func_149647_a(CreativeTabs.tabBlock);
        this.func_149663_c("Slate");
        this.func_149658_d(Glacios.MODID + ":" + this.func_149739_a());
    }

}
