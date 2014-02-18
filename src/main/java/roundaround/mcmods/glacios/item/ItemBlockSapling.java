package roundaround.mcmods.glacios.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import roundaround.mcmods.glacios.block.BlockSaplingGlacios;

public class ItemBlockSapling extends ItemBlock {

    public ItemBlockSapling(Block block) {
        super(block);
    }

    @Override
    public int getMetadata(int meta) {
        return meta & ((BlockSaplingGlacios) this.field_150939_a).names.length;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        BlockSaplingGlacios block = (BlockSaplingGlacios) field_150939_a;
        return super.getUnlocalizedName() + "." + block.names[itemStack.getItemDamage()];
    }

}
