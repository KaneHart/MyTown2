package mytown.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import mytown.Constants;
import mytown.core.Log;
import mytown.entities.Nation;
import mytown.entities.Rank;
import mytown.entities.Resident;
import mytown.entities.TownBlock;
import mytown.entities.town.AdminTown;
import mytown.entities.town.Town;
import mytown.interfaces.ITownFlag;
import mytown.interfaces.ITownPlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.Configuration;

// TODO Datasource pool?
// TODO Add logging
// TODO More error reporting
// TODO Link Residents to their Towns
// TODO Link Towns to their Nations
// TODO Load Plots
// TODO Link Residents to their Plots
// TODO Map of TownIds to their Towns?

/**
 * Abstract Datasource class. Extend to add more support
 * 
 * @author Joe Goett
 */
public abstract class MyTownDatasource {
	protected String configCat = "datasource";

	protected Log log;
	protected ConcurrentMap<String, Town> towns;
	protected ConcurrentMap<String, Resident> residents;
	protected ConcurrentMap<String, Nation> nations;
	protected ConcurrentMap<String, TownBlock> blocks;
	protected ConcurrentMap<String, Rank> ranks;
	protected ConcurrentMap<String, ITownPlot> plots;
	protected ConcurrentMap<String, ITownFlag> townFlags; //TODO: Saving twice in the datasource?
	protected ConcurrentMap<String, ITownFlag> plotFlags; //TODO: Saving twice in the datasource?
	
	/**
	 * Used for connecting to Databases. Returns if connection was successful
	 * 
	 * @return
	 */
	public abstract boolean connect() throws Exception;

	public void configure(Configuration config, Log log) {
		this.log = log;
		doConfig(config);
		towns = new ConcurrentHashMap<String, Town>();
		residents = new ConcurrentHashMap<String, Resident>();
		nations = new ConcurrentHashMap<String, Nation>();
		blocks = new ConcurrentHashMap<String, TownBlock>();
		ranks = new ConcurrentHashMap<String, Rank>();
		plots = new ConcurrentHashMap<String, ITownPlot>();
		townFlags = new ConcurrentHashMap<String, ITownFlag>();
		plotFlags = new ConcurrentHashMap<String, ITownFlag>();
	}

	/**
	 * Does implementation specific configuration
	 * 
	 * @param config
	 */
	protected abstract void doConfig(Configuration config);

	/**
	 * Saves everything to the Datasource
	 * 
	 * @throws Exception
	 */
	public abstract void save() throws Exception;

	/**
	 * Disconnects from the Datasource
	 * 
	 * @throws Exception
	 */
	public abstract void disconnect() throws Exception;

	// /////////////////////////////////////////////////////////////
	// Map Getters
	// /////////////////////////////////////////////////////////////

	/**
	 * Returns a Map of towns
	 * 
	 * @return
	 */
	public Map<String, Town> getTownsMap() {
		return towns;
	}

	/**
	 * Returns a Map of residents
	 * 
	 * @return
	 */
	public Map<String, Resident> getResidentsMap() {
		return residents;
	}

	/**
	 * Returns a Map of nations
	 * 
	 * @return
	 */
	public Map<String, Nation> getNationsMap() {
		return nations;
	}

	/**
	 * Returns a Map of the town blocks
	 * 
	 * @return
	 */
	public Map<String, TownBlock> getTownBlocksMap() {
		return blocks;
	}

	/**
	 * Returns a Map of ranks
	 * 
	 * @return
	 */
	public Map<String, Rank> getRanksMap() {
		return ranks;
	}
	
	/**
	 * Returns a Map of plots
	 * 
	 * @return
	 */
	public Map<String, ITownPlot> getPlotsMap() {
		return plots;
	}
	
	/**
	 * Returns a Map of flags in towns
	 * 
	 * @return
	 */
	public Map<String, ITownFlag> getTownFlagsMap() {
		return townFlags;
	}
	
	/**
	 * Returns a Map of flags in plots
	 * 
	 * @return
	 */
	public Map<String, ITownFlag> getPlotFlagsMap() {
		return plotFlags;
	}
	

	

	// /////////////////////////////////////////////////////////////
	// Collection Getters
	// /////////////////////////////////////////////////////////////

