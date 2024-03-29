package roundaround.mcmods.glacios;

import java.util.ArrayList;

import net.minecraft.world.biome.BiomeGenBase;
import roundaround.mcmods.glacios.world.biome.BiomeGenGlacios;
import roundaround.mcmods.glacios.world.biome.BiomeGenGlaciosHills;
import roundaround.mcmods.glacios.world.biome.BiomeGenGlaciosLake;
import roundaround.mcmods.glacios.world.biome.BiomeGenGlaciosOcean;
import roundaround.mcmods.glacios.world.biome.BiomeGenGlaciosPlateau;
import roundaround.mcmods.glacios.world.biome.BiomeGenGlaciosTaiga;
import roundaround.mcmods.glacios.world.biome.BiomeGenGlaciosVolcano;

public class GlaciosBiomes {
    
    private static final ArrayList<BiomeMapping> biomes = new ArrayList<BiomeMapping>();
    
    public static final BiomeGenBase.Height height_Default = new BiomeGenBase.Height(0.1F, 0.2F);
    public static final BiomeGenBase.Height height_HighDefault = new BiomeGenBase.Height(1.2F, 0.2F);
    public static final BiomeGenBase.Height height_ShallowWaters = new BiomeGenBase.Height(-0.5F, 0.0F);
    public static final BiomeGenBase.Height height_Oceans = new BiomeGenBase.Height(-1.0F, 0.1F);
    public static final BiomeGenBase.Height height_DeepOceans = new BiomeGenBase.Height(-1.8F, 0.1F);
    public static final BiomeGenBase.Height height_LowPlains = new BiomeGenBase.Height(0.125F, 0.05F);
    public static final BiomeGenBase.Height height_MidPlains = new BiomeGenBase.Height(0.2F, 0.2F);
    public static final BiomeGenBase.Height height_LowHills = new BiomeGenBase.Height(0.45F, 0.3F);
    public static final BiomeGenBase.Height height_MidHills = new BiomeGenBase.Height(0.8F, 0.4F);
    public static final BiomeGenBase.Height height_HugeHills = new BiomeGenBase.Height(1.0F, 0.8F);
    public static final BiomeGenBase.Height height_MidPlateausSuperFlat = new BiomeGenBase.Height(1.2F, 0.005F);
    public static final BiomeGenBase.Height height_MidPlateaus = new BiomeGenBase.Height(1.2F, 0.025F);
    public static final BiomeGenBase.Height height_HighPlateaus = new BiomeGenBase.Height(1.5F, 0.025F);
    public static final BiomeGenBase.Height height_Shores = new BiomeGenBase.Height(0.0F, 0.025F);
    public static final BiomeGenBase.Height height_RockyWaters = new BiomeGenBase.Height(0.1F, 0.8F);
    public static final BiomeGenBase.Height height_LowIslands = new BiomeGenBase.Height(0.2F, 0.3F);
    public static final BiomeGenBase.Height height_PartiallySubmerged = new BiomeGenBase.Height(-0.2F, 0.1F);
    
    public static BiomeGenGlacios plateau;
    public static BiomeGenGlacios lake;
    public static BiomeGenGlacios ocean;
    public static BiomeGenGlacios volcano;
    public static BiomeGenGlacios taiga;
    public static BiomeGenGlacios hills;

    public static void init() {
        plateau = (BiomeGenGlacios) new BiomeGenGlaciosPlateau(GlaciosConfig.biome_plateau).setHeight(height_MidPlateaus).setBiomeName("Plateau");
        lake = (BiomeGenGlacios) new BiomeGenGlaciosLake(GlaciosConfig.biome_lake).setHeight(height_ShallowWaters).setBiomeName("Lake");
        ocean = (BiomeGenGlacios) new BiomeGenGlaciosOcean(GlaciosConfig.biome_ocean).setHeight(height_Oceans).setBiomeName("Ocean");
        volcano = (BiomeGenGlacios) new BiomeGenGlaciosVolcano(GlaciosConfig.biome_volcano).setHeight(height_LowHills).setBiomeName("Volcano");
        taiga = (BiomeGenGlacios) new BiomeGenGlaciosTaiga(GlaciosConfig.biome_taiga).setHeight(height_MidHills).setBiomeName("Taiga");
        hills = (BiomeGenGlacios) new BiomeGenGlaciosHills(GlaciosConfig.biome_hills).setHeight(height_MidHills).setBiomeName("Hills");
        
        registerBiome(plateau, 3);
        registerBiome(lake, 6);
        registerBiome(ocean, 10);
        registerBiome(volcano, 6);
        registerBiome(taiga);
        registerBiome(hills);
    }
    
    public static void registerBiome(BiomeGenBase biome) {
        registerBiome(biome, 1);
    }
    
    public static void registerBiome(BiomeGenBase biome, int rarity) {
        biomes.add(new BiomeMapping(biome.biomeID, rarity));
    }
    
    public static BiomeMapping[] getBiomeList() {
        BiomeMapping[] biomesArr = new BiomeMapping[] { };
        return biomes.toArray(biomesArr);
    }
    
    public static class BiomeMapping {
        public final int biomeId;
        public final int rarity;
        
        public BiomeMapping(int biomeId, int rarity) {
            this.biomeId = biomeId;
            this.rarity = rarity;
        }
    }
}
