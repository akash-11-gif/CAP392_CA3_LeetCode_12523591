import java.util.*;

class LFUCache {
    class Node {
        int key, value, frequency;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            frequency = 1;
        }
    }

    class DoublyLinkedList {
        Node head, tail;
        int size = 0;

        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        void add(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        Node removeLast() {
            if (size == 0) return null;
            Node node = tail.prev;
            remove(node);
            return node;
        }
    }

    Map<Integer, Node> cache = new HashMap<>();
    Map<Integer, DoublyLinkedList> frequencyMap = new HashMap<>();
    int capacity, minFrequency = 0;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) return -1;

        Node node = cache.get(key);
        updateFrequency(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            updateFrequency(node);
            return;
        }

        if (cache.size() == capacity) {
            DoublyLinkedList list = frequencyMap.get(minFrequency);
            Node removed = list.removeLast();
            cache.remove(removed.key);
        }

        Node node = new Node(key, value);
        cache.put(key, node);

        frequencyMap.putIfAbsent(1, new DoublyLinkedList());
        frequencyMap.get(1).add(node);
        minFrequency = 1;
    }

    private void updateFrequency(Node node) {
        int frequency = node.frequency;
        DoublyLinkedList oldList = frequencyMap.get(frequency);
        oldList.remove(node);

        if (frequency == minFrequency && oldList.size == 0) {
            minFrequency++;
        }

        node.frequency++;
        frequencyMap.putIfAbsent(node.frequency, new DoublyLinkedList());
        frequencyMap.get(node.frequency).add(node);
    }
}
