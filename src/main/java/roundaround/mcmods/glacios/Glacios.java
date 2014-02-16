package roundaround.mcmods.glacios;

import net.minecraftforge.common.DimensionManager;
import roundaround.mcmods.glacios.world.WorldProviderGlacios;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Glacios.MODID, name = Glacios.NAME, version = Glacios.VERS)
public class Glacios {
    public static final String NAME = "Glacios";
	public static final String MODID = "glacios";
	public static final String VERS = "0.12a";
	
	@EventHandler
	public void preload(FMLPreInitializationEvent evnt) {
        GlaciosConfig.init(evnt.getSuggestedConfigurationFile());
        
        GlaciosBlocks.init();
        GlaciosBiomes.init();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent evnt) {
	    DimensionManager.registerProviderType(GlaciosConfig.dimID, WorldProviderGlacios.class, false);
	    DimensionManager.registerDimension(GlaciosConfig.dimID, GlaciosConfig.dimID);
	}
}