	/**
	 * Returns a Collection of the Towns
	 * 
	 * @return
	 */
	public Collection<Town> getTowns(boolean adminTownsIncluded) {
		if(adminTownsIncluded) return towns.values();
		Collection<Town> temp = new ArrayList<Town>();
		for(Town t : towns.values()) {
			if(!(t instanceof AdminTown))
				temp.add(t);
		}
		return temp;
	}

	/**
	 * Returns a Collection of the Residents
	 * 
	 * @return
	 */
	public Collection<Resident> getResidents() {
		return residents.values();
	}

	/**
	 * Returns a Collection of the Nations
	 * 
	 * @return
	 */
	public Collection<Nation> getNations() {
		return nations.values();
	}

	/**
	 * Returns a Collection of the TownBlocks
	 * 
	 * @return
	 */
	public Collection<TownBlock> getTownBlocks() {
		return blocks.values();
	}

	/**
	 * Returns a Collection of the Ranks
	 * 
	 * @return
	 */
	public Collection<Rank> getRanks() {
		return ranks.values();
	}

	/**
	 * Returns a Collection of the Plots
	 * 
	 * @return
	 */
	public Collection<ITownPlot> getPlots() {
		return plots.values();
	}

	
	/**
	 * Returns a Collection of the flags in towns
	 * 
	 * @return
	 */
	public Collection<ITownFlag> getTownFlags() {
		return townFlags.values();
	}
	
	/**
	 * Returns a Collection of the flags in plots
	 * 
	 * @return
	 */
	public Collection<ITownFlag> getPlotFlags() {
		return plotFlags.values();
	}
	
	// /////////////////////////////////////////////////////////////
	// Single Instance Getters
	// /////////////////////////////////////////////////////////////

	/**
	 * Gets a Town with the given name
	 * 
	 * @param name
	 * @return
	 */
	public Town getTown(String name) {
		return towns.get(name);
	}

	/**
	 * Gets a Resident with the given name
	 * 
	 * @param name
	 * @return
	 */
	public Resident getResident(String name) {
		return residents.get(name);
	}

	/**
	 * Gets a Nation with the given name
	 * 
	 * @param name
	 * @return
	 */
	public Nation getNation(String name) {
		return nations.get(name);
	}

	/**
	 * Gets a TownBlock with the given key, or null if it doesn't exist. Key Format: dimID;x;z
	 * 
	 * @param key
	 * @return
	 */
	public TownBlock getTownBlock(String key) {
		return blocks.get(key);
	}

	/**
	 * Gets a townblock at coordinates
	 * 
	 * @param x
	 * @param z
	 * @param inChunkCoords
	 * @return
	 */
	public TownBlock getTownBlock(int dim, int x, int z, boolean inChunkCoords) {
		return getTownBlock(dim, x, z, inChunkCoords, null);
	}
	
	/**
	 * Gets a townblock from a town at coordinates
	 * 
	 * @param dim
	 * @param x
	 * @param z
	 * @param inChunkCoords
	 * @param town
	 * @return
	 */
	public TownBlock getTownBlock(int dim, int x, int z, boolean inChunkCoords, Town town) {
		String key;
		if(inChunkCoords)
			key = dim + ";" + x + ";" + z;
		else
			key = dim + ";" + (x >> 4) + ";" + (z >> 4);
		return getTownBlock(key);
	}

	/**
	 * Gets a Rank from the Town specified
	 * 
	 * @param rank
	 * @return
	 */
	public Rank getRank(String rank, Town town) {
		if (town == null) return null;
		if (ranks.get(town.getName() + ";" + rank) == null) return null;
		return ranks.get(town.getName() + ";" + rank);
	}

	/**
	 * 
	 * Gets a Rank
	 * 
	 * @param key
	 *            should look like this: TownName;Rank
	 * @return
	 */
	public Rank getRank(String key) {
		return ranks.get(key);
	}

	/**
	 * Gets a Plot
	 * 
	 * @param key
	 * 			  
	 * @return
	 */
	public ITownPlot getPlot(String key) {
		return plots.get(key);
	}
	
	/**
	 * Gets a flag in a town
	 * 
	 * @param key
	 * @return
	 */
	public ITownFlag getTownFlag(String key) {
		return townFlags.get(key);
	}
	
	/**
	 * Gets a flag in the specified town
	 * 
	 * @param flagName
	 * @param town
	 * @return
	 */
	public ITownFlag getTownFlag(String flagName, Town town) {
		return townFlags.get(flagName + ";" + town.getName());
	}
	
