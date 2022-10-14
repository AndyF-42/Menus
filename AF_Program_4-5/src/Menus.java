import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
Andy Fleischer - CS202 - Program 4-5 - 8/27/2021
------------------------------------------------
This file manages the 3 menu classes, and their
abstract base class Menu. Menu predefines some
functions like the constructor, finish_order,
etc. that are overloaded and called by the
derived classes. There are 3 types of Menus:
Italian, Ramen, and Thai, each with their own
unique implementations of functions that need
to be different. Menu derives from DLL to have
access to all of its functions directly.
 */

abstract class Menu extends DLL {

    Scanner scanner;

    public Menu(String file) {
        scanner = new Scanner(System.in);

        try {
            File my_file = new File(file);
            Scanner scanner = new Scanner(my_file);

            while (scanner.hasNextLine()) {
                insert(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    abstract public String[] place_order();

    protected String[] finish_order() {
        String order = package_order();

        if (order.equals("")) {
            System.out.println("You have no items!");
            return null;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.println("Thank you, " + name + ". Your order has been placed.");
        String[] to_return = {name, order};
        return to_return;
    }

    protected void help() {
        System.out.println("0. Cancel order");
        System.out.println("1. Previous section");
        System.out.println("2. Next section");
        System.out.println("3. Add item");
        System.out.println("4. Remove item");
        System.out.println("5. Finish order");
    }
}

class Italian extends Menu {

    public Italian(String file) {
        super(file);

    }

    public String[] place_order() {
        int input = 0;
        String[] order = null;

        do {
            display();
            help();
            input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 0: break;
                case 1: go_backward(); break;
                case 2: go_forward(); break;
                case 3: add_item(); break;
                case 4: remove_item(); break;
                case 5: order = finish_order(); break;
                default: System.out.println("Invalid command."); break;
            }
        } while (input != 0 && order == null);

        return order;
    }

    protected String[] finish_order() {
        String[] order = super.finish_order();
        order[1] = "\n(Italian)" + order[1];
        return order;
    }

    protected void help() {
        System.out.println("----- Italian -----");
        super.help();
    }
}

class Ramen extends Menu {

    public Ramen(String file) {
        super(file);

    }

    public String[] place_order() {
        int input = 0;
        String[] order = null;

        do {
            display();
            help();
            input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 0: break;
                case 1: go_backward(); break;
                case 2: go_forward(); break;
                case 3: add_item(); break;
                case 4: remove_item(); break;
                case 5: order = finish_order(); break;
                default: System.out.println("Invalid command."); break;
            }
        } while (input != 0 && order == null);

        return order;
    }

    protected String[] finish_order() {
        String[] order = super.finish_order();
        order[1] = "\n(Ramen)" + order[1];
        return order;
    }

    protected void help() {
        System.out.println("----- Ramen -----");
        super.help();
    }
}

class Thai extends Menu {

    public Thai(String file) {
        super(file);

    }

    public String[] place_order() {
        int input = 0;
        String[] order = null;

        do {
            display();
            help();
            input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 0: break;
                case 1: go_backward(); break;
                case 2: go_forward(); break;
                case 3: add_item(); break;
                case 4: remove_item(); break;
                case 5: order = finish_order(); break;
                default: System.out.println("Invalid command."); break;
            }
        } while (input != 0 && order == null);

        return order;
    }

    protected String[] finish_order() {
        String[] order = super.finish_order();
        order[1] = "\n(Thai)" + order[1];
        return order;
    }

    protected void help() {
        System.out.println("----- Thai -----");
        super.help();
    }
}