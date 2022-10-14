import java.util.Scanner;

/*
Andy Fleischer - CS202 - Program 4-5 - 8/27/2021
------------------------------------------------
This file has the static main method and creates
the array of DLLs for all the menus. It serves
as a testing application to test all the methods
from the menu types and RBTree.
 */

public class Main {

    Menu[] menus;
    Scanner scanner;
    RBTree orders;

    public Main() {

        menus = new Menu[3];
        orders = new RBTree();
        menus[0] = new Ramen("ramen_menu.txt");
        menus[1] = new Italian("italian_menu.txt");
        menus[2] = new Thai("thai_menu.txt");
        scanner = new Scanner(System.in);

        System.out.println("-----------------------");
        System.out.println("   Welcome to Menus!   ");
        System.out.println("-----------------------");

        int input;
        do {
            System.out.println("----- Main -----");
            System.out.println("0. Quit");
            System.out.println("1. Order ramen");
            System.out.println("2. Order italian");
            System.out.println("3. Order thai");
            System.out.println("4. View all orders");
            System.out.println("5. Remove all orders");
            System.out.println("6. Retrieve order (and display)");

            input = scanner.nextInt();
            scanner.nextLine();

            if (input >= 1 && input <= 3) {
                String[] order = menus[input-1].place_order();
                if (order != null)
                    orders.insert(order[0], order[1]);
            } else if (input == 4) {
                orders.display();
            } else if (input == 5) {
                orders.remove_all();
            } else if (input == 6) {
                System.out.print("Name: ");
                System.out.println(orders.retrieve(scanner.nextLine()));
            }
            else if (input != 0) {
                System.out.println("Invalid command.");
            }

        } while (input != 0);
    }

    public static void main(String[] args) {
        new Main();
    }
}
