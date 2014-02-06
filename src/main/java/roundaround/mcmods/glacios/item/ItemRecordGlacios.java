package roundaround.mcmods.glacios.item;

import net.minecraft.item.ItemRecord;
import cpw.mods.fml.client.FMLClientHandler;

public class ItemRecordGlacios extends ItemRecord {

    public ItemRecordGlacios(String title) {
        super(title);
        FMLClientHandler.instance().getClient().ingameGUI.setRecordPlayingMessage(title);
    }

}
