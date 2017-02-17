package me.momocow.mobasic.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class represents a file with the specifiend path name to store arbitrary Object with the help of Gson.
 * @author MomoCow
 */
public class StorageFile<T extends Serializable>
{
	private final Logger logger;
	private final File storageFile;
	private final String encoding;
	private T content = null;
	
	public StorageFile(T cnt, File storageFile) throws Exception
	{
		this(cnt, storageFile, LogManager.getLogger());
	}
	
	public StorageFile(T cnt, File storageFile, Logger logger) throws Exception
	{
		this(cnt, storageFile, logger, "UTF-8");
	}
	
	public StorageFile(T cnt, File storageFile, Logger logger, String encoding) throws Exception
	{
		this.content = cnt;
		this.storageFile = storageFile;
		this.logger = logger;
		this.encoding = encoding;
		
		try
		{
			FileUtils.forceMkdir(this.storageFile.getParentFile());
		}
		catch(Exception e)
		{
			logger.warn("Unable to create the storage directory. " + this.storageFile.getParentFile().getAbsolutePath(), e);
			throw e;
		}
	}
	
	public String getEncoding()
	{
		return this.encoding;
	}
	
	@Override
	public String toString() {
		return this.storageFile.getAbsolutePath();
	}
	
	@SuppressWarnings("unchecked")
	public T load() throws Exception
	{
		if(!this.storageFile.exists())
		{
			FileUtils.forceMkdir(this.storageFile.getParentFile());
			this.storageFile.createNewFile();
			logger.info("The storage is created. " + this.storageFile.getAbsolutePath());
		}
		else
		{
			ObjectInputStream ois  = null;
			T object = null;
			try
			{
				ois = new ObjectInputStream(new FileInputStream(this.storageFile));
				object = (T) ois.readObject();
			}
			catch(EOFException e){}
			finally
			{
				if(ois != null) ois.close();
			}

			if(object != null)
			{
				logger.info("The storage is read. " + this.storageFile.getAbsolutePath());
				this.content = object;
			}
		}
		
		return this.content;
	}
	
	public void save(T newContent) throws Exception
	{	
		this.content = newContent;
	
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.storageFile));
			oos.writeObject(this.content);
			oos.flush();
			oos.close();
		}
		catch(Exception e)
		{
			logger.warn("Unable to save to the file.", e);
			throw e;
		}
	}
}
