package me.momocow.mobasic.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBans;
import net.minecraft.server.management.UserListIPBans;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Server 
{
	public static MinecraftServer getServer()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}
	
	public static WorldServer[] getWorlds()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;
	}
	
	public static WorldServer getWorld(int worldId)
	{
		WorldServer[] worlds = getWorlds();
				
		for (WorldServer w : worlds)
		{
			if (w.provider.getDimension() == worldId)
			{
				return w;
			}
		}
		
		return null;
	}
	
	public void broadcast(ITextComponent text)
	{
		getServer().getPlayerList().sendChatMsg(text);
	}
	
	public static EntityPlayerMP getOnlinePlayer(UUID userID)
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(userID);
	}
	
	public static EntityPlayerMP getOnlinePlayer(String username)
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(username);
	}

	@Nullable
	public static String getPlayerName(UUID uid)
	{
		return UsernameCache.getLastKnownUsername(uid);
	}
	
	@Nullable
	public static UUID getPlayerId(String name)
	{
		for(Entry<UUID, String> user: UsernameCache.getMap().entrySet())
		{
			if(((String)user.getValue()).equals(name))
			{
				return (UUID) user.getKey();
			}
		}
		return null;
	}
	
	public static UserListIPBans getBannedIPs()
	{
		return getServer().getPlayerList().getBannedIPs();
	}
	
	public static UserListBans getBannedPlayers()
	{
		return getServer().getPlayerList().getBannedPlayers();
	}
	
	public static GameProfile getProfile(UUID player)
	{
		return getServer().getPlayerProfileCache().getProfileByUUID(player);
	}
	
	public static GameProfile getProfile(String player)
	{
		return getServer().getPlayerProfileCache().getGameProfileForUsername(player);
	}
	
	public static List<GameProfile> getProfile()
	{
		List<GameProfile> players = new ArrayList<GameProfile>();
		for(String username: getServer().getPlayerProfileCache().getUsernames())
		{
			GameProfile profile = getProfile(username);
			if(profile != null)
			{
				players.add(profile);
			}
		}
		
		return players;
	}
}
