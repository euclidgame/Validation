package Validation.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ResultHolder<T> {

    private final List<Element<T>> representatives = new ArrayList<>();

    private final Map<T, Element<T>> elements = new HashMap<>();

    private int count = 0;

    public ResultHolder() {}

    public void addRepresentative(T representative) {
        Element<T> newElement = new Element<>(representative, representative, ++ count);
        elements.put(representative, newElement);
        representatives.add(newElement);
    }

    public void addElement(T element, T representative) {
        Element<T> newElement = new Element<>(element, representative, ++ count);
        elements.put(element, newElement);
    }

    public Stream<T> representatives() {
        return representatives.stream().map(Element::getElement);
    }

    public List<T> getRepresentatives() {
        List<T> ret = new ArrayList<>();
        representatives.forEach(r -> ret.add(r.getElement()));
        return ret;
    }

    public Stream<T> elements() {
        return elements.keySet().stream();
    }

    public T getRepresentativeOf(T program) {
        return elements.get(program).getRepresentative();
    }

    public int getOrderOf(T program) {
        return elements.get(program).getOrder();
    }

}
