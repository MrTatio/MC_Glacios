package roundaround.mcmods.glacios;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class GlaciosConfig {

    public static GlaciosConfig instance = new GlaciosConfig();
    
    private static final String MAIN = "general";
    private static final String GEN = "worldgen";
    private static final String BIOME = "biome";

    private GlaciosConfig() {}

    public static int dimID = 17;

    public static int biome_plateau = 81;
    public static int biome_ocean = 82;

    public static int gen_razorGrass = 8;

    public static Configuration config;

    public static void init(File configFile) {
        config = new Configuration(configFile);

        try {
            config.load();
            loadVals();
            
        } catch (Exception e) {
            // TODO: Error report.  Crash?  Load defaults?
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
    
    private static void loadVals() {
        dimID = config.get(MAIN, "dimID", dimID).getInt();
        
        biome_plateau = config.get(BIOME, "plateau", biome_plateau).getInt();
        biome_ocean = config.get(BIOME, "ocean", biome_ocean).getInt();
        
        gen_razorGrass = config.get(GEN, "razorGrass", gen_razorGrass).getInt();
    }

}
