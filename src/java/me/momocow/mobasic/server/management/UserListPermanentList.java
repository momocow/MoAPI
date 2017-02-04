package me.momocow.mobasic.server.management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UserListPermanentList extends MoUserList<GameProfile, UserListPermenentListEntry>
{
	public UserListPermanentList(File saveFile) 
	{
		super(saveFile);
	}
	
	protected MoUserListEntry<GameProfile> createEntry(JsonObject entryData)
    {
        return new UserListPermenentListEntry(entryData);
    }

    public List<String> getNames()
    {
        List<String> astring = new ArrayList<String>();

        for (UserListPermenentListEntry userListPermenentPlayerListEntry : this.getValues().values())
        {
            astring.add(((GameProfile)userListPermenentPlayerListEntry.getValue()).getName());
        }

        return astring;
    }

    /**
     * Returns true if the profile is in the whitelist.
     */
    @SideOnly(Side.SERVER)
    public boolean isFirstVisit(GameProfile profile)
    {
        return !this.hasEntry(profile);
    }

    /**
     * Gets the key value for the given object
     */
    protected String getObjectKey(GameProfile obj)
    {
        return obj.getId().toString();
    }

    /**
     * Get a GameProfile entry by its name
     */
    public GameProfile getByName(String profileName)
    {
        for (UserListPermenentListEntry userListPermenentPlayerListEntry : this.getValues().values())
        {
            if (profileName.equalsIgnoreCase(((GameProfile)userListPermenentPlayerListEntry.getValue()).getName()))
            {
                return (GameProfile)userListPermenentPlayerListEntry.getValue();
            }
        }

        return null;
    }
    
    /**
     * Get a GameProfile entry by its UUID
     */
    public GameProfile getByUUID(UUID profileID)
    {
        for (UserListPermenentListEntry userListPermenentPlayerListEntry : this.getValues().values())
        {
            if (profileID.equals(((GameProfile)userListPermenentPlayerListEntry.getValue()).getId()))
            {
                return (GameProfile)userListPermenentPlayerListEntry.getValue();
            }
        }

        return null;
    }
}
