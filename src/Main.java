import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RBTree tree = new RBTree();
        boolean flag = true;
        while (flag) {
            System.out.println("Введите gen, чтобы сгенерировать дерево определенного размера");
            System.out.println("Введите add, чтобы добавить элемент в дерево");
            System.out.println("Введите show, чтобы отобразить дерево");
            System.out.println("Введите exit, чтобы выйти из программы");
            String choice = scanner.nextLine();
            if (choice.equals("gen")) {
                System.out.println("Введите размер дерева");
                int size = Integer.parseInt(scanner.nextLine());
                generateTree(tree, size);
            }
            if (choice.equals("add")) {
                System.out.println("Введите число");
                tree.add(Integer.parseInt(scanner.nextLine()));
                tree.drawTree();
            }
            if (choice.equals("show")){
                tree.drawTree();
            }
            if (choice.equals("exit")) {
                flag = false;
            }
        }
        scanner.close();
    }
    public static void generateTree(RBTree tree, int size) {
        for (int i = 1; i <= size; i++) {
            tree.add(i);
        }
    }
}