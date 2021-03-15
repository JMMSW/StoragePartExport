
package com.company.graphparttransfer.model;

import java.util.HashSet;
import java.util.Set;

import com.company.graphparttransfer.model.common.User;


public class DataRoot
{
	private Set<User>   user    = new HashSet<>();
	private Set<Client> clients = new HashSet<>();
	
	public Set<User> getUser()
	{
		return this.user;
	}
	
	public void setUser(final Set<User> user)
	{
		this.user = user;
	}
	
	public Set<Client> getClients()
	{
		return this.clients;
	}
	
	public void setClients(final Set<Client> clients)
	{
		this.clients = clients;
	}
	
}
