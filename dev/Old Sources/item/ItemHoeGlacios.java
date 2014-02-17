package glacios.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemHoe;

public class ItemHoeGlacios extends ItemHoe {

	public ItemHoeGlacios(int id, EnumToolMaterial toolMaterial) {
		super(id, toolMaterial);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5));
	}

}
