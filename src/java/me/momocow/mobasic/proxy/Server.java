package me.momocow.mobasic.proxy;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.WorldServer;
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

	public static GameProfile getPlayerProfile(String username)
	{
		return getServer().getPlayerProfileCache().getGameProfileForUsername(username);
	}
}
