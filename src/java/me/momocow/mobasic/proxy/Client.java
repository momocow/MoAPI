package me.momocow.mobasic.proxy;

import java.util.Map;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Client 
{
	public static ResourceLocation getPlayerSkin(GameProfile profile)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
        Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);

        if (map.containsKey(Type.SKIN))
        {
            return minecraft.getSkinManager().loadSkin((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN);
        }
        else
        {
            UUID uuid = EntityPlayer.getUUID(profile);
            return DefaultPlayerSkin.getDefaultSkin(uuid);
        }
	}
}
