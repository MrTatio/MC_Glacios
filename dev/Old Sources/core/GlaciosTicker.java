package glacios.core;

import glacios.block.GlaciosBlocks;
import glacios.client.particle.EntityGlowFX;
import glacios.world.TeleporterGlacios;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class GlaciosTicker implements ITickHandler {

	public static World world;
	public static int particleLightTicker = 0;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.SERVER))) {
			onServerTickStart();
		}
		if (type.equals(EnumSet.of(TickType.RENDER))){
			onRenderTickStart();
		}
		if (type.equals(EnumSet.of(TickType.CLIENT))) {
			onClientTickStart();
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.SERVER))) {
			onServerTickEnd();
		}
		if (type.equals(EnumSet.of(TickType.RENDER))){
			onRenderTickEnd();
		}
		if (type.equals(EnumSet.of(TickType.CLIENT))) {
			onClientTickEnd();
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER, TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return null;
	}

	public void onServerTickStart() {
		MinecraftServer server = MinecraftServer.getServer();

		List<EntityPlayer> players = getPlayers();
		for (EntityPlayer player0 : players) {
			EntityPlayerMP player = (EntityPlayerMP) player0;

			int timeInPortal = player.getEntityData().getInteger("GlaciosTimeInPortal");
			int timeUntilPortal = player.getEntityData().getInteger("GlaciosTimeUntilPortal");

			if (player.getEntityData().getBoolean("GlaciosInPortal")) {
				if (server.getAllowNether()) {
					if (player.ridingEntity == null && timeInPortal++ >= player.getMaxInPortalTime()) {
						timeInPortal = player.getMaxInPortalTime();
						timeUntilPortal = player.getPortalCooldown();

						byte dim = Glacios.dimID;
						if (player.dimension == Glacios.dimID)
							dim = 0;
						player.mcServer.getConfigurationManager().transferPlayerToDimension(player, dim,
								new TeleporterGlacios(player.mcServer.worldServerForDimension(dim)));
					}

					player.getEntityData().setBoolean("GlaciosInPortal", false);
				}
			} else {
				if (timeInPortal > 0) {
					timeInPortal -= 8;
				}

				if (timeInPortal < 0) {
					timeInPortal = 0;
				}
			}

			if (timeUntilPortal > 0) {
				timeUntilPortal -= 2;
			}

			player.getEntityData().setInteger("GlaciosTimeInPortal", timeInPortal);
			player.getEntityData().setInteger("GlaciosTimeUntilPortal", timeUntilPortal);
		}
	}
	
	public void onServerTickEnd() {
		
	}

	public void onRenderTickStart() {
        GlaciosBlocks.iceLeaves.setGraphicsLevel(Glacios.mc.gameSettings.fancyGraphics);
	}
	
	public void onRenderTickEnd() {
	}

	public void onClientTickStart() {
		if (Glacios.soundManager != null) {
			Glacios.soundManager.onTick();
		}
        EntityGlowFX.clearLights();
        EntityGlowFX.resets.clear();
	}
	
	public void onClientTickEnd() {
		EntityGlowFX.updateLights();
        EntityGlowFX.lights.clear();
	}

	public List<EntityPlayer> getPlayers() {
		List<EntityPlayer> players = new LinkedList<EntityPlayer>();
		world = MinecraftServer.getServer().worldServerForDimension(0);
		if (world != null) {
			for (int i = 0; i < world.playerEntities.size(); i++) {
				players.add((EntityPlayer) world.playerEntities.get(i));
			}
		}
		world = MinecraftServer.getServer().worldServerForDimension(Glacios.dimID);
		if (world != null) {
			for (int i = 0; i < world.playerEntities.size(); i++) {
				players.add((EntityPlayer) world.playerEntities.get(i));
			}
		}
		return players;
	}

}
