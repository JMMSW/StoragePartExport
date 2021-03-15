
package com.company.graphparttransfer.model.common;

public class User
{
	String email;
	String firstname;
	String lastname;
	
	public User()
	{
		super();
	}
	
	public User(final String email, final String firstname, final String lastname)
	{
		super();
		this.email     = email;
		this.firstname = firstname;
		this.lastname  = lastname;
	}
	
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public String getFirstname()
	{
		return this.firstname;
	}

	public void setFirstname(final String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return this.lastname;
	}

	public void setLastname(final String lastname)
	{
		this.lastname = lastname;
	}
}
