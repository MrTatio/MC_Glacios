package glacios.core;

import glacios.block.GlaciosBlocks;
import glacios.client.audio.SoundManagerGlacios;
import glacios.entity.GlaciosEntities;
import glacios.item.GlaciosItems;
import glacios.world.WorldProviderGlacios;
import glacios.world.biome.BiomeGenGlacios;
import glacios.world.gen.layer.GenLayerBiomeGlacios;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "Glacios", name = "Glacios", version = "0.0.0.2")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Glacios {

    public static final Minecraft mc = FMLClientHandler.instance().getClient();

    public static final int dimID = 26;
    public static final int groundHeight = 192;

    public GlaciosTicker glaciosTicker;
    public static SoundManagerGlacios soundManager = new SoundManagerGlacios();

    @Instance("Glacios")
    public static Glacios instance;

    @SidedProxy(clientSide = "glacios.core.ClientProxy", serverSide = "glacios.core.CommonProxy")
    public static CommonProxy proxy;

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Init
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderers();

        glaciosTicker = new GlaciosTicker();
        TickRegistry.registerTickHandler(glaciosTicker, Side.SERVER);
        TickRegistry.registerTickHandler(glaciosTicker, Side.CLIENT);

        DimensionManager.registerProviderType(dimID, WorldProviderGlacios.class, true);
        DimensionManager.registerDimension(dimID, dimID);
        GenLayerBiomeGlacios.registerBiome(BiomeGenGlacios.glaciosMountains);
        GenLayerBiomeGlacios.registerBiome(BiomeGenGlacios.glaciosIslands, 33);
        GenLayerBiomeGlacios.registerBiome(BiomeGenGlacios.glaciosLake, 10);
        GenLayerBiomeGlacios.registerBiome(BiomeGenGlacios.glaciosOcean, 10);
        GenLayerBiomeGlacios.registerBiome(BiomeGenGlacios.glaciosPlains, 7);
        GenLayerBiomeGlacios.registerBiome(BiomeGenGlacios.glaciosForest, 7);

        GlaciosBlocks.RegisterBlocks();
        GlaciosItems.RegisterItems();
        GlaciosEntities.RegisterEntities();
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    }
}