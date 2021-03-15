
package com.company.graphparttransfer.model;

import java.util.HashSet;
import java.util.Set;

import com.company.graphparttransfer.model.client.Category;
import com.company.graphparttransfer.model.client.Product;
import com.company.graphparttransfer.model.common.User;


public class Client
{
	private String name;
	private String description;

	private Set<User> user = new HashSet<>();

	private Set<Category> categories = new HashSet<>();
	private Set<Product>  products   = new HashSet<>();

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

	public Set<User> getUser()
	{
		return this.user;
	}

	public void setUser(final Set<User> user)
	{
		this.user = user;
	}

	public Set<Category> getCategories()
	{
		return this.categories;
	}

	public void setCategories(final Set<Category> categories)
	{
		this.categories = categories;
	}

	public Set<Product> getProducts()
	{
		return this.products;
	}

	public void setProducts(final Set<Product> products)
	{
		this.products = products;
	}
}
