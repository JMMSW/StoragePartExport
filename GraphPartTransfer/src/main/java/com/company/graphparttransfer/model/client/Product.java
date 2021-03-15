
package com.company.graphparttransfer.model.client;

public class Product
{
	private String   name;
	private String   description;
	private Category category;
	
	public Product()
	{
		super();
	}

	public Product(final String name, final Category category)
	{
		super();
		this.name     = name;
		this.category = category;
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

	public Category getCategory()
	{
		return this.category;
	}

	public void setCategory(final Category category)
	{
		this.category = category;
	}
}
