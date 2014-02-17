package glacios.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemRecord;

public class ItemIceRecord extends ItemRecord {

	public ItemIceRecord(int id, String title) {
		super(id, title);
	}

	/*
	 * Return the title for this record.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordTitle() {
		return "Punchaface - " + recordName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("Glacios:record_" + this.recordName);
	}

}
