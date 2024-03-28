public class Order {
	// attributes of Order object
    String orderNumber;
    String meal;
    double price;
    int quantity;
    String instructions;
    
 // constructor for Order class
    public Order(String orderNumber, String meal, double price, int quantity, String instructions) {
        this.orderNumber = orderNumber;
        this.meal = meal;
        this.price = price;
        this.quantity = quantity;
        this.instructions = instructions;
    }
    
    
    // this method will calculate the total of the order
    double calculateTotal() {
        double total;
        total = price * quantity;
        return total;
    }

}
