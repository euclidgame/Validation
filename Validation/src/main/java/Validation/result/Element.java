package Validation.result;

public class Element<T> {

    private final T element;

    private T representative;

    private final int order;

    public Element(T element, T representative, int order) {
        this.element = element;
        this.representative = representative;
        this.order = order;
    }

    public T getElement() {
        return element;
    }

    public void setRepresentative(T representative) {
        this.representative = representative;
    }

    public T getRepresentative() {
        return representative;
    }

    public int getOrder() {
        return order;
    }

}
