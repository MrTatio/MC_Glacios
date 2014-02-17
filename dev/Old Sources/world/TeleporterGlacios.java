package glacios.world;

import glacios.block.GlaciosBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.PortalPosition;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterGlacios extends Teleporter {

    private WorldServer world;
    private Random random;

    /*
     * Stores successful portal placement locations for rapid lookup.
     */
    private final LongHashMap destinationCoordinateCache = new LongHashMap();

    /*
     * A list of valid keys for the destinationCoordainteCache. These are based on the X & Z of the players initial
     * location.
     */
    private final List<Long> destinationCoordinateKeys = new ArrayList<Long>();

    public TeleporterGlacios(WorldServer worldServer) {
        super(worldServer);
        world = worldServer;
        random = new Random(worldServer.getSeed());
    }

    /*
     * Place an entity in a nearby portal, creating one if necessary.
     */
    @Override
    public void placeInPortal(Entity entity, double x, double y, double z, float w) {
        if (world.provider.dimensionId != -1) {
            if (!placeInExistingPortal(entity, x, y, z, w)) {
                makePortal(entity);
                placeInExistingPortal(entity, x, y, z, w);
            }
        } else {
            int i = MathHelper.floor_double(entity.posX);
            int j = MathHelper.floor_double(entity.posY) - 1;
            int k = MathHelper.floor_double(entity.posZ);
            byte b0 = 1;
            byte b1 = 0;

            for (int l = -2; l <= 2; ++l) {
                for (int i1 = -2; i1 <= 2; ++i1) {
                    for (int j1 = -1; j1 < 3; ++j1) {
                        int k1 = i + i1 * b0 + l * b1;
                        int l1 = j + j1;
                        int i2 = k + i1 * b1 - l * b0;
                        boolean flag = j1 < 0;
                        world.setBlockAndMetadataWithNotify(k1, l1, i2, flag ? Block.blockNetherQuartz.blockID : 0, flag ? 1 : 0, 2);
                    }
                }
            }

            entity.setLocationAndAngles(i, j, k, entity.rotationYaw, 0.0F);
            entity.motionX = entity.motionY = entity.motionZ = 0.0D;
        }
    }

    /*
     * Place an entity in a nearby portal which already exists.
     */
    @Override
    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float w) {
        short maxDist = 128;
        double dist = -1.0D;
        int posX = 0;
        int posY = 0;
        int posZ = 0;
        int entityPosX = MathHelper.floor_double(entity.posX);
        int entityPosZ = MathHelper.floor_double(entity.posZ);
        long j1 = ChunkCoordIntPair.chunkXZ2Int(entityPosX, entityPosZ);
        boolean newPortalLocation = true;

        if (destinationCoordinateCache.containsItem(j1)) {
            PortalPosition portalposition = (PortalPosition) this.destinationCoordinateCache.getValueByKey(j1);
            dist = 0.0D;
            posX = portalposition.posX;
            posY = portalposition.posY;
            posZ = portalposition.posZ;
            portalposition.lastUpdateTime = world.getTotalWorldTime();
            newPortalLocation = false;
        } else {
            for (int posXitr = entityPosX - maxDist; posXitr <= entityPosX + maxDist; ++posXitr) {
                double xDist = posXitr + 0.5D - entity.posX;

                for (int posZitr = entityPosZ - maxDist; posZitr <= entityPosZ + maxDist; ++posZitr) {
                    double zDist = posZitr + 0.5D - entity.posZ;

                    for (int posYitr = world.getActualHeight() - 1; posYitr >= 0; --posYitr) {
                        if (world.getBlockId(posXitr, posYitr, posZitr) == GlaciosBlocks.portalGlacios.blockID) {
                            while (world.getBlockId(posXitr, posYitr - 1, posZitr) == GlaciosBlocks.portalGlacios.blockID) {
                                --posYitr;
                            }

                            double yDist = posYitr + 0.5D - entity.posY;
                            double distItr = xDist * xDist + yDist * yDist + zDist * zDist;

                            if (dist < 0.0D || distItr < dist) {
                                dist = distItr;
                                posX = posXitr;
                                posY = posYitr;
                                posZ = posZitr;
                            }
                        }
                    }
                }
            }
        }

        if (dist < 0.0D) {
            return false;
        } else {
            if (newPortalLocation) {
                destinationCoordinateCache.add(j1, new PortalPosition(this, posX, posY, posZ, world.getTotalWorldTime()));
                destinationCoordinateKeys.add(Long.valueOf(j1));
            }
            double posXd = posX + 0.5D;
            double posYd = posY + 0.5D;
            double posZd = posZ + 0.5D;
            int portalDir = -1;

            if (world.getBlockId(posX - 1, posY, posZ) == GlaciosBlocks.portalGlacios.blockID) {
                portalDir = 2;
            }

            if (world.getBlockId(posX + 1, posY, posZ) == GlaciosBlocks.portalGlacios.blockID) {
                portalDir = 0;
            }

            if (world.getBlockId(posX, posY, posZ - 1) == GlaciosBlocks.portalGlacios.blockID) {
                portalDir = 3;
            }

            if (world.getBlockId(posX, posY, posZ + 1) == GlaciosBlocks.portalGlacios.blockID) {
                portalDir = 1;
            }

            int entityTeleDir = entity.getEntityData().getInteger("GlaciosTeleportDirection");

            if (portalDir > -1) {
                int portalDirRotate = Direction.rotateLeft[portalDir];
                int offsetX = Direction.offsetX[portalDir];
                int offsetZ = Direction.offsetZ[portalDir];
                int offsetXRotate = Direction.offsetX[portalDirRotate];
                int offsetZRotate = Direction.offsetZ[portalDirRotate];
                boolean isRotateSpaceFree = !world.isAirBlock(posX + offsetX + offsetXRotate, posY, posZ + offsetZ + offsetZRotate)
                        || !world.isAirBlock(posX + offsetX + offsetXRotate, posY + 1, posZ + offsetZ + offsetZRotate);
                boolean isSpaceFree = !world.isAirBlock(posX + offsetX, posY, posZ + offsetZ) || !world.isAirBlock(posX + offsetX, posY + 1, posZ + offsetZ);

                if (isRotateSpaceFree && isSpaceFree) {
                    portalDir = Direction.footInvisibleFaceRemap[portalDir]; // rotateOpposite
                    portalDirRotate = Direction.footInvisibleFaceRemap[portalDirRotate]; // rotateOpposite
                    offsetX = Direction.offsetX[portalDir];
                    offsetZ = Direction.offsetZ[portalDir];
                    offsetXRotate = Direction.offsetX[portalDirRotate];
                    offsetZRotate = Direction.offsetZ[portalDirRotate];
                    int posXoffset = posX - offsetXRotate;
                    posXd -= offsetXRotate;
                    int posZoffset = posZ - offsetZRotate;
                    posZd -= offsetZRotate;
                    isRotateSpaceFree = !world.isAirBlock(posXoffset + offsetX + offsetXRotate, posY, posZoffset + offsetZ + offsetZRotate)
                            || !world.isAirBlock(posXoffset + offsetX + offsetXRotate, posY + 1, posZoffset + offsetZ + offsetZRotate);
                    isSpaceFree = !world.isAirBlock(posXoffset + offsetX, posY, posZoffset + offsetZ) || !world.isAirBlock(posXoffset + offsetX, posY + 1, posZoffset + offsetZ);
                }

                float rotateOffScale = 0.5F;
                float offScale = 0.5F;

                if (!isRotateSpaceFree && isSpaceFree) {
                    rotateOffScale = 1.0F;
                } else if (isRotateSpaceFree && !isSpaceFree) {
                    rotateOffScale = 0.0F;
                } else if (isRotateSpaceFree && isSpaceFree) {
                    offScale = 0.0F;
                }

                posXd += offsetXRotate * rotateOffScale + offScale * offsetX;
                posZd += offsetZRotate * rotateOffScale + offScale * offsetZ;
                float motionXScaleX = 0.0F;
                float motionZScaleZ = 0.0F;
                float motionXScaleZ = 0.0F;
                float motionZScaleX = 0.0F;

                if (portalDir == entityTeleDir) {
                    motionXScaleX = 1.0F;
                    motionZScaleZ = 1.0F;
                } else if (portalDir == Direction.footInvisibleFaceRemap[entityTeleDir]) { // rotateOpposite
                    motionXScaleX = -1.0F;
                    motionZScaleZ = -1.0F;
                } else if (portalDir == Direction.enderEyeMetaToDirection[entityTeleDir]) { // rotateLeft
                    motionXScaleZ = 1.0F;
                    motionZScaleX = -1.0F;
                } else {
                    motionXScaleZ = -1.0F;
                    motionZScaleX = 1.0F;
                }

                double motionX = entity.motionX;
                double motionZ = entity.motionZ;
                entity.motionX = motionX * motionXScaleX + motionZ * motionZScaleX;
                entity.motionZ = motionX * motionXScaleZ + motionZ * motionZScaleZ;
                entity.rotationYaw = w - entityTeleDir * 90 + portalDir * 90;
            } else {
                entity.motionX = entity.motionY = entity.motionZ = 0.0D;
            }

            entity.setLocationAndAngles(posXd, posYd, posZd, entity.rotationYaw, entity.rotationPitch);
            return true;
        }
    }

    /**
     * Create a new portal near an entity.
     */
    @Override
    public boolean makePortal(Entity entity) {
        byte b0 = 16;
        double d0 = -1.0D;
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = random.nextInt(4);
        int i2;
        double d1;
        double d2;
        int j2;
        int k2;
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        double d3;
        double d4;

        for (i2 = i - b0; i2 <= i + b0; ++i2) {
            d1 = i2 + 0.5D - entity.posX;

            for (j2 = k - b0; j2 <= k + b0; ++j2) {
                d2 = j2 + 0.5D - entity.posZ;
                label274:

                for (k2 = world.getActualHeight() - 1; k2 >= 0; --k2) {
                    if (world.isAirBlock(i2, k2, j2)) {
                        while (k2 > 0 && world.isAirBlock(i2, k2 - 1, j2)) {
                            --k2;
                        }

                        for (i3 = l1; i3 < l1 + 4; ++i3) {
                            l2 = i3 % 2;
                            k3 = 1 - l2;

                            if (i3 % 4 >= 2) {
                                l2 = -l2;
                                k3 = -k3;
                            }

                            for (j3 = 0; j3 < 3; ++j3) {
                                for (i4 = 0; i4 < 4; ++i4) {
                                    for (l3 = -1; l3 < 4; ++l3) {
                                        k4 = i2 + (i4 - 1) * l2 + j3 * k3;
                                        j4 = k2 + l3;
                                        int l4 = j2 + (i4 - 1) * k3 - j3 * l2;

                                        if (l3 < 0 && !world.getBlockMaterial(k4, j4, l4).isSolid() || l3 >= 0 && !world.isAirBlock(k4, j4, l4)) {
                                            continue label274;
                                        }
                                    }
                                }
                            }

                            d4 = k2 + 0.5D - entity.posY;
                            d3 = d1 * d1 + d4 * d4 + d2 * d2;

                            if (d0 < 0.0D || d3 < d0) {
                                d0 = d3;
                                l = i2;
                                i1 = k2;
                                j1 = j2;
                                k1 = i3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D) {
            for (i2 = i - b0; i2 <= i + b0; ++i2) {
                d1 = i2 + 0.5D - entity.posX;

                for (j2 = k - b0; j2 <= k + b0; ++j2) {
                    d2 = j2 + 0.5D - entity.posZ;
                    label222:

                    for (k2 = world.getActualHeight() - 1; k2 >= 0; --k2) {
                        if (world.isAirBlock(i2, k2, j2)) {
                            while (k2 > 0 && world.isAirBlock(i2, k2 - 1, j2)) {
                                --k2;
                            }

                            for (i3 = l1; i3 < l1 + 2; ++i3) {
                                l2 = i3 % 2;
                                k3 = 1 - l2;

                                for (j3 = 0; j3 < 4; ++j3) {
                                    for (i4 = -1; i4 < 4; ++i4) {
                                        l3 = i2 + (j3 - 1) * l2;
                                        k4 = k2 + i4;
                                        j4 = j2 + (j3 - 1) * k3;

                                        if (i4 < 0 && !world.getBlockMaterial(l3, k4, j4).isSolid() || i4 >= 0 && !world.isAirBlock(l3, k4, j4)) {
                                            continue label222;
                                        }
                                    }
                                }

                                d4 = k2 + 0.5D - entity.posY;
                                d3 = d1 * d1 + d4 * d4 + d2 * d2;

                                if (d0 < 0.0D || d3 < d0) {
                                    d0 = d3;
                                    l = i2;
                                    i1 = k2;
                                    j1 = j2;
                                    k1 = i3 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i5 = l;
        int j5 = i1;
        j2 = j1;
        int k5 = k1 % 2;
        int l5 = 1 - k5;

        if (k1 % 4 >= 2) {
            k5 = -k5;
            l5 = -l5;
        }

        boolean flag;

        if (d0 < 0.0D) {
            if (i1 < 70) {
                i1 = 70;
            }

            if (i1 > world.getActualHeight() - 10) {
                i1 = world.getActualHeight() - 10;
            }

            j5 = i1;

            for (k2 = -1; k2 <= 1; ++k2) {
                for (i3 = 1; i3 < 3; ++i3) {
                    for (l2 = -1; l2 < 3; ++l2) {
                        k3 = i5 + (i3 - 1) * k5 + k2 * l5;
                        j3 = j5 + l2;
                        i4 = j2 + (i3 - 1) * l5 - k2 * k5;
                        flag = l2 < 0;
                        world.setBlockAndMetadataWithNotify(k3, j3, i4, flag ? Block.blockNetherQuartz.blockID : 0, flag ? 1 : 0, 2);
                    }
                }
            }
        }

        for (k2 = 0; k2 < 4; ++k2) {
            for (i3 = 0; i3 < 4; ++i3) {
                for (l2 = -1; l2 < 4; ++l2) {
                    k3 = i5 + (i3 - 1) * k5;
                    j3 = j5 + l2;
                    i4 = j2 + (i3 - 1) * l5;
                    flag = i3 == 0 || i3 == 3 || l2 == -1 || l2 == 3;
                    world.setBlockAndMetadataWithNotify(k3, j3, i4, flag ? Block.blockNetherQuartz.blockID : GlaciosBlocks.portalGlacios.blockID, flag ? 1 : 0, 2);
                }
            }

            for (i3 = 0; i3 < 4; ++i3) {
                for (l2 = -1; l2 < 4; ++l2) {
                    k3 = i5 + (i3 - 1) * k5;
                    j3 = j5 + l2;
                    i4 = j2 + (i3 - 1) * l5;
                    world.notifyBlocksOfNeighborChange(k3, j3, i4, world.getBlockId(k3, j3, i4));
                }
            }
        }

        return true;
    }

}