	/**
	 * Gets a flag in the town with the specified name
	 * 
	 * @param flagName
	 * @param townName
	 * @return
	 */
	public ITownFlag getTownFlag(String flagName, String townName) {
		return townFlags.get(flagName + ";" + townName);
	}
	
	/**
	 * Gets a flag in a plot
	 * 
	 * @param key
	 * @return
	 */
	public ITownFlag getPlotFlag(String key) {
		return plotFlags.get(key);
	}
	
	/**
	 * Gets a flag at the specified plot
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public ITownFlag getPlotFlag(String flagName, ITownPlot plot) {
		return plotFlags.get(flagName + ";" + plot.getKey());
	}
	
	// /////////////////////////////////////////////////////////////
	// Checkers?
	// /////////////////////////////////////////////////////////////

	/**
	 * Checks if a Town with the given name exists
	 * 
	 * @param townName
	 * @return
	 */
	public boolean hasTown(String townName) {
		return towns.containsKey(townName);
	}

	/**
	 * Checks if the Resident with the given UUID exists
	 * 
	 * @param residentUUID
	 *            1.6 is player name, 1.7> is player UUID
	 * @return
	 */
	public boolean hasResident(String residentUUID) {
		return residents.containsKey(residentUUID);
	}

	/**
	 * Checks if the Nation with the given name exists
	 * 
	 * @param nationName
	 * @return
	 */
	public boolean hasNation(String nationName) {
		return nations.containsKey(nationName);
	}

	/**
	 * Checks if the TownBlock with the given key exists
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasTownBlock(String key) {
		return blocks.containsKey(key);
	}

	/**
	 * Checks if the Rank with the given key exists in the Datasource
	 * 
	 * @param rankName
	 * @return
	 */
	public boolean hasRank(String key) {
		return ranks.containsKey(key);
	}
	
	/**
	 * Checks if the Plot with the given key exists in the Datasource
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasPlot(String key) {
		return plots.containsKey(key);
	}
	
	/**
	 * Checks if the town flag exists
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasTownFlag(String key) {
		return townFlags.containsKey(key);
	}

	/**
	 * Checks if the plot flag exists
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasPlotFlag(String key) {
		return plotFlags.containsKey(key);
	}
	
	// /////////////////////////////////////////////////////////////
	// Loaders
	// /////////////////////////////////////////////////////////////

	/**
	 * Loads all the Residents into the Datasource
	 */
	public abstract void loadResidents() throws Exception;

	/**
	 * Loads all the Towns into the Datasource
	 */
	public abstract void loadTowns() throws Exception;

	/**
	 * Loads all the Nations into the Datasource
	 */
	public abstract void loadNations() throws Exception;

	/**
	 * Loads all TownBlocks for the given town into the Datasource
	 */
	public abstract void loadTownBlocks(Town town) throws Exception;

	/**
	 * Loads all Ranks into the Datasource
	 */
	public abstract void loadRanks() throws Exception;

	/**
	 * Loads all Plots into the Datasource
	 */
	public abstract void loadPlots() throws Exception;
	
	// /////////////////////////////////////////////////////////////
	// Insert Single Entity
	// /////////////////////////////////////////////////////////////

	/**
	 * Adds a Town to the Datasource and executes a query
	 * 
	 * @param town
	 * @throws Exception
	 */
	public abstract void insertTown(Town town) throws Exception;

	/**
	 * Adds a Resident to the Datasource and executes a query
	 * 
	 * @param resident
	 * @throws Exception
	 */
	public abstract void insertResident(Resident resident) throws Exception;

	/**
	 * Adds a Nation to the Datasource and executes a query
	 * 
	 * @param nation
	 * @throws Exception
	 */
	public abstract void insertNation(Nation nation) throws Exception;

	/**
	 * Adds a TownBlock to the Datasource and executes a query
	 * 
	 * @param townBlock
	 * @throws Exception
	 */
	public abstract void insertTownBlock(TownBlock townBlock) throws Exception;

	/**
	 * Adds a Rank to the Datasource and executes a query
	 * 
	 * @param town
	 * @param rank
	 * @throws Exception
	 */
	public abstract void insertRank(Rank rank) throws Exception;

	/**
	 * Adds a TownPlot to the Datasource and executes a query
	 * 
	 * @param plot
	 * @throws Exception
	 */
	public abstract void insertPlot(ITownPlot plot) throws Exception;
	
