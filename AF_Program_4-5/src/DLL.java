import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/*
Andy Fleischer - CS202 - Program 4-5 - 8/27/2021
------------------------------------------------
This file manages 3 classes that handle a menu's
DLL. There is the overall DLL, with head and tail
DNodes (DLL nodes). A DNode has next and
previous pointers, and extends class Section. The
Section class holds all the data related to
one section of a menu, including the title, items
(with prices), and ordered items (with quantities).
The Section class has the ability to add and
remove items, display, and finalize the order for
that section.
 */

class Section {

    Scanner scanner;
    String title;
    Map<String, Double> items;
    Map<String, Integer> order;

    public Section(String data) {
        scanner = new Scanner(System.in);
        items = new TreeMap<String, Double>();
        order = new TreeMap<String, Integer>();

        String[] split = data.split("/");
        title = split[0];

        String[] foods = split[1].split(",");
        for (String food: foods) {
            String[] food_split = food.split("-");
            items.put(food_split[0], Double.parseDouble(food_split[1]));
        }
    }

    public void display() {
        System.out.println(title);
        for (int i = 0; i < title.length(); i++)
            System.out.print("-");
        System.out.println();

        for (String key: items.keySet()) {
            double rounded = Math.round(items.get(key) * 100.0) / 100.0;
            System.out.println("$" + rounded + " - " + key);
        }
    }
    
    public void add_item() {
        System.out.println("What would you like to add?");
        int i = 0;
        for (String key: items.keySet()) { //print all the items
            System.out.println(i + ". " + key);
            ++i;
        }
        //get choices
        int choice = scanner.nextInt();
        if (choice >= i) {
            System.out.println("Invalid choice.");
            return;
        }
        String item = (String) items.keySet().toArray()[choice];

        System.out.print("How many orders of " + item + " would you like to add? ");
        int count = scanner.nextInt();

        if (order.containsKey(item)) { //already exists, so add what you had before
            count += order.get(item);
        }
        order.put(item, count);
        System.out.println("Added");
    }

    public void remove_item() {
        System.out.println("What would you like to remove?");
        int i = 0;
        for (String key: order.keySet()) { //print all the items
            System.out.println(i + ". " + key + " (" + order.get(key) + ")");
            ++i;
        }
        //get choices
        int choice = scanner.nextInt();
        if (choice >= i) {
            System.out.println("Invalid choice.");
            return;
        }
        String item = (String) order.keySet().toArray()[choice];

        System.out.print("How many of your " + order.get(item) + " " + item + " would you like to remove? ");
        int count = scanner.nextInt();

        order.put(item, order.get(item) - count); //subtract the amount
        if (order.get(item) <= 0) { //removed all, so remove from order
            order.remove(item);
        }
    }

    public String package_order() {
        boolean has_item = false;
        String output = new String(title + ":");
        for (String key: order.keySet()) {
            if (order.get(key) != 0) {
                output = "\n" + output + key + "(" + order.get(key) + ")";
                has_item = true;
            }
        }
        if (has_item)
            return output;
        return "";
    }

    public float cost() {
        float to_return = 0;
        for (String key: items.keySet()) {
            if (order.get(key) != null)
                to_return += (order.get(key) * items.get(key));
        }
        return to_return;
    }
}

class DNode extends Section {

    private DNode next;
    private DNode prev;

    public DNode(String data) {
        super(data);
        next = null;
        prev = null;
    }

    public DNode get_next() {
        return next;
    }
    public void set_next(DNode next) {
        this.next = next;
    }

    public DNode get_prev() {
        return prev;
    }
    public void set_prev(DNode prev) {
        this.prev = prev;
    }
}

class DLL {

    private DNode head;
    private DNode tail;
    private DNode current;

    public DLL() {
        head = null;
        tail = null;
        current = null;
    }

    public void insert(String line) {
        DNode new_node = new DNode(line);

        if (head == null) {
            head = new_node;
            current = head;
        }
        else {
            tail.set_next(new_node);
            new_node.set_prev(tail);
        }
        tail = new_node;
    }

    public void display() {
        if (current == null) {
            System.out.println("No menu items!");
            return;
        }
        current.display();
    }

    public void go_forward() {
        if (current.get_next() == null) {
            System.out.println("Cannot go forward!");
            return;
        }
        current = current.get_next();
    }

    public void go_backward() {
        if (current.get_prev() == null) {
            System.out.println("Cannot go backward!");
            return;
        }
        current = current.get_prev();
    }
    
    public void add_item() {
        if (current == null) {
            System.out.println("No menu!");
            return;
        }
        current.add_item();
    }

    public void remove_item() {
        if (current == null) {
            System.out.println("No menu!");
            return;
        }
        current.remove_item();
    }

    public String package_order() {
        return package_order(head) + "\nPrice: $" + total_cost(head);
    }

    private String package_order(DNode head) {
        if (head == null) {
            return "";
        }
        return head.package_order() + package_order(head.get_next());
    }

    private float total_cost(DNode head) {
        if (head == null) {
            return 0;
        }
        return head.cost() + total_cost(head.get_next());
    }
}
