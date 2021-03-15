
package com.company.graphparttransfer.func.transfer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.zeroturnaround.zip.ZipUtil;

import com.company.graphparttransfer.func.storage.DB;
import com.company.graphparttransfer.model.Client;
import com.company.graphparttransfer.model.DataRoot;
import com.company.graphparttransfer.model.common.User;
import com.company.graphparttransfer.model.transfer.TransferRoot;

import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;


public class Import
{

	public static Optional<String> importClient(final Pair<String, byte[]> uploadData) throws IOException
	{
		final String systmpdir    = System.getProperty("java.io.tmpdir");
		final Path   importtmpdir = Paths.get(systmpdir, "import");

		if(Files.notExists(importtmpdir))
		{
			Files.createDirectory(importtmpdir);
		}

		//// write upload to file
		final String importtempfilename = "import_" + Instant.now().toEpochMilli();
		final Path   importtempfilepath = importtmpdir.resolve(importtempfilename);

		Files.write(importtempfilepath, uploadData.getRight());

		if(Files.exists(importtempfilepath))
		{

			//// read
			final TransferRoot tempRoot = Import.read(importtempfilepath);

			// cleanup files
			try
			{
				if(Files.isDirectory(importtempfilepath))
				{
					FileUtils.deleteDirectory(importtempfilepath.toFile());
				}
				else
				{
					Files.delete(importtempfilepath);
				}
			}
			catch(final IOException e1)
			{
				// Cannot delete file or directory
				e1.printStackTrace();
			}

			//// import client
			if(tempRoot != null && !tempRoot.getClients().isEmpty())
			{

				Import.finalizeClientImport(tempRoot);
				return Optional.empty();
			}
			else
			{
				return Optional.of("Reading file failed!");
			}
		}
		else
		{
			return Optional.of("Opening file failed!");
		}
	}

	/**
	 * Update imported client if necessary.
	 * And add imported client to target storage.
	 *
	 * @param tempRoot
	 */
	private static void finalizeClientImport(final TransferRoot tempRoot)
	{
		final Client   clienttoimport = tempRoot.getClients().iterator().next();
		final DataRoot targetRoot     = DB.root2();

		//// If there are objects that are "Storage scoped":
		// they must be replaced in or added to the dataset-to-import

		final Set<User> userSetOfTargetStorage = targetRoot.getUser();

		for(final User user : tempRoot.getUser())
		{
			// Search in target storage for a user by email

			final Optional<User> userIsInTargetStorage =
				userSetOfTargetStorage.stream()
					.filter(u -> Objects.equals(u.getEmail(), user.getEmail()))
					.findFirst();

			if(userIsInTargetStorage.isPresent())
			{
				clienttoimport.getUser().remove(user);
				clienttoimport.getUser().add(userIsInTargetStorage.get());
			}
			else
			{
				userSetOfTargetStorage.add(user);
			}
		}

		DB.instance2().store(userSetOfTargetStorage);

		//// Add imported client to target storage:
		
		final Set<Client> clients = targetRoot.getClients();
		clients.add(clienttoimport);
		DB.instance2().store(clients);

	}

	public static TransferRoot read(final Path zipFilePath)
	{

		//// Unpack / Replace zip by folder
		ZipUtil.explode(zipFilePath.toFile());

		//// Import "TransferRoot"
		final TransferRoot clientRoot = Import.importTransferRootFromNewInstance(zipFilePath);

		return clientRoot;

	}

	public static TransferRoot importTransferRootFromNewInstance(final Path storageDir)
	{

		final EmbeddedStorageManager storageManager = EmbeddedStorage.start(storageDir);

		final TransferRoot root = (TransferRoot)storageManager.root();

		storageManager.shutdown();

		return root;
	}

}
