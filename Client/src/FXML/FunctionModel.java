package FXML;

import javafx.beans.property.SimpleBooleanProperty;

public class FunctionModel {
    private String name;
    private SimpleBooleanProperty selected;

    public FunctionModel(String name) {
        this.name = name;
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}