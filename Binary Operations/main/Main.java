package main;

import operation.*;

import java.util.Scanner;

public class Main {
    private static Operation getOperation(String operation) throws IllegalArgumentException {
        if (operation.equalsIgnoreCase("and")) {
            return new AndOperation();
        } else if (operation.equalsIgnoreCase("or")) {
            return new OrOperation();
        } else if (operation.equalsIgnoreCase("xor")) {
            return new XorOperation();
        } else {
            throw new IllegalArgumentException("Operation must be XOR|OR|AND");
        }
    }

    public static void main(String[] args) {
        if (args.length > 2 || args.length < 1) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }

        String firstPath = args[0];
        String secondPath = args[1];

        Scanner scanner = new Scanner(System.in);

        System.out.print("Select the operation: ");
        Operation operation = getOperation(scanner.nextLine());

        System.out.print("Select the number of threads: ");
        int threads = scanner.nextInt();

        scanner.close();

        new ImageProcessor(firstPath, secondPath, operation, threads).launchTasks();
    }
}