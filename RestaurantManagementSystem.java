import java.util.*;

abstract class Person {
    protected String name;

    Person(String name) {
        this.name = name;
    }

    abstract void displayInfo();
}

class Customer extends Person {
    private int tableNo;

    Customer(String name, int tableNo) {
        super(name);
        this.tableNo = tableNo;
    }

    public int getTableNo() {
        return tableNo;
    }

    @Override
    public void displayInfo() {
        System.out.println("\nCustomer : " + name);
        System.out.println("Table No : " + tableNo);
    }
}

class Food {
    private String name;
    private int price;

    Food(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class Restaurant {

    private ArrayList<Food> menu = new ArrayList<>();
    private double totalRevenue = 0;

    public void addFood(String name, int price) {
        menu.add(new Food(name, price));
    }

    public void removeFood(int index) {
        if (index >= 0 && index < menu.size()) {
            menu.remove(index);
            System.out.println("Item Removed Successfully.");
        } else {
            System.out.println("Invalid Item Number.");
        }
    }

    public void showMenu() {
        System.out.println("\n========= MENU =========");
        for (int i = 0; i < menu.size(); i++) {
            Food f = menu.get(i);
            System.out.println((i + 1) + ". " + f.getName() + " - Rs." + f.getPrice());
        }
    }

    public void customerSection(Scanner sc) {

        System.out.print("\nEnter Customer Name : ");
        String name = sc.next();

        System.out.print("Enter Table Number : ");
        int table = sc.nextInt();

        Customer customer = new Customer(name, table);
        customer.displayInfo();

        ArrayList<Food> orders = new ArrayList<>();
        int total = 0;

        while (true) {
            showMenu();

            System.out.print("\nSelect Food Number (0 to Finish): ");
            int choice = sc.nextInt();

            if (choice == 0)
                break;

            if (choice < 1 || choice > menu.size()) {
                System.out.println("Invalid Choice!");
                continue;
            }

            Food selected = menu.get(choice - 1);
            orders.add(selected);
            total += selected.getPrice();

            System.out.println(selected.getName() + " Added.");
        }

        generateBill(orders, total);
        totalRevenue += total;
    }

    private void generateBill(ArrayList<Food> orders, int total) {

        double gst = total * 0.05;
        double finalAmount = total + gst;

        System.out.println("\n========== BILL ==========");

        for (Food food : orders) {
            System.out.println(food.getName() + " - Rs." + food.getPrice());
        }

        System.out.println("--------------------------");
        System.out.println("Food Total : Rs." + total);
        System.out.println("GST (5%)   : Rs." + gst);
        System.out.println("Final Bill : Rs." + finalAmount);
        System.out.println("==========================");
    }

    public void adminSection(Scanner sc) {

        System.out.print("Enter Admin PIN : ");
        int pin = sc.nextInt();

        if (pin != 1234) {
            System.out.println("Access Denied!");
            return;
        }

        while (true) {

            System.out.println("\n====== ADMIN PANEL ======");
            System.out.println("1. View Menu");
            System.out.println("2. Add Food");
            System.out.println("3. Remove Food");
            System.out.println("4. View Revenue");
            System.out.println("5. Exit");

            System.out.print("Choice : ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showMenu();
                    break;

                case 2:
                    System.out.print("Food Name : ");
                    String name = sc.next();

                    System.out.print("Price : ");
                    int price = sc.nextInt();

                    addFood(name, price);
                    System.out.println("Food Added.");
                    break;

                case 3:
                    showMenu();
                    System.out.print("Enter Item Number : ");
                    int remove = sc.nextInt();
                    removeFood(remove - 1);
                    break;

                case 4:
                    System.out.println("Total Revenue : Rs." + totalRevenue);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}

public class RestaurantManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Restaurant restaurant = new Restaurant();

        restaurant.addFood("Pizza", 150);
        restaurant.addFood("Burger", 100);
        restaurant.addFood("Pasta", 130);
        restaurant.addFood("Momos", 120);
        restaurant.addFood("Biryani", 180);
        restaurant.addFood("Fries", 90);
        restaurant.addFood("PaneerTikka", 200);

        while (true) {

            try {

                System.out.println("\n===== RESTAURANT SYSTEM =====");
                System.out.println("1. Customer");
                System.out.println("2. Admin");
                System.out.println("3. Exit");

                System.out.print("Enter Choice : ");
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        restaurant.customerSection(sc);
                        break;

                    case 2:
                        restaurant.adminSection(sc);
                        break;

                    case 3:
                        System.out.println("Thank You!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please Enter Numbers Only.");
                sc.nextLine();
            }
        }
    }
}