package roundaround.mcmods.glacios;

import roundaround.mcmods.glacios.block.*;
import net.minecraft.block.Block;
import net.minecraftforge.client.IRenderHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Glacios.MODID, version = Glacios.VERS)
public class Glacios {
	public static final String MODID = "glacios";
	public static final String VERS = "0.01a";
	
	@SideOnly(Side.CLIENT)
	public static IRenderHandler skyRenderer;
	
	public static final Block slate = new BlockSlate();
}
