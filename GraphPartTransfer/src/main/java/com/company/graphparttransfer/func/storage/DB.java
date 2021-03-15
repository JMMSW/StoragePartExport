
package com.company.graphparttransfer.func.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.company.graphparttransfer.model.DataRoot;

import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;


public class DB
{
	private static DataRoot               root1 = new DataRoot();
	private static EmbeddedStorageManager storageManager1;
	
	private static DataRoot               root2 = new DataRoot();
	private static EmbeddedStorageManager storageManager2;
	
	public static void startStorages()
	{
		final Path resolve = Paths.get("data").resolve("storage1");
		System.out.println(resolve.toAbsolutePath());
		DB.storageManager1 = EmbeddedStorage.start(
			DB.root1,
			Paths.get("data").resolve("storage1"));
		
		DB.storageManager2 = EmbeddedStorage.start(
			DB.root2,
			Paths.get("data").resolve("storage2"));
	}
	
	public static EmbeddedStorageManager instance1()
	{
		return DB.storageManager1;
	}

	public static DataRoot root1()
	{
		return DB.root1;
	}
	
	public static EmbeddedStorageManager instance2()
	{
		return DB.storageManager2;
	}

	public static DataRoot root2()
	{
		return DB.root2;
	}
}
