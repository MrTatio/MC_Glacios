package roundaround.mcmods.glacios.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import roundaround.mcmods.glacios.block.BlockLogGlacios;

public class ItemBlockLog extends ItemBlock {
    public ItemBlockLog(Block block) {
        super(block);

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta & ((BlockLogGlacios) this.field_150939_a).names.length;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        BlockLogGlacios block = (BlockLogGlacios) field_150939_a;
        return super.getUnlocalizedName() + "." + block.names[itemStack.getItemDamage()];
    }
}