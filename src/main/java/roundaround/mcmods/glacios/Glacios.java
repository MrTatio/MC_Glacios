package roundaround.mcmods.glacios;

import net.minecraftforge.common.DimensionManager;
import roundaround.mcmods.glacios.world.WorldProviderGlacios;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

@Mod(modid = Glacios.MODID, name = Glacios.NAME, version = Glacios.VERS)
public class Glacios {
    public static final String NAME = "Glacios";
    public static final String MODID = "glacios";
    public static final String VERS = "0.15a";

    @EventHandler
    public void preload(FMLPreInitializationEvent evnt) {
        GlaciosConfig.init(evnt.getSuggestedConfigurationFile());

        GlaciosBlocks.init();
        GlaciosItems.init();
        GlaciosBiomes.init();
        GlaciosEntities.init(this);

        // FMLCommonHandler.instance().bus().register(this);
    }

    @EventHandler
    public void load(FMLInitializationEvent evnt) {
        DimensionManager.registerProviderType(GlaciosConfig.dimID, WorldProviderGlacios.class, false);
        DimensionManager.registerDimension(GlaciosConfig.dimID, GlaciosConfig.dimID);
    }

    @SubscribeEvent
    public void onTick(ClientTickEvent tick) {

    }
}
