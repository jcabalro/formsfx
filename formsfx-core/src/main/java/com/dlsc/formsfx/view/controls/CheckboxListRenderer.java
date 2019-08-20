package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.MultiSelectionField;

import javafx.scene.control.CheckBox;

public class CheckboxListRenderer<V> extends LabeledListRenderer<MultiSelectionField<V>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        super.initializeParts();
        getStyleClass().add("simple-checkbox-control");
        createCheckboxes();
    }

    private void createCheckboxes() {
        box.getChildren().clear();
        controls.clear();
        field.getItems().stream().forEach(item -> {
        	CheckBox cb = new CheckBox();
            cb.setText(item.toString());
            cb.setSelected(field.getSelection().contains(item));
            controls.add(cb);
        });
        box.getChildren().addAll(controls);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();

        field.itemsProperty().addListener((ob, o, n) -> {
            createCheckboxes();
            setupBindings();
            setupValueChangedListeners();
        });

        field.selectionProperty().addListener((ob, o, n) -> {
            for (int i = 0; i < controls.size(); i++) {
            	get(i).setSelected(field.getSelection().contains(field.getItems().get(i)));
            }
        });
    }

    @Override
    public void setupEventHandlers() {
    	super.setupEventHandlers();
        for (int i = 0; i < controls.size(); i++) {
            final int j = i;
            get(i).setOnAction(event -> {
                if (get(j).isSelected()) {
                    field.select(j);
                } else {
                    field.deselect(j);
                }
            });
        }
    }

    public CheckBox get(int i) {
    	return (CheckBox) controls.get(i);
    }
}
