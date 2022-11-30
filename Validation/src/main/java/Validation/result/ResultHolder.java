package Validation.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ResultHolder<T> {
    private final Map<T, Element<T>> elements = new HashMap<>();

    private int count = 0;

    public ResultHolder() {}

    public void addRepresentative(T representative) {
        Element<T> newElement = new Element<>(representative, representative, ++ count);
        elements.put(representative, newElement);
    }

    public void addElement(T element, T representative) {
        Element<T> newElement = new Element<>(element, representative, ++ count);
        elements.put(element, newElement);
    }

    public List<List<T>> disjointLists() {
        Map<T, Set<T>> tempMap = new HashMap<>();
        elements.forEach((t, e) -> {
            T rep = getRepresentativeOf(t);
            tempMap.computeIfAbsent(rep, r -> new HashSet<>()).add(t);
        });
        List<List<T>> ret = new ArrayList<>();
        tempMap.forEach((t, s) -> ret.add(new ArrayList<>(s)));
        return ret;
    }

    public boolean hasElement(T element) {
        return elements.containsKey(element);
    }

    public Stream<T> representatives() {
        Set<T> reps = new HashSet<>();
        elements.forEach((e, unused) -> reps.add(getRepresentativeOf(e)));
        return reps.stream();
    }

    public List<T> getRepresentatives() {
        return representatives().toList();
    }

    public Stream<T> elements() {
        return elements.keySet().stream();
    }

    public T getRepresentativeOf(T program) {
        Element<T> element = elements.get(program);
        T rep = element.getRepresentative();
        if (rep != program) {
            T temp = getRepresentativeOf(rep);
            element.setRepresentative(temp);
        }
        return element.getRepresentative();
    }

    public void union(T element1, T element2) {
        T rep1 = getRepresentativeOf(element1);
        T rep2 = getRepresentativeOf(element2);
        Element<T> element = elements.get(rep2);
        element.setRepresentative(rep1);
    }

    public int getOrderOf(T program) {
        return elements.get(program).getOrder();
    }

    public void merge(ResultHolder<T> other) {
        elements.putAll(other.elements);
    }

}
