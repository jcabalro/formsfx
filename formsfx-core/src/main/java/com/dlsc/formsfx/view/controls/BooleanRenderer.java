package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.BooleanField;

import javafx.scene.control.CheckBox;

public class BooleanRenderer extends LabeledRenderer<BooleanField, CheckBox> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        super.initializeParts();
        getStyleClass().add("simple-boolean-control");
        control = new CheckBox();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();
        field.userInputProperty().addListener((observable, oldValue, newValue) -> control.setSelected(Boolean.parseBoolean(field.getUserInput())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupEventHandlers() {
        control.selectedProperty().addListener((ob, o, n) -> field.userInputProperty().setValue(String.valueOf(n)));
    }

}