	// /////////////////////////////////////////////////////////////
	// Insert Multiple Entities
	// /////////////////////////////////////////////////////////////

	/**
	 * Adds multiple Towns to the Datasource and executes a query
	 * 
	 * @param towns
	 * @throws Exception
	 */
	public void insertTowns(Town... towns) throws Exception {
		for (Town town : towns) {
			insertTown(town);
		}
	}

	/**
	 * Adds multiple Residents to the Datasource and executes a query
	 * 
	 * @param residents
	 * @throws Exception
	 */
	public void insertResidents(Resident... residents) throws Exception {
		for (Resident res : residents) {
			insertResident(res);
		}
	}

	/**
	 * Adds multiple Nations to the Datasource and executes a query
	 * 
	 * @param nations
	 * @throws Exception
	 */
	public void insertNations(Nation... nations) throws Exception {
		for (Nation nation : nations) {
			insertNation(nation);
		}
	}

	/**
	 * Adds multiple TownBlocks to the Datasource and executes a query
	 * 
	 * @param townBlocks
	 * @throws Exception
	 */
	public void insertTownBlocks(TownBlock... townBlocks) throws Exception {
		for (TownBlock block : townBlocks) {
			insertTownBlock(block);
		}
	}

	/**
	 * Adds multiple Ranks to the Datasource and executes a query
	 * 
	 * @param town
	 * @param ranks
	 * @throws Exception
	 */
	public void insertRanks(Rank... ranks) throws Exception {
		for (Rank r : ranks)
			insertRank(r);
	}

	/**
	 * Adds multiple TownPlots to the Datasource and executes a query
	 * 
	 * @param plots
	 * @throws Exception
	 */
	public void insertPlots(ITownPlot... plots) throws Exception {
		for(ITownPlot plot : plots){
			insertPlot(plot);
		}
	}
	
	// /////////////////////////////////////////////////////////////
	// Remove Single Entity - Internal Only
	// /////////////////////////////////////////////////////////////

	/**
	 * Removes a Town from the Datasource
	 * 
	 * @param town
	 */
	protected boolean removeTown(Town town) {
		for(Nation n : town.getNations())
			n.removeTown(town);
		for(Resident r : town.getResidents())
			r.removeResidentFromTown(town);
		for(TownBlock tb : blocks.values())
			if(tb.getTown() == town)
				blocks.remove(tb.getKey());
		return towns.remove(town.getName()) != null;
	}

	/**
	 * Removes a Resident from the Datasource
	 * 
	 * @param resident
	 */
	protected boolean removeResident(Resident resident) {
		for(Town t : resident.getTowns())
			t.removeResident(resident);
		return residents.remove(resident.getUUID()) != null;
	}

	/**
	 * Removes a Nation from the Datasource
	 * 
	 * @param nation
	 */
	protected boolean removeNation(Nation nation) {
		for(Town t : nation.getTowns())
			t.removeNation(nation);
		return nations.remove(nation.getName()) != null;
	}

	/**
	 * Removes a TownBlock from the Datasource
	 * 
	 * @param townBlock
	 */
	protected boolean removeTownBlock(TownBlock townBlock) {
		townBlock.getTown().removeTownBlock(townBlock);
		return blocks.remove(townBlock.getKey()) != null;
	}
	
	/**
	 * Removes a Rank from the Datasource
	 * 
	 * @param rank
	 * @return
	 */
	protected boolean removeRank(Rank rank) {
		for (Resident res : rank.getTown().getResidents())
			if (res.getTownRank(rank.getTown()) == rank) res.setTownRank(rank.getTown(), rank.getTown().getRank("Resident"));
		return ranks.remove(rank.getTown().getName() + ";" + rank.getName(), rank);
	}
	
	/**
	 * Removes a TownPlot from the Datasource
	 * 
	 * @param plot
	 * @return
	 */
	protected boolean removePlot(ITownPlot plot) {
		return plots.remove(plot.getKey(), plot);
	}
	
	// /////////////////////////////////////////////////////////////
	// Remove Multiple Entities
	// /////////////////////////////////////////////////////////////

	/**
	 * Removes the Towns from the Datasource
	 * 
	 * @param towns
	 */
	protected void removeTowns(Town... towns) {
		for (Town town : towns) {
			removeTown(town);
		}
	}

	/**
	 * Removes the Residents from the Datasource
	 * 
	 * @param residents
	 */
	protected void removeResidents(Resident... residents) {
		for (Resident res : residents) {
			removeResident(res);
		}
	}

