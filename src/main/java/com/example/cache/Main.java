package com.example.cache;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter cache capacity: ");
        int capacity = keyboard.nextInt();
        LRUCache cache = new LRUCache(capacity);

        while (true) {
            System.out.println("Enter operation: ");
            String[] commandLine = keyboard.nextLine().trim().split("\\s");
            String command = commandLine[0];
            if (command.isEmpty()) {
                continue;
            }
            switch (command) {
                case "get": {
                    int num = Integer.parseInt(commandLine[1]);
                    System.out.println(cache.get(num));
                    System.out.println(cache);
                    System.out.println("Head = "+cache.getHead());
                    System.out.println("Tail = "+cache.getTail());
                    break;
                }
                case "put": {
                    int key = Integer.parseInt(commandLine[1]);
                    int value = Integer.parseInt(commandLine[2]);
                    cache.put(key, value);
                    System.out.println(cache);
                    System.out.println("Head = "+cache.getHead());
                    System.out.println("Tail = "+cache.getTail());
                    break;
                }
                case "exit": {
                    return;
                }
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}