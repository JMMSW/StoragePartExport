
package com.company.graphparttransfer.func.storage;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.company.graphparttransfer.model.Client;
import com.company.graphparttransfer.model.client.Category;
import com.company.graphparttransfer.model.client.Product;
import com.company.graphparttransfer.model.common.User;


public class MockData
{
	public static void fillStorage1()
	{
		final User user  = new User("User1@example.com", "1", "User");
		final User user2 = new User("User2@example.com", "2", "User");
		final User user3 = new User("User3@example.com", "3", "User");
		final User user4 = new User("User4@example.com", "4", "User");
		final User user5 = new User("User5@example.com", "5", "User");

		final Set<User> users = DB.root1().getUser();
		users.addAll(Stream.of(user, user2, user3, user4, user5).collect(Collectors.toSet()));

		DB.instance1().store(users);

		final Client client1 =
			MockData.createClient("Client Foo", Stream.of(user, user2, user3).collect(Collectors.toSet()));
		
		final Client client2 = MockData.createClient("Client Bar", Stream.of(user4, user5).collect(Collectors.toSet()));

		final Client client3 = MockData.createClient("Client Baz", Stream.of(user2, user5).collect(Collectors.toSet()));
		
		final Set<Client> clients = DB.root1().getClients();
		clients.add(client1);
		clients.add(client2);
		clients.add(client3);

		DB.instance1().store(clients);
		
	}
	
	public static Client createClient(final String name, final Set<User> user)
	{
		final Client client = new Client();
		client.setName(name);
		client.getUser().addAll(user);
		
		final Category category  = new Category("Category 1 for " + name);
		final Category category2 = new Category("Category 2 for " + name);
		client.getCategories().add(category);
		client.getCategories().add(category2);
		
		final Product product  = new Product("Product 1", category);
		final Product product2 = new Product("Product 2", category);
		final Product product3 = new Product("Product 3", category2);
		final Product product4 = new Product("Product 4", category2);
		client.getProducts().add(product);
		client.getProducts().add(product2);
		client.getProducts().add(product3);
		client.getProducts().add(product4);
		return client;
	}
}