	/**
	 * Removes the Nations from the Datasource
	 * 
	 * @param nations
	 */
	protected void removeNations(Nation... nations) {
		for (Nation nation : nations) {
			removeNation(nation);
		}
	}

	/**
	 * Removes the TownBlocks from the Datasource
	 * 
	 * @param blocks
	 */
	protected void removeTownBlocks(TownBlock... blocks) {
		for (TownBlock block : blocks) {
			removeTownBlock(block);
		}
	}
	
	/**
	 * Removes the Ranks from the Datasource
	 * 
	 * @param ranks
	 */
	protected void removeRanks(Rank... ranks) {
		for(Rank r : ranks) {
			removeRank(r);
		}
	}
	
	/**
	 * Removes the TownPlots from the Datasource
	 * 
	 * @param plots
	 */
	protected void removePlots(ITownPlot... plots) {
		for(ITownPlot plot : plots) {
			removePlot(plot);
		}
	}
	
	

	// /////////////////////////////////////////////////////////////
	// Update Single Entity
	// /////////////////////////////////////////////////////////////

	/**
	 * Updates the Town
	 * 
	 * @param town
	 * @throws Exception
	 */
	public abstract void updateTown(Town town) throws Exception;

	/**
	 * Updates the Resident
	 * 
	 * @param resident
	 * @throws Exception
	 */
	public abstract void updateResident(Resident resident) throws Exception;

	/**
	 * Updates the Nation
	 * 
	 * @param nation
	 * @throws Exception
	 */
	public abstract void updateNation(Nation nation) throws Exception;

	/**
	 * Updates the TownBlock
	 * 
	 * @param block
	 * @throws Exception
	 */
	public abstract void updateTownBlock(TownBlock block) throws Exception;

	/**
	 * Updates the Rank
	 * 
	 * @param town
	 * @param rank
	 * @throws Exception
	 */
	public abstract void updateRank(Rank rank) throws Exception;

	/**
	 * Updates the plot
	 * 
	 * @param plot
	 * @throws Exception
	 */
	public abstract void updatePlot(ITownPlot plot) throws Exception;
	
	// /////////////////////////////////////////////////////////////
	// Update Multiple Entities
	// /////////////////////////////////////////////////////////////

	/**
	 * Updates all the given Towns
	 * 
	 * @param towns
	 * @throws Exception
	 */
	public void updateTowns(Town... towns) throws Exception {
		for (Town town : towns) {
			updateTown(town);
		}
	}

	/**
	 * Updates all the given Residents
	 * 
	 * @param residents
	 * @throws Exception
	 */
	public void updateResidents(Resident... residents) throws Exception {
		for (Resident res : residents) {
			updateResident(res);
		}
	}

	/**
	 * Updates all the given Nations
	 * 
	 * @param nations
	 * @throws Exception
	 */
	public void updateNations(Nation... nations) throws Exception {
		for (Nation nation : nations) {
			updateNation(nation);
		}
	}

	/**
	 * Updates all the given TownBlocks
	 * 
	 * @param blocks
	 * @throws Exception
	 */
	public void updateTownBlocks(TownBlock... blocks) throws Exception {
		for (TownBlock block : blocks) {
			updateTownBlock(block);
		}
	}

	/**
	 * Updates all the given Ranks
	 * 
	 * @param town
	 * @param ranks
	 * @throws Exception
	 */
	public void updateRanks(Rank... ranks) throws Exception {
		for (Rank r : ranks)
			updateRank(r);
	}

	/**
	 * Updates all the given Plots
	 * 
	 * @param plots
	 * @throws Exception
	 */
	public void updatePlots(ITownPlot... plots) throws Exception {
		for(ITownPlot plot : plots) {
			updatePlot(plot);
		}
	}
	
	// /////////////////////////////////////////////////////////////
	// Delete Single Entities
	// /////////////////////////////////////////////////////////////

	/**
	 * Deletes the town from Datasource and executes a query
	 * 
	 * @param town
	 * @return
	 */
	public abstract boolean deleteTown(Town town) throws Exception;

	/**
	 * Deletes the nation from Datasource and executes a query
	 * 
	 * @param town
	 * @return
	 */
	public abstract boolean deleteNation(Nation nation) throws Exception;

