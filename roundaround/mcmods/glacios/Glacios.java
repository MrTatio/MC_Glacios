package roundaround.mcmods.glacios;

import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.DimensionManager;
import roundaround.mcmods.glacios.world.WorldProviderGlacios;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Glacios.MODID, name = Glacios.NAME, version = Glacios.VERS)
public class Glacios {
    public static final String NAME = "Glacios";
	public static final String MODID = "glacios";
	public static final String VERS = "0.01a";
	public static final int DIMID = 17;
	
	@SideOnly(Side.CLIENT)
	public static IRenderHandler skyRenderer;
	
	@EventHandler
	public void preload(FMLPreInitializationEvent evnt) {
        GlaciosBlocks.init();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent evnt) {
	    DimensionManager.registerProviderType(DIMID, WorldProviderGlacios.class, false);
	    DimensionManager.registerDimension(DIMID, DIMID);
	}
}
