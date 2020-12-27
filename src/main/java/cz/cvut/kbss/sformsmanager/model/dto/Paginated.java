package cz.cvut.kbss.sformsmanager.model.dto;

import java.util.List;

public class Paginated<T> {

    private int totalItems;
    private int offset;
    private int limit;
    private List<T> items;

    public Paginated(int totalItems, int offset, int limit, List<T> items) {
        this.totalItems = totalItems;
        this.offset = offset;
        this.limit = limit;
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