	/**
	 * Deletes the townblock from Datasource and executes a query
	 * 
	 * @param town
	 * @return
	 */
	public abstract boolean deleteTownBlock(TownBlock townBlock) throws Exception;

	/**
	 * Deletes the resident from Datasource and executes a query
	 * 
	 * @param town
	 * @return
	 */
	public abstract boolean deleteResident(Resident resident) throws Exception;

	/**
	 * Deletes a rank from Datasource and executes a query
	 * 
	 * @param rank
	 * @return
	 * @throws Exception
	 */
	public abstract boolean deleteRank(Rank rank) throws Exception;

	/**
	 * Deletes a plot from Datasource and executes a query
	 * 
	 * @param plot
	 * @return
	 * @throws Exception
	 */
	public abstract boolean deletePlot(ITownPlot plot) throws Exception;
	
	// /////////////////////////////////////////////////////////////
	// Delete Multiple Entities
	// /////////////////////////////////////////////////////////////

	/**
	 * Deletes the towns from Datasource and executes a query
	 * 
	 * @param town
	 * @return
	 */
	public void deleteTowns(Town... towns) throws Exception {
		for (Town t : towns)
			deleteTown(t);
	}

	/**
	 * Deletes the nations from Datasource and executes a query
	 * 
	 * @param town
	 * @return
	 */
	public void deleteNations(Nation... nations) throws Exception {
		for (Nation n : nations)
			deleteNation(n);
	}

	/**
	 * Deletes the townblocks from Datasource and executes a query
	 * 
	 * @param townBlocks
	 * @return
	 */
	public void deleteTownBlocks(TownBlock... townBlocks) throws Exception {
		for (TownBlock b : townBlocks)
			deleteTownBlock(b);
	}

	/**
	 * Deletes the residents from Datasource and executes a query
	 * 
	 * @param town
	 * @return
	 */
	public void deleteResidents(Resident... residents) throws Exception {
		for (Resident r : residents)
			deleteResident(r);
	}

	/**
	 * Deletes the ranks from Datasource and executes a query
	 * 
	 * @param town
	 * @param ranks
	 * @throws Exception
	 */
	public void deleteRanks(Rank... ranks) throws Exception {
		for (Rank r : ranks)
			deleteRank(r);
	}
	
	/**
	 * Deletes the plots from Datasource and executes a query
	 * 
	 * @param plots
	 * @throws Exception
	 */
	public void deletePlots(ITownPlot... plots) throws Exception {
		for(ITownPlot plot : plots) {
			deletePlot(plot);
		}
	}

	// /////////////////////////////////////////////////////////////
	// Linkages
	// /////////////////////////////////////////////////////////////

	/**
	 * Loads all stored links between Residents and Towns
	 * 
	 * @throws Exception
	 */
	public abstract void loadResidentToTownLinks() throws Exception;

	/**
	 * Loads all stored links between Towns and Nations
	 * 
	 * @throws Exception
	 */
	public abstract void loadTownToNationLinks() throws Exception;

	/**
	 * Links the Resident with the given Rank to the Town
	 * 
	 * @param resident
	 * @param town
	 * @param rank
	 */
	public abstract void linkResidentToTown(Resident resident, Town town, Rank rank) throws Exception;

	/**
	 * Links the Resident to the Town with a Rank of Resident
	 * 
	 * @param resident
	 * @param town
	 */
	public void linkResidentToTown(Resident resident, Town town) throws Exception {
		Rank rank = new Rank("Resident", Constants.DEFAULT_RANK_VALUES.get("Resident"), town);
		linkResidentToTown(resident, town, rank);
	}

	/**
	 * Links the Town to the Nation
	 * 
	 * @param town
	 * @param nation
	 */
	public abstract void linkTownToNation(Town town, Nation nation, Nation.Rank rank) throws Exception;

	/**
	 * Links the Town to the Nation with the Rank of Town
	 * 
	 * @param town
	 * @param nation
	 * @throws Exception
	 */
	public void linkTownToNation(Town town, Nation nation) throws Exception {
		linkTownToNation(town, nation, Nation.Rank.Town);
	}

	/**
	 * Removes link of a Resident to the specified Town
	 * 
	 * @param resident
	 * @param town
	 * @throws Exception
	 */
	public abstract void unlinkResidentFromTown(Resident resident, Town town) throws Exception;

