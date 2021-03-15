
package com.company.graphparttransfer.model.client;

public class Category
{
	private String name;
	private String description;
	
	public Category()
	{
		super();
	}
	
	public Category(final String name)
	{
		super();
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(final String description)
	{
		this.description = description;
	}
}
