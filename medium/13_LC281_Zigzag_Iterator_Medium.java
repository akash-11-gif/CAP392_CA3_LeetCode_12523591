
import java.util.*;

public class ZigzagIterator {

    Queue<Iterator<Integer>> queue = new LinkedList<>();

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        if (!v1.isEmpty()) {
            queue.offer(v1.iterator());
        }

        if (!v2.isEmpty()) {
            queue.offer(v2.iterator());
        }
    }

    public int next() {
        Iterator<Integer> iterator = queue.poll();

        int value = iterator.next();

        if (iterator.hasNext()) {
            queue.offer(iterator);
        }

        return value;
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