	/**
	 * Removes link of a Town to the specified Nation
	 * 
	 * @param town
	 * @param nation
	 * @throws Exception
	 */
	public abstract void unlinkTownFromNation(Town town, Nation nation) throws Exception;

	// /////////////////////////////////////////////////////////////
	// Extras
	// /////////////////////////////////////////////////////////////

	public abstract void dump() throws Exception;

	// /////////////////////////////////////////////////////////////
	// Unknown Group/Helpers? TODO Change/Move Later?
	// /////////////////////////////////////////////////////////////

	/**
	 * Gets or makes a new Resident from the playerName
	 * 
	 * @param playerName
	 * @return
	 * @throws Exception
	 */
	public Resident getOrMakeResident(String playerName) throws Exception {
		Resident res = residents.get(playerName);
		if (res == null) {
			res = new Resident(playerName);
			insertResident(res);
		}
		return res;
	}

	/**
	 * Gets or makes a new Resident from the EntityPlayer
	 * 
	 * @param player
	 * @return
	 * @throws Exception
	 */
	public Resident getOrMakeResident(EntityPlayer player) throws Exception {
		return getOrMakeResident(player.getCommandSenderName());
	}

	// ***** Everything below here is for internal use only! *****

	// /////////////////////////////////////////////////////////////
	// Add Single Entity - Internal Only
	// /////////////////////////////////////////////////////////////

	/**
	 * Adds a Town to the Datasource
	 * 
	 * @param town
	 */
	protected void addTown(Town town) throws Exception {
		log.info("Adding town %s", town.getName()); // TODO Remove later
		towns.put(town.getName(), town);
	}

	/**
	 * Adds a Resident to the Datasource
	 * 
	 * @param resident
	 */
	protected void addResident(Resident resident) throws Exception {
		residents.put(resident.getUUID(), resident);
	}

	/**
	 * Adds a Nation to the Datasource
	 * 
	 * @param nation
	 */
	protected void addNation(Nation nation) throws Exception {
		nations.put(nation.getName(), nation);
	}

	/**
	 * Adds a TownBlock to the Datasource
	 * 
	 * @param townBlock
	 */
	protected void addTownBlock(TownBlock townBlock) throws Exception {
		blocks.put(townBlock.getKey(), townBlock);
	}

	/**
	 * 
	 * Adds a Rank to the Datasource
	 * 
	 * @param rank
	 * @throws Exception
	 */
	protected void addRank(Rank rank) throws Exception {
		ranks.put(rank.getKey(), rank);
	}

	/**
	 * Adds a Plot to the Datasource
	 * 
	 * @param plot
	 * @throws Exception
	 */
	protected void addPlot(ITownPlot plot) throws Exception {
		plots.put(plot.getKey(), plot);
	}
	
	// /////////////////////////////////////////////////////////////
	// Add Multiple Entities - Internal Only
	// /////////////////////////////////////////////////////////////

	/**
	 * Adds multiple Towns to the towns Map
	 * 
	 * @param towns
	 * @throws Exception
	 */
	protected void addTowns(Town... towns) throws Exception {
		for (Town town : towns) {
			addTown(town);
		}
	}

	/**
	 * Adds multiple Residents to the residents Map
	 * 
	 * @param residents
	 * @throws Exception
	 */
	protected void addResidents(Resident... residents) throws Exception {
		for (Resident res : residents) {
			addResident(res);
		}
	}

	/**
	 * Adds multiple Nations to the nations Map
	 * 
	 * @param nations
	 * @throws Exception
	 */
	protected void addNations(Nation... nations) throws Exception {
		for (Nation nation : nations) {
			addNation(nation);
		}
	}

	/**
	 * Adds multiple TownBlocks to the blocks Map
	 * 
	 * @param townBlocks
	 * @throws Exception
	 */
	protected void addTownBlocks(TownBlock... townBlocks) throws Exception {
		for (TownBlock block : townBlocks) {
			addTownBlocks(block);
		}
	}

	/**
	 * Adds multiple Ranks to the ranks Map
	 * 
	 * @param ranks
	 * @throws Exception
	 */
	protected void addRanks(Rank... ranks) throws Exception {
		for (Rank r : ranks)
			addRank(r);
	}
	
	/**
	 * Add multiple Plots to the plots Map
	 * 
	 * @param plots
	 * @throws Exception
	 */
	protected void addPlots(ITownPlot... plots) throws Exception {
		for (ITownPlot plot : plots) {
			addPlot(plot);
		}
	}

}