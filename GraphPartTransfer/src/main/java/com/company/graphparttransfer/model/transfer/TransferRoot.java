
package com.company.graphparttransfer.model.transfer;

import java.time.Instant;

import com.company.graphparttransfer.model.Client;
import com.company.graphparttransfer.model.DataRoot;


public class TransferRoot extends DataRoot
{
	private final Instant created = Instant.now();
	
	public static TransferRoot New(final Client client)
	{
		final TransferRoot transferRoot = new TransferRoot();
		
		transferRoot.getClients().add(client);
		transferRoot.getUser().addAll(client.getUser());

		return transferRoot;
	}
	
	public Instant getCreated()
	{
		return this.created;
	}
}
