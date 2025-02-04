package main;

import java.util.InputMismatchException;
import java.text.DecimalFormat;
import model.Product;
import model.Sale;
import model.Amount;
import model.Employee;
import model.Client;

import java.util.ArrayList;
import java.util.Scanner;


public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory = new ArrayList<>();
    private ArrayList<Sale> sales = new ArrayList<>();
    final static double TAX_RATE = 1.04;

    public static void main(String[] args) {
        initSession();
        Shop shop = new Shop();
        shop.loadInventory();

        int option;
        boolean exit = false;

        do {
            System.out.println("\n===========================");
            System.out.println("        Main Menu       ");
            System.out.println("===========================");
            System.out.println("1) Show Cash");
            System.out.println("2) Add Product");
            System.out.println("3) Add Stock");
            System.out.println("4) Mark a Product as Expired");
            System.out.println("5) Show Inventory");
            System.out.println("6) Make a Sale");
            System.out.println("7) Show Sales");
            System.out.println("8) Show Total Sales");
            System.out.println("9) Delete Product");
            System.out.println("10) Exit");
            System.out.print("Select an option: ");
            option = scannerInt();

            switch (option) {
                case 1:
                    shop.showCash();
                    break;
                case 2:
                    shop.addProduct();
                    break;
                case 3:
                    shop.addStock();
                    break;
                case 4:
                    shop.setExpired();
                    break;
                case 5:
                    shop.showInventory();
                    break;
                case 6:
                    shop.sale();
                    break;
                case 7:
                    shop.showSales();
                    break;
                case 8:
                    shop.totalAmount();
                    break;
                case 9:
                    shop.deleteProduct();
                    break;
                case 10:
                    exit = true;
                    System.out.println("\nExiting the program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (!exit);
    }

    public static void initSession() {
        boolean login = false;

        do {
            System.out.print("\nEnter Employee ID: ");
            int employeeId = scannerInt();
            System.out.print("Enter Password: ");
            String password = scannerString();

            Employee employee = new Employee("test", employeeId, password);
            login = employee.login(employeeId, password);

            if (!login) {
                System.out.println("Incorrect credentials. Try again.");
            }
        } while (!login);

        System.out.println("Login successful.");
    }

    public void loadInventory() {
        inventory.add(new Product("Apple", 10.00, true, 10));
        inventory.add(new Product("Pear", 20.00, true, 20));
        inventory.add(new Product("Hamburguer", 30.00, true, 30));
        inventory.add(new Product("Strawberry", 5.00, true, 20));
    }

    private void showCash() {
        System.out.println("\nTotal Cash: $" + cash);
    }

    public void addProduct() {
        System.out.print("\nProduct Name: ");
        String name = scannerString();

        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.println("This product already exists.");
                return;
            }
        }

        System.out.print("Wholesaler Price: ");
        double wholesalerPrice = scannerDouble();
        System.out.print("Initial Stock: ");
        int stock = scannerInt();

        inventory.add(new Product(name, wholesalerPrice, true, stock));
        System.out.println("Product added successfully.");
    }

    public void addStock() {
        System.out.print("\nProduct Name: ");
        String name = scannerString();
        Product product = findProduct(name);

        if (product != null) {
            System.out.print("Quantity to add: ");
            int stock = scannerInt();
            product.setStock(product.getStock() + stock);
            System.out.println("Stock updated. New stock: " + product.getStock());
        } else {
            System.out.println("Product not found.");
        }
    }

    private void setExpired() {
        System.out.print("\nProduct Name: ");
        String name = scannerString();
        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("Product " + name + " has been marked as expired.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void showInventory() {
        System.out.println("\nShop Inventory:");
        for (Product product : inventory) {
            System.out.println("-------------------------------");
            System.out.println(product.getName());
            System.out.println("- Wholesaler Price: $" + product.getWholesalerPrice());
            System.out.println("- Available: " + product.isAvailable());
            System.out.println("- Stock: " + product.getStock());
        }
    }

    public void sale() {
        DecimalFormat df = new DecimalFormat("#.00");
        Amount bill = new Amount(0);
        double totalAmount = 0.0;
        ArrayList<Product> purchasedProducts = new ArrayList<>();
        int memberId = 0;

        System.out.print("\nCustomer Name: ");
        String name = scannerString();
        Client client = new Client(name, memberId + 1, bill);

        while (true) {
            System.out.print("Product Name (0 to finish): ");
            name = scannerString();

            if (name.equals("0")) {
                break;
            }

            Product product = findProduct(name);

            if (product != null && product.isAvailable() && product.getStock() > 0) {
                totalAmount += product.getPublicPrice().getValue();
                product.setStock(product.getStock() - 1);
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                purchasedProducts.add(product);
                System.out.println("Product added.");
            } else {
                System.out.println("Product not found or out of stock.");
            }
        }

        totalAmount *= TAX_RATE;
        cash.setValue(cash.getValue() + totalAmount);
        bill.setValue(totalAmount);
        sales.add(new Sale(client, purchasedProducts.toArray(new Product[0]), bill.getValue()));

        System.out.println("\nPurchase completed successfully.");
        System.out.println("Total amount: $" + df.format(bill.getValue()));

        // Verificar si el total supera 100 y si el cliente tiene suficiente saldo
        if (bill.getValue() > 100) {
            System.out.println("Warning!!! The client has exceeded $100.");
            boolean enoughMoney = client.pay(bill);

            if (!enoughMoney) {
                double amountDue = bill.getValue() - 100;
                System.out.println("The client needs to pay an additional: $" + df.format(amountDue));
            }
        }

        System.out.println();
    }

    public void showSales() {
        if (sales.isEmpty()) {
            System.out.println("\nNo sales recorded.");
            return;
        }

        System.out.println("\nSales List:");
        for (Sale sale : sales) {
            System.out.println("Customer: " + sale.getClient());
            System.out.print("Products: ");
            for (Product product : sale.getProducts()) {
                System.out.print(product.getName() + ", ");
            }
            System.out.println("Total: $" + sale.getAmount());
        }
    }

    public void totalAmount() {
        double totalSales = 0;
        if (sales.isEmpty()) {
            System.out.println("\nNo sales recorded.");
            return;
        }

        for (Sale sale : sales) {
            totalSales += sale.getAmount().getValue();
        }

        System.out.println("\nTotal Sales Revenue: $" + new Amount(totalSales));
    }

    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void deleteProduct() {
        System.out.print("\nProduct Name: ");
        String name = scannerString();

        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                inventory.remove(product);
                System.out.println("Product removed successfully.");
                return;
            }
        }

        System.out.println("Product not found.");
    }

    public static int scannerInt() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number.");
                sc.nextLine();
            }
        }
    }

    public static String scannerString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static double scannerDouble() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid decimal number.");
                sc.nextLine();
            }
        }
    }
}
