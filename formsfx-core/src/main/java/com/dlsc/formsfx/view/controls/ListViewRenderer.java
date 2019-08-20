package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.MultiSelectionField;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ListViewRenderer<V> extends LabeledRenderer<MultiSelectionField<V>, ListView<String>> {

    /**
     * The flag used for setting the selection properly.
     */
    protected boolean preventUpdate;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void initializeParts() {
        super.initializeParts();
        control = new ListView<>();
        getStyleClass().add("simple-listview-control");
        control.setItems((ObservableList<String>) field.getItems());
        control.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        for (int i = 0; i < field.getItems().size(); i++) {
            if (field.getSelection().contains(field.getItems().get(i))) {
            	control.getSelectionModel().select(i);
            } else {
            	control.getSelectionModel().clearSelection(i);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void layoutParts() {
        super.layoutParts();
        control.setPrefHeight(200);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();

        field.itemsProperty().addListener((ob, o, n) -> control.setItems((ObservableList<String>) field.getItems()));

        field.selectionProperty().addListener((observable, oldValue, newValue) -> {
            if (preventUpdate) {
                return;
            }

            preventUpdate = true;

            for (int i = 0; i < field.getItems().size(); i++) {
                if (field.getSelection().contains(field.getItems().get(i))) {
                    control.getSelectionModel().select(i);
                } else {
                	control.getSelectionModel().clearSelection(i);
                }
            }

            preventUpdate = false;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupEventHandlers() {

        control.getSelectionModel().getSelectedIndices().addListener((ListChangeListener<Integer>) c -> {
            if (preventUpdate) {
                return;
            }

            preventUpdate = true;

            for (int i = 0; i < control.getItems().size(); i++) {
                if (control.getSelectionModel().getSelectedIndices().contains(i)) {
                    field.select(i);
                } else {
                    field.deselect(i);
                }
            }

            preventUpdate = false;
        });
    }

}
