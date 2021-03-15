
package com.company.graphparttransfer.model.common;

/**
 * A User, identified by it's email address
 *
 */
public class User
{
	private String email;
	private String firstname;
	private String lastname;

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
	
	protected void setEmail(final String email)
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
