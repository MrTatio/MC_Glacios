package glacios.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemGlacios extends Item {

	public ItemGlacios(int id) {
		super(id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("Glacios:" + this.getUnlocalizedName().substring(5));
	}

}
