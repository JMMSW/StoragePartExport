
package com.company.graphparttransfer.func.transfer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.tuple.Pair;
import org.zeroturnaround.zip.ZipUtil;

import com.company.graphparttransfer.model.Client;
import com.company.graphparttransfer.model.transfer.TransferRoot;

import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;


public class Export
{
	public static Pair<String, byte[]> export(final Client clientToExport) throws IOException
	{
		final String systmpdir    = System.getProperty("java.io.tmpdir");
		final Path   exporttmpdir = Paths.get(systmpdir, "export");
		
		if(Files.notExists(exporttmpdir))
		{
			Files.createDirectory(exporttmpdir);
		}

		final String exportfilename = new StringBuilder("export")
			.append("_")
			.append(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss")))
			.append(".zip")
			.toString();
		
		final TransferRoot transferRoot = TransferRoot.New(clientToExport);
		
		final byte[] transferStorageZip = Export.exportClientAsZipFile(transferRoot, exporttmpdir, exportfilename);
		
		return Pair.of(exportfilename, transferStorageZip);
	}
	
	public static byte[]
		exportClientAsZipFile(final TransferRoot root, final Path exporttmpdir, final String exportfilename)
			throws IOException
	{
		final Path exportPath = exporttmpdir.resolve(exportfilename);
		
		Export.createNewInstance(exportPath, root);

		// to Zip
		ZipUtil.unexplode(exportPath.toFile());
		
		// read zip file
		final byte[] fileContent = Files.readAllBytes(exportPath);

		// cleanup
		exportPath.toFile().delete();

		return fileContent;
	}

	public static void createNewInstance(final Path storageDir, final TransferRoot rootWithDataToExport)
	{
		// Start the database manager
		final EmbeddedStorageManager storageManager = EmbeddedStorage.start(storageDir);

		// Set the entity (graph) as root
		storageManager.setRoot(rootWithDataToExport);

		// Store root
		storageManager.storeRoot();
		
		// Stop the database
		storageManager.shutdown();

	}
}
