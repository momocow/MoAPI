package me.momocow.mobasic.server.management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import me.momocow.mobasic.reference.Reference;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoUserList<K, V extends MoUserListEntry<K>>
{
	protected static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    protected final Gson gson;
    private final File saveFile;
    private final Map<String, V> values = Maps.<String, V>newHashMap();
    private boolean lanServer = true;
    private static final ParameterizedType USER_LIST_ENTRY_TYPE = new ParameterizedType()
    {
        public Type[] getActualTypeArguments()
        {
            return new Type[] {MoUserListEntry.class};
        }
        public Type getRawType()
        {
            return List.class;
        }
        public Type getOwnerType()
        {
            return null;
        }
    };

    @SuppressWarnings("rawtypes")
	public MoUserList(File saveFile)
    {
        this.saveFile = saveFile;
        GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
        gsonbuilder.registerTypeHierarchyAdapter(MoUserListEntry.class, new MoUserList.Serializer());
        this.gson = gsonbuilder.create();
    }

    public boolean isLanServer()
    {
        return this.lanServer;
    }

    public void setLanServer(boolean state)
    {
        this.lanServer = state;
    }

    /**
     * Adds an entry to the list
     */
    public void addEntry(V entry)
    {
        this.values.put(this.getObjectKey(entry.getValue()), entry);

        try
        {
            this.writeChanges();
        }
        catch (IOException ioexception)
        {
            LOGGER.warn((String)"Could not save the list after adding a user.", (Throwable)ioexception);
        }
    }

    @SuppressWarnings("unchecked")
	public V getEntry(K obj)
    {
        return (V)((MoUserListEntry<K>)this.values.get(this.getObjectKey(obj)));
    }

    public void removeEntry(K entry)
    {
        this.values.remove(this.getObjectKey(entry));

        try
        {
            this.writeChanges();
        }
        catch (IOException ioexception)
        {
            LOGGER.warn((String)"Could not save the list after removing a user.", (Throwable)ioexception);
        }
    }

    @SideOnly(Side.SERVER)
    public File getSaveFile()
    {
        return this.saveFile;
    }

    public String[] getKeys()
    {
        return (String[])this.values.keySet().toArray(new String[this.values.size()]);
    }

    /**
     * Gets the key value for the given object
     */
    protected String getObjectKey(K obj)
    {
        return obj.toString();
    }

    protected boolean hasEntry(K entry)
    {
        return this.values.containsKey(this.getObjectKey(entry));
    }

    protected MoUserListEntry<K> createEntry(JsonObject entryData)
    {
        return new MoUserListEntry<K>((K)null, entryData);
    }

    protected Map<String, V> getValues()
    {
        return this.values;
    }

    public void writeChanges() throws IOException
    {
        Collection<V> collection = this.values.values();
        String s = this.gson.toJson((Object)collection);
        BufferedWriter bufferedwriter = null;

        try
        {
            bufferedwriter = Files.newWriter(this.saveFile, Charsets.UTF_8);
            bufferedwriter.write(s);
        }
        finally
        {
            IOUtils.closeQuietly((Writer)bufferedwriter);
        }
    }

    @SideOnly(Side.SERVER)
    public boolean isEmpty()
    {
        return this.values.size() < 1;
    }

    @SuppressWarnings("unchecked")
	@SideOnly(Side.SERVER)
    public void readSavedFile() throws IOException, FileNotFoundException
    {
        Collection<MoUserListEntry<K>> collection = null;
        BufferedReader bufferedreader = null;

        try
        {
            bufferedreader = Files.newReader(this.saveFile, Charsets.UTF_8);
            collection = (Collection<MoUserListEntry<K>>)this.gson.fromJson((Reader)bufferedreader, USER_LIST_ENTRY_TYPE);
        }
        finally
        {
            IOUtils.closeQuietly((Reader)bufferedreader);
        }

        if (collection != null)
        {
            this.values.clear();

            for (MoUserListEntry<K> MoUserListEntry : collection)
            {
                if (MoUserListEntry.getValue() != null)
                {
                    this.values.put(this.getObjectKey(MoUserListEntry.getValue()), (V)MoUserListEntry);
                }
            }
        }
    }

    class Serializer implements JsonDeserializer<MoUserListEntry<K>>, JsonSerializer<MoUserListEntry<K>>
    {
        private Serializer()
        {
        }

        public JsonElement serialize(MoUserListEntry<K> p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            JsonObject jsonobject = new JsonObject();
            p_serialize_1_.onSerialization(jsonobject);
            return jsonobject;
        }

        public MoUserListEntry<K> deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            if (p_deserialize_1_.isJsonObject())
            {
                JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
                return MoUserList.this.createEntry(jsonobject);
            }
            else
            {
                return null;
            }
        }
    }
}
