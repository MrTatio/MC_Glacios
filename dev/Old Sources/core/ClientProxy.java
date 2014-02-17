package glacios.core;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import glacios.client.model.ModelArcticFox;
import glacios.client.model.ModelWraith;
import glacios.client.renderer.entity.RenderArcticFox;
import glacios.client.renderer.entity.RenderWraith;
import glacios.core.CommonProxy;
import glacios.entity.EntityArcticFox;
import glacios.entity.EntityWraith;

public class ClientProxy extends CommonProxy {
   
    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenderers() {
    	RenderingRegistry.registerEntityRenderingHandler(EntityWraith.class, new RenderWraith(new ModelWraith(), 0.5F));
    	RenderingRegistry.registerEntityRenderingHandler(EntityArcticFox.class, new RenderArcticFox(new ModelArcticFox(), 0.5F));
    }
       
}