package mytown.util;

import mytown.MyTown;
import mytown.datasource.MyTownDatasource;
import mytown.entities.BlockWhitelist;
import mytown.entities.Town;
import mytown.entities.TownBlock;
import mytown.entities.flag.FlagType;
import mytown.proxies.DatasourceProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Utils class for random useful things
 */
public class MyTownUtils {

    private MyTownUtils() {

    }

    /**
     * Returns the town at the specified position or null if nothing found.
     */
    public static Town getTownAtPosition(int dim, int x, int z) {
        TownBlock block = getDatasource().getBlock(dim, x, z);
        if (block == null)
            return null;
        return block.getTown();
    }

    /**
     * Gets the town at the entity's position
     */
    protected static Town getTownFromEntity(Entity entity) {
        return getTownAtPosition(entity.dimension, entity.chunkCoordX, entity.chunkCoordZ);
    }

    /**
     * Returns the Flag name from the selector's Lore tag
     */
    public static String getFlagNameFromLore(EntityPlayer player) {
        ItemStack currentStack = player.inventory.getCurrentItem();
        NBTTagList lore = currentStack.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);
        String flagLore = lore.getStringTagAt(1);
        return flagLore.substring(8); // We use hacks in here
    }

    /**
     * Returns the Town name from the selector's Lore tag
     */
    public static String getTownNameFromLore(EntityPlayer player) {
        ItemStack currentStack = player.inventory.getCurrentItem();
        NBTTagList lore = currentStack.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);
        String flagLore = lore.getStringTagAt(2);
        return flagLore.substring(8);
    }

    /**
     * Gets the nearby tile entities of the specified tile entity and of the specified type
     */
    public static List<TileEntity> getNearbyTileEntity(TileEntity te, Class<? extends TileEntity> type) {
        List<TileEntity> result = new ArrayList<TileEntity>();
        int[] dx = {0, 1, 0, -1, 0, 0};
        int[] dy = {1, 0, -1, 0, 0, 0};
        int[] dz = {0, 0, 0, 0, 1, -1};

        for (int i = 0; i < 6; i++) {
            TileEntity found = te.getWorldObj().getTileEntity(te.xCoord + dx[i], te.yCoord + dy[i], te.zCoord + dz[i]);
            if (found != null && type.isAssignableFrom(found.getClass())) {
                MyTown.instance.LOG.info("Found tile entity " + found + " for class " + type.getName());
                result.add(found);
            }
        }
        return result;
    }

    /**
     * Searches if the specified block is whitelisted in any town
     */
    public static boolean isBlockWhitelisted(int dim, int x, int y, int z, FlagType flagType) {
        Town town = getTownAtPosition(dim, x >> 4, z >> 4);
        if (town == null)
            return false;
        BlockWhitelist bw = town.getBlockWhitelist(dim, x, y, z, flagType);
        if (bw != null) {
            if (bw.isDeleted()) {
                getDatasource().deleteBlockWhitelist(bw, town);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Gets all towns in a range
     */
    public static List<Town> getTownsInRange(int dim, int x, int z, int rangeX, int rangeZ) {
        List<Town> list = new ArrayList<Town>();
        for (int i = x - rangeX; i <= x + rangeX; i++) {
            for (int j = z - rangeZ; j <= z + rangeZ; j++) {
                Town town = getTownAtPosition(dim, i >> 4, j >> 4);
                if (town != null)
                    list.add(town);
            }
        }
        return list;
    }

    /**
     * Takes the selector tool (for plots) from the player.
     */
    public static void takeSelectorToolFromPlayer(EntityPlayer player) {
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getDisplayName().equals(Constants.EDIT_TOOL_NAME)) {
                player.inventory.decrStackSize(i, 1);
                return;
            }
        }
    }

    /**
     * Gets the datasource
     */
    public static MyTownDatasource getDatasource() {
        return DatasourceProxy.getDatasource();
    }
}
