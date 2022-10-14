/*
Andy Fleischer - CS202 - Program 4-5 - 8/27/2021
------------------------------------------------
This file manages 4 classes to handle the red-
black tree of linear linked lists data structure.
There is the overall RBTree, with a TNode (tree
node) root. TNode has left, right, and parent
TNodes, boolean for black/red, and LLL orders
for orders with the same name. An LLL has a Node
head, and Nodes are a single order, with a name
(for sorting) and an order (string)
 */

class Node {

    private String name;
    private String order;
    private Node next;

    public Node(String name, String order) {
        this.name = name;
        this.order = order;
        next = null;
    }

    public Node get_next() {
        return next;
    }
    public void set_next(Node next) {
        this.next = next;
    }

    public String get_name() {
        return name;
    }
    public String get_order() {
        return order;
    }
}

class LLL {

    private Node head;

    public LLL() {
        head = null;
    }

    public void insert(String name, String order) {
        head = insert(head, name, order);
    }

    private Node insert(Node head, String name, String order) {
        if (head == null) {
            head = new Node(name, order);
            return head;
        }
        head.set_next(insert(head.get_next(), name, order));
        return head;
    }

    public void display() {
        display(head);
    }

    private void display(Node head) {
        if (head == null)
            return;

        System.out.println(head.get_name());
        System.out.println(head.get_order());
        System.out.println();
        display(head.get_next());
    }

    public boolean equals(String name) {
        if (head == null)
            return false;
        return head.get_name().equals(name);
    }

    public boolean less_than(String name) {
        if (head == null)
            return false;
        return head.get_name().compareTo(name) < 0;
    }

    public boolean greater_than(String name) {
        if (head == null)
            return false;
        return head.get_name().compareTo(name) > 0;
    }

    public String retrieve() {
        return retrieve(head);
    }

    protected String retrieve(Node head) {
        if (head == null) {
            return "";
        }
        return head.get_order() + "\n\n" + retrieve(head.get_next());
    }
}

class TNode {

    private LLL orders;
    private TNode left;
    private TNode right;
    private TNode parent;
    private boolean is_black;

    public TNode() {
        orders = new LLL();
        left = null;
        right = null;
        parent = null;
        is_black = false;
    }

    public TNode get_left() {
        return left;
    }
    public void set_left(TNode left) {
        this.left = left;
    }

    public TNode get_right() {
        return right;
    }
    public void set_right(TNode right) {
        this.right = right;
    }

    public TNode get_parent() {
        return parent;
    }
    public void set_parent(TNode parent) {
        this.parent = parent;
    }

    public boolean is_black() {
        return is_black;
    }
    public void color(char color) {
        if (color == 'b')
            is_black = true;
        if (color == 'r')
            is_black = false;
    }

    public LLL get_data() {
        return orders;
    }
}

class RBTree {

    private TNode root;

    public RBTree() {
        root = null;
    }

    public void insert(String name, String order) {
        root = insert(null, root, name, order);
        root = insert_fix(root, name, order);
    }

    private TNode insert(TNode parent, TNode root, String name, String order) {
        if (root == null) {
            root = new TNode();
            root.get_data().insert(name, order);
            root.set_parent(parent);
            return root;
        }

        if (root.get_data().less_than(name)) {
            root.set_right(insert(root, root.get_right(), name, order));
        }
        else if (root.get_data().greater_than(name)) {
            root.set_left(insert(root, root.get_left(), name, order));
        } else {
            root.get_data().insert(name, order);
        }
        return root;
    }

    private TNode insert_fix(TNode root, String name, String order) {
        boolean flag = false;

        if (root==null)
            return null;
        else if(root.get_data().less_than(name) && root != this.root) {
            root.set_left(insert_fix(root.get_left(), name, order));
            root.get_left().set_parent(root);
            if(!root.is_black() && !root.get_left().is_black())
                flag = true;
        }
        else if (root.get_data().greater_than(name) && root != this.root) {
            root.set_right(insert_fix(root.get_right(), name, order));
            root.get_right().set_parent(root);
            if(!root.is_black() && !root.get_right().is_black())
                flag = true;
        }

        if(flag) {
            if(root.get_parent().get_right() == root) {
                if(root.get_parent().get_left() == null || root.get_parent().get_left().is_black()) {
                    if(root.get_left() != null && !root.get_left().is_black())
                        root = rotateLeft(root);
                    else if(root.get_right() != null && !root.get_right().is_black())
                        root = rotateRight(root);
                }
                else {
                    root.get_parent().get_left().color('b');
                    root.color('b');
                    if(root.get_parent() != this.root)
                        root.get_parent().color('r');
                }
            }
            else {
                if(root.get_parent().get_right() == null || root.get_parent().get_right().is_black()) {
                    if(root.get_right() != null && !root.get_right().is_black())
                        root = rotateRight(root);
                    else if(root.get_left() != null && !root.get_left().is_black())
                        root = rotateLeft(root);
                }
                else {
                    root.get_parent().get_left().color('b');
                    root.color('b');
                    if(root.get_parent() != this.root)
                        root.get_parent().color('r');
                }
            }
        }
        return root;
    }

    private TNode rotateLeft(TNode root) {
        TNode right = root.get_right();
        TNode left = root.get_left();

        right.set_left(root);
        root.set_right(left);
        root.set_parent(right);

        if (left != null) {
            left.set_parent(root);
        }
        return right;
    }

    private TNode rotateRight(TNode root) {
        TNode left = root.get_left();
        TNode right = root.get_right();

        left.set_right(root);
        root.set_left(right);
        root.set_parent(left);

        if (right != null) {
            right.set_parent(root);
        }
        return left;
    }

    public void display() {
        display(root);
    }

    private void display(TNode root) {
        if (root == null)
            return;

        display(root.get_left());
        root.get_data().display();
        display(root.get_right());
    }

    public void remove_all() {
        root = null; // I love Java
    }

    public String retrieve(String name) {
        return retrieve(root, name);
    }

    protected String retrieve(TNode root, String name) {
        if (root == null) {
            return "No orders under that name.";
        }

        if (root.get_data().less_than(name)) {
            return retrieve(root.get_right(), name);
        } else if (root.get_data().greater_than(name)) {
            return retrieve(root.get_left(), name);
        } else {
            return root.get_data().retrieve();
        }
    }
}
