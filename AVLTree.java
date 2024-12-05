public class AVLTree<T extends Comparable<? super T>> extends BST<T> {
    protected int height;

    public AVLTree() {
        super();
        height = -1;
    }

    public AVLTree(BSTNode<T> root) {
        super(root);
        height = -1;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BSTNode<T> node) {
        if (node == null)
            return -1;
        else
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private AVLTree<T> getLeftAVL() {
        AVLTree<T> leftsubtree = new AVLTree<>(root.left);
        return leftsubtree;
    }

    private AVLTree<T> getRightAVL() {
        AVLTree<T> rightsubtree = new AVLTree<>(root.right);
        return rightsubtree;
    }

    protected int getBalanceFactor() {
        if (isEmpty())
            return 0;
        else
            return getRightAVL().getHeight() - getLeftAVL().getHeight();
    }

    public void insertAVL(T el) {
        super.insert(el);
        this.balance();
    }

    public void deleteAVL(T el) {
        root = deleteRec(root, el);
        this.balance();
    }

    private BSTNode<T> deleteRec(BSTNode<T> node, T el) {
        if (node == null) {
            return node;
        }

        if (el.compareTo(node.el) < 0) {
            node.left = deleteRec(node.left, el);
        } else if (el.compareTo(node.el) > 0) {
            node.right = deleteRec(node.right, el);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            node.el = minValue(node.right);
            node.right = deleteRec(node.right, node.el);
        }

        return node;
    }

    private T minValue(BSTNode<T> node) {
        T minv = node.el;
        while (node.left != null) {
            minv = node.left.el;
            node = node.left;
        }
        return minv;
    }

    protected void balance() {
        if (!isEmpty()) {
            getLeftAVL().balance();
            getRightAVL().balance();

            adjustHeight();

            int balanceFactor = getBalanceFactor();

            if (balanceFactor == -2) {
                System.out.println("Balancing node with el: " + root.el);
                if (getLeftAVL().getBalanceFactor() < 0)
                    rotateRight();
                else
                    rotateLeftRight();
            } else if (balanceFactor == 2) {
                System.out.println("Balancing node with el: " + root.el);
                if (getRightAVL().getBalanceFactor() > 0)
                    rotateLeft();
                else
                    rotateRightLeft();
            }
        }
    }

    protected void adjustHeight() {
        if (isEmpty())
            height = -1;
        else
            height = 1 + Math.max(getLeftAVL().getHeight(), getRightAVL().getHeight());
    }

    protected void rotateRight() {
        System.out.println("RIGHT ROTATION");
        BSTNode<T> tempNode = root.left;
        root.left = tempNode.right;
        tempNode.right = root;
        root = tempNode;

        getRightAVL().adjustHeight();
        adjustHeight();
    }

    protected void rotateLeft() {
        System.out.println("LEFT ROTATION");
        BSTNode<T> tempNode = root.right;
        root.right = tempNode.left;
        tempNode.left = root;
        root = tempNode;

        getLeftAVL().adjustHeight();
        adjustHeight();
    }

    protected void rotateLeftRight() {
        System.out.println("Double Rotation...");
        getLeftAVL().rotateLeft();
        getLeftAVL().adjustHeight();
        this.rotateRight();
        this.adjustHeight();
    }

    protected void rotateRightLeft() {
        System.out.println("Double Rotation...");
        getRightAVL().rotateRight();
        getRightAVL().adjustHeight();
        this.rotateLeft();
        this.adjustHeight();
    }

    public class Queue<T> {
        private java.util.LinkedList<T> list = new java.util.LinkedList<>();

        public Queue() {
        }

        public void clear() {
            list.clear();
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }

        public T firstEl() {
            return list.getFirst();
        }

        public T dequeue() {
            return list.removeFirst();
        }

        public void enqueue(T el) {
            list.addLast(el);
        }

        public String toString() {
            return list.toString();
        }
    }

    public class Stack<T> {
        private java.util.ArrayList<T> pool = new java.util.ArrayList<>();

        public Stack() {
        }

        public Stack(int n) {
            pool.ensureCapacity(n);
        }

        public void clear() {
            pool.clear();
        }

        public boolean isEmpty() {
            return pool.isEmpty();
        }

        public T topEl() {
            if (isEmpty())
                throw new java.util.EmptyStackException();
            return pool.get(pool.size() - 1);
        }

        public T pop() {
            if (isEmpty())
                throw new java.util.EmptyStackException();
            return pool.remove(pool.size() - 1);
        }

        public void push(T el) {
            pool.add(el);
        }

        public String toString() {
            return pool.toString();
        }
    }

    public class TestAVL {
        public static void main(String[] args) {
            // Example which works with Left Rotation
            AVLTree<String> t = new AVLTree<>();
            for (int i = 1; i <= 5; i++)
                t.insertAVL("a" + i);
            t.breadthFirst();
            System.out.println();

            System.out.println("Exercise 2");
            int[] values = {8, 14, 12, 18, 23, 20, 15, 13, 7, 16};

            System.out.println("Exercise 3");
            // read the text file, insert unique words into the AVL tree and print its inorder traversal
        }
    }
}
