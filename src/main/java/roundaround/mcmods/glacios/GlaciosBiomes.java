package roundaround.mcmods.glacios;

import net.minecraft.world.biome.BiomeGenBase;
import roundaround.mcmods.glacios.world.biome.BiomeGenGlaciosTaiga;

public class GlaciosBiomes {
    
    // The taiga biome stuff is just here for reference.
    
    public static final int taigaId = 40;
    
    public static final BiomeGenBase.Height taigaHeight = new BiomeGenBase.Height(0.2F, 0.2F);
    
    public static BiomeGenBase taiga;

    public static void init() {
        taiga = new BiomeGenGlaciosTaiga(taigaId).setHeight(taigaHeight).setBiomeName("Taiga");
    }
}
