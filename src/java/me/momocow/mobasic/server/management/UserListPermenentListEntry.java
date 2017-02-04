package me.momocow.mobasic.server.management;

import java.util.UUID;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListPermenentListEntry extends MoUserListEntry<GameProfile>
{
	public UserListPermenentListEntry(GameProfile profile)
    {
        super(profile);
    }

    public UserListPermenentListEntry(JsonObject json)
    {
        super(gameProfileFromJsonObject(json), json);
    }
    
    protected void onSerialization(JsonObject data) {
    	if(this.getValue() != null)
    	{
    		data.addProperty("uuid", ((GameProfile)this.getValue()).getId() == null ? "" : ((GameProfile)this.getValue()).getId().toString());
            data.addProperty("name", ((GameProfile)this.getValue()).getName());
    	}
    }
    
    public static GameProfile gameProfileFromJsonObject(JsonObject json)
    {
    	if (json.has("uuid") && json.has("name"))
        {
            String s = json.get("uuid").getAsString();
            UUID uuid;

            try
            {
                uuid = UUID.fromString(s);
            }
            catch (Throwable var4)
            {
                return null;
            }

            return new GameProfile(uuid, json.get("name").getAsString());
        }
        else
        {
            return null;
        }
    }
}
