import java.util.ArrayList;
import java.util.List;

public class RBTree {
    private Node root;
    private int depth;

    public boolean add(int value) {
        if (root != null) {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.Black;
            return result;
        } else {
            root = new Node(value);
            root.color = Color.Black;
            return true;
        }
    }
    public boolean exists(int value) {
        if (root != null) {
            Node node = find(root, value);
            return node != null;
        }
        return false;
    }
    private Node find(Node node, int value) {
        if (node.value == value) return node;
        else {
            if (value > node.value && node.right != null) {
                return find(node.right, value);
            } else {
                if (node.left != null) return find(node.left, value);
            }
        }
        return null;
    }
    public void drawTree() {
        List<Node> line = new ArrayList<>();
        line.add(root);
        calculateDepth();
        while (line.size() > 0) {
            List<Node> nextLine = new ArrayList<>();
            for (int i = 0; i < line.size(); i++) {
                if (line.get(i).left != null) nextLine.add(line.get(i).left);
                if (line.get(i).right != null) nextLine.add(line.get(i).right);
                if (i == 0) {
                    for (int j = 0; j < powerOf2(depth - line.get(i).level + 1) - 2; j++) {
                        System.out.print("  ");
                    }
                }
                if (line.get(i).color.equals(Color.Red))
                {
                    System.out.print("r-" + line.get(i).value);
                    if (i != line.size() - 1) {
                        for (int j = 0; j < powerOf2(depth - line.get(i).level + 2) - 1; j++) {
                            System.out.print("  ");
                        }
                    }
                }

                else
                {
                    System.out.print("b-" + line.get(i).value);
                    if (i != line.size() - 1) {
                        for (int j = 0; j < powerOf2(depth - line.get(i).level + 2) - 1; j++) {
                            System.out.print("  ");
                        }
                    }
                }
            }
            System.out.println();
            line = nextLine;
        }
    }
    private void calculateDepth() {
        List<Node> line = new ArrayList<>();
        depth = 0;
        root.level = depth;
        line.add(root);
        while (line.size() > 0) {
            List<Node> nextLine = new ArrayList<>();
            depth++;
            for (Node node : line) {
                if (node.left != null) {
                    nextLine.add(node.left);
                    node.left.level = depth;
                }
                if (node.right != null) {
                    nextLine.add(node.right);
                    node.right.level = depth;
                }
            }
            line = nextLine;
        }
        depth--;
    }
    private int powerOf2(int pow){
        int result = 1;
        for (int i = 0; i < pow; i++) {
            result *= 2;
        }
        return result;
    }
    private Node altFind(int value) {
        List<Node> line = new ArrayList<>();
        line.add(root);
        while (line.size() > 0) {
            List<Node> nextLine = new ArrayList<>();
            for (Node node : line) {
                if (node.value == value) {
                    return node;
                }
                nextLine.add(node.left);
                nextLine.add(node.right);
            }
            line = nextLine;
        }
        return null;
    }
    private boolean addNode(Node node, int value) {
        if (node.value == value) {
            return false;
        } else {
            if (node.value > value) {
                if (node.left != null) {
                    boolean result = addNode(node.left, value);
                    node.left = rebalance(node.left);
                    return result;
                } else {
                    node.left = new Node(value);
                    node.left.color = Color.Red;
                    return true;
                }
            } else  {
                if (node.right != null) {
                    boolean result = addNode(node.right, value);
                    node.right = rebalance(node.right);
                    return result;
                } else {
                    node.right = new Node(value);
                    node.right.color = Color.Red;
                    return true;
                }
            }
        }
    }

    private Node rebalance(Node node){
        Node result = node;
        boolean notBalanced;
        do {
            notBalanced = false;
            if (result.right != null && result.right.color.equals(Color.Red) && (result.left == null || result.left.color.equals(Color.Black))) {
                notBalanced = true;
                result = rightSwap(result);
            }
            if (result.left != null && result.left.color.equals(Color.Red) && result.left.left != null && result.left.left.color.equals(Color.Red)) {
                notBalanced = true;
                result = leftSwap(result);
            }
            if (result.left != null && result.left.color.equals(Color.Red) && result.right != null && result.right.color.equals(Color.Red)) {
                notBalanced = true;
                colorSwap(result);
            }
        } while (notBalanced);
        return result;
    }
    private Node leftSwap(Node node){
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        newParent.color = node.color;
        node.color = Color.Red;
        return newParent;
    }
    private Node rightSwap(Node node){
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        newParent.color = node.color;
        node.color = Color.Red;
        return newParent;
    }
    private void colorSwap(Node node){
        node.left.color = Color.Black;
        node.right.color = Color.Black;
        node.color = Color.Red;
    }

    private class Node {
        private Node left;
        private Node right;
        private int value;
        private int level;
        private Color color;
        public Node(int value) {
            this.value = value;
        }
    }

    private enum Color {
        Black, Red
    }
}
