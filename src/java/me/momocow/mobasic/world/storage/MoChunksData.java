package me.momocow.mobasic.world.storage;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;

public abstract class MoChunksData extends WorldSavedData
{
	//Data fields
	protected World world;
	protected boolean isInit = false;
	protected Map<Long, NBTTagCompound> data = new HashMap<Long, NBTTagCompound>();
	
	protected MoChunksData(String dataId, World w) {
		this(dataId);
		
		this.world = w;
	}
	
	protected MoChunksData(String dataId) {
		super(dataId);
	}
	
	public void init(World w)
	{
		this.world = w;
		
		this.isInit = true;
	}
	
	public boolean isInit()
	{
		return this.isInit;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.data = new HashMap<Long, NBTTagCompound>();
		
		NBTTagList chunkList = nbt.getTagList("chunkList", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < chunkList.tagCount(); i++)
		{
			NBTTagCompound chunk = chunkList.getCompoundTagAt(i);
			this.data.put(chunk.getLong("chunkId"), chunk.getCompoundTag("chunkData"));
		}
	}
	
	/**
	 * NBTTagCompound
	 * {
	 * 	   "world": int,
	 *     "chunkList": NBTTagList
	 *     [
	 *         NBTTagCompound
	 *         {
	 *             "chunkId": long,
	 *             "chunkData": NBTTagCompound
	 *         },
	 *         ...
	 *     ]
	 * }
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("world", this.world.provider.getDimension());
		NBTTagList chunkList = new NBTTagList();
		for(long chunkId: data.keySet())
		{
			NBTTagCompound chunk = new NBTTagCompound();
			chunk.setLong("chunkId", chunkId);
			chunk.setTag("chunkData", this.data.get(chunkId));
			chunkList.appendTag(chunk);
		}
		nbt.setTag("chunkList", chunkList);
		return nbt;
	}
	
	public void setInteger(Chunk chunk, String dataKey, int dataValue)
	{
		this.setInteger(ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition), dataKey, dataValue);
	}
	
	public void setInteger(long chunkId, String dataKey, int dataValue)
	{
		this.touchChunk(chunkId);
		this.data.get(chunkId).setInteger(dataKey, dataValue);
		this.markDirty();
	}
	
	public Integer getInteger(Chunk chunk, String dataKey)
	{
		return this.getInteger(ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition), dataKey);
	}
	
	public Integer getInteger(long chunkId, String dataKey)
	{
		if(this.existChunk(chunkId))
		{
			return this.data.get(chunkId).getInteger(dataKey);
		}
		
		return null;
	}
	
	public boolean existChunk(Chunk chunk)
	{
		return this.existChunk(ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition));
	}
	
	public boolean existChunk(long chunkId)
	{
		return this.data.containsKey(chunkId);
	}
	
	protected void touchChunk(long chunkId)
	{
		if(!this.existChunk(chunkId))
		{
			this.data.put(chunkId, new NBTTagCompound());
		}
	}
	
	public static int getHeightFromHeightMap(int[] heightMap, BlockPos pos)
	{
		return heightMap[(pos.getZ() & 15) << 4 | (pos.getX() & 15)];
	}
}
