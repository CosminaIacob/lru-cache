package com.example.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.enjoyalgorithms.com/blog/implement-least-recently-used-cache
 * <p>
 * The Least Recently Used (LRU) Cache is a popular caching strategy
 * that discards the least recently used items first
 * to make room for new elements when the cache is full.
 * It organizes items int the order of their use,
 * allowing us to easily identify items that have not been used for a long time.
 * This strategy is useful for optimizing the use of limited cache space
 * and improving the performance of caching systems.
 * <p>
 * Access time for any item should be O(1).
 * Time required to get the least frequently used element should be O(1).
 * Time required to put any item should be O(1).
 * The space required should be O(n).
 * <p>
 * Retrieving data from a  computer's memory is an expensive task.
 * A high-speed memory known as cache memory is used to avoid accessing data from memory repeatedly.
 * A cache holds frequently requested data and instructions to be immediately available to the CPU.
 * Cache memory reduces the average time for accessing data from the main memory.
 * The cache memory size is generally much smaller than the main memory.
 * So we cannot fit everything from the main memory into the cache.
 * The main idea of the LRU cache is to store the n recently accessed elements.
 * <p>
 * Solution: a combination if a doubly-linked list and a hash map.
 * The doubly-linked list allows us to store the elements in a specific order (with th eLRU element at the bottom)
 * and move any element to the top in constant time.
 * Accessing an element in the doubly-linked list would take O(n) time.
 * To address this, we use a hash map to map the elements to doubly-linked list nodes,
 * allowing us to access an element in the doubly-linked list in O(1) time.
 * <p>
 * When inserting new data into the cache, we insert it into the head of the linked list and record it in the map.
 * After each cache hit, we mve the accessed data (nodes) to the head of the linked list.
 * When the linked list is full, we discard the tail of the linked list
 * and delete the corresponding map key to make room for the new element.
 */
public class LRUCache {

    private final int capacity;
    private Map<Integer, Node> cache;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
    }

    private void addNodeToHead(Node node) {
        node.setPrevious(null);
        node.setNext(head);

        if (head != null) {
            head.setPrevious(node);
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    private void removeNode(Node node) {
        Node previousNode = node.getPrevious();
        Node nextNode = node.getNext();

        if (previousNode != null) {
            previousNode.setNext(nextNode);
        } else {
            head = nextNode;
        }
        if (nextNode != null) {
            nextNode.setPrevious(previousNode);
        } else {
            tail = previousNode;
        }
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addNodeToHead(node);
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            moveToHead(node);
            return node.getValue();
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.setValue(value);
            moveToHead(node);
            return;
        }

        Node node = new Node(key, value);
        if (cache.size() == capacity) {
            cache.remove(tail.getKey());
            removeNode(tail);
        }

        cache.put(key, node);
        addNodeToHead(node);
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        cache.forEach((key, value) -> stringBuilder.append("[").append(key)
                .append(", Node[")
                .append(value.getKey())
                .append(", ")
                .append(value.getValue())
                .append("]\n"));
        return stringBuilder.toString();
    }
}
