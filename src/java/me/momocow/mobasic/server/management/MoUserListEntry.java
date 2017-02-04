package me.momocow.mobasic.server.management;

import com.google.gson.JsonObject;

public class MoUserListEntry<T>
{
	private final T value;

    public MoUserListEntry(T valueIn)
    {
        this.value = valueIn;
    }

    protected MoUserListEntry(T valueIn, JsonObject json)
    {
        this.value = valueIn;
    }

    public T getValue()
    {
        return this.value;
    }

    protected void onSerialization(JsonObject data)
    {
    }
}
