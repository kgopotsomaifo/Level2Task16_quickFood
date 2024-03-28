import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class Main {
	// main driver code: take input for customer and restaurant, read drivers.txt
	// and find driver and restaurant in the same area, then produce a final invoice text file

	public static void main(String[] args) {
		try {
			File drivers = new File("drivers.txt");
			Scanner driversScanner = new Scanner(drivers);
			Scanner inputScanner = new Scanner(System.in); // scanner to scan input from user
			Formatter invoice = new Formatter("invoice.txt");

			// an empty Customer and Restaurant object is constructed, to be filled in via inputScanner
			Customer theCustomer = new Customer(null, null, null, null, null);
			Restaurant theRestaurant = new Restaurant(null, null, null);

			System.out.println("In which city is the customer?");
			theCustomer.customerLocation = inputScanner.nextLine().toLowerCase();
			System.out.println("In which city is your restaurant?");
			theRestaurant.restaurantLocation = inputScanner.nextLine().toLowerCase();

			List<String[]> availableDrivers = AvailableDrivers(driversScanner, theCustomer, theRestaurant);

			formatInvoice(inputScanner, invoice, theCustomer, theRestaurant, availableDrivers);

			inputScanner.close();
			driversScanner.close();
			invoice.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	} // end of main method

	// if there are no available drivers, output should be no drivers will deliver,
	// else continue with program and format the invoice
	static void formatInvoice(Scanner inputScanner, Formatter invoice, Customer theCustomer, Restaurant theRestaurant,
			List<String[]> availableDrivers) {
		try {
			if (availableDrivers.isEmpty()) {
				invoice.format("Sorry! Our drivers are too far away from you to be able to deliver to your location.");
			} else {
				System.out.println("What is the customer's name?");
				theCustomer.customerName = inputScanner.nextLine();
				System.out.println("Provide the customer's contact number:");
				theCustomer.contactNum = inputScanner.nextLine();
				System.out.println("What is the customer's street address?");
				theCustomer.customerAddress = inputScanner.nextLine();
				System.out.println("What is the customer's email address?");
				theCustomer.customerMail = inputScanner.nextLine();

				System.out.println("What is the name of the restaurant?");
				theRestaurant.restaurantName = inputScanner.nextLine();
				System.out.println("Provide the contact number of the restaurant:");
				theRestaurant.restaurantContact = inputScanner.nextLine();

				// an order is constructed for the details of the meal order
				Order theOrder = new Order(null, null, 0, 0, null);
				System.out.println("Provide the order number:");
				theOrder.orderNumber = inputScanner.nextLine();
				System.out.println("What meal is being ordered?");
				theOrder.meal = inputScanner.nextLine();
				System.out.println("What is the price of the item?");
				theOrder.price = Double.parseDouble(inputScanner.nextLine());
				System.out.println("How many are being ordered?");
				theOrder.quantity = Integer.parseInt(inputScanner.nextLine());
				System.out.println("Are there any special instructions for this order?");
				theOrder.instructions = inputScanner.nextLine();

				// logic for getting the correct driver
				int lowestLoad = Integer.parseInt(availableDrivers.get(0)[2]);
				String[] lowestLoadDriver = availableDrivers.get(0);
				// if there is more than 1 driver in the list, compare loads of drivers
				if (availableDrivers.size() > 1) {
					for (int i = 1; i < availableDrivers.size(); i++) {
						int temporaryLoad = Integer.parseInt(availableDrivers.get(i)[2]);
						if (temporaryLoad < lowestLoad) {
							lowestLoad = Integer.parseInt(availableDrivers.get(i)[2]);
							lowestLoadDriver = availableDrivers.get(i);
						}
					}
				}
				// formating the invoice in specified format
				invoice.format("Order number: %s\n", theOrder.orderNumber);
				invoice.format("Customer: %s\n", theCustomer.customerName);
				invoice.format("Email: %s\n", theCustomer.customerMail);
				invoice.format("Phone number: %s\n", theCustomer.contactNum);
				invoice.format("Location: %s\n\n", theCustomer.customerLocation);
				invoice.format("You have ordered the following from %s in %s:\n\n", theRestaurant.restaurantName,
						theRestaurant.restaurantLocation);
				invoice.format("%s x %s (R%s)\n\n", theOrder.quantity, theOrder.meal, theOrder.price);
				invoice.format("Special instructions: %s\n\n", theOrder.instructions);
				invoice.format("Total: R%s\n\n", theOrder.calculateTotal());
				invoice.format(
						"%s is nearest to the restaurant so they will be delivering your order at:\n\n%s, %s\n\n",
						lowestLoadDriver[0], theCustomer.customerAddress, theCustomer.customerLocation);
				invoice.format("If you need to contact the restaurant, their number is %s.",
						theRestaurant.restaurantContact);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	} // end of format invoice method

	// this method creates the list of available drivers.
	static List<String[]> AvailableDrivers(Scanner driversScanner, Customer theCustomer, Restaurant theRestaurant) {
		// availableDrivers list stores drivers in the city of customer and restaurant
		ArrayList<String[]> availableDrivers = new ArrayList<String[]>();
		// loop through drivers.txt
		while (driversScanner.hasNextLine()) {
			// each line will be stored as the variable "line"
			String line = driversScanner.nextLine();
			// convert line to an array of strings, split with delimiter ", "
			String[] lineAsArray = line.split(", ");
			// if lineAsArray[1] which is the city, matches inputed city/cities, add to
			// availableDrivers list
			if (lineAsArray[1].toLowerCase().equals(theCustomer.customerLocation)
					&& lineAsArray[1].toLowerCase().equals(theRestaurant.restaurantLocation)) {
				availableDrivers.add(lineAsArray);
			}
		}
		return availableDrivers;
	}

}
