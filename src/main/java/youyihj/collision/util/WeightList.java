package youyihj.collision.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
public class WeightList<E> {
    private final List<Entry<E>> elements;
    private List<Integer> nodeList;
    private int bound = 0;

    private WeightList(List<Entry<E>> elements) {
        this.elements = elements;
        genNodeList();
    }

    public E get(Random random) {
        int seed = random.nextInt(bound);
        int i = 0;
        for (int m : nodeList) {
            if (seed < m) {
                return elements.get(i).element;
            }
            i++;
        }
        throw new NoSuchElementException();
    }

    public void add(E element, int weight) {
        elements.add(new Entry<>(element, weight));
        genNodeList();
    }

    public static <E> WeightList<E> create(Map<E, Integer> map) {
        return new WeightList<>(map.entrySet().stream().map(Entry::new).collect(Collectors.toList()));
    }

    private void genNodeList() {
        List<Integer> temp = new ArrayList<>();
        elements.forEach(element -> {
            bound += element.weight;
            temp.add(bound);
        });
        nodeList = temp;
    }

    private static class Entry<E> {
        private final E element;
        private final int weight;

        public Entry(E element, int weight) {
            this.element = element;
            this.weight = weight;
        }

        public Entry(Map.Entry<E, Integer> eIntegerEntry) {
            this(eIntegerEntry.getKey(), eIntegerEntry.getValue());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry<?> entry = (Entry<?>) o;
            return weight == entry.weight &&
                    Objects.equals(element, entry.element);
        }

        @Override
        public int hashCode() {
            return Objects.hash(element, weight);
        }
    }
}
