public class Customer {
	// Attributes
		String customerName; 
		String customerMail; 
		String contactNum; 
		String customerLocation; // city
		String customerAddress; 
		
		// constructor for customer class
		public Customer(String customerName, String contactNum, String customerAddress, String customerLocation, String customerMail) {
			
			this.customerName = customerName;
			this.contactNum = contactNum;
			this.customerAddress = customerAddress;
			this.customerLocation = customerLocation;
			this.customerMail = customerMail;
		}

}
