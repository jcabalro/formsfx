package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.DateField;

import javafx.scene.control.DatePicker;

public class DateRenderer extends LabeledRenderer<DateField, DatePicker> {

    @Override
    public void initializeParts() {
        super.initializeParts();
        control = new DatePicker();
        control.setEditable(true);
    }

    @Override
    public void setupBindings() {
        super.setupBindings();
        control.getEditor().textProperty().bindBidirectional(field.userInputProperty());
        control.promptTextProperty().bind(field.placeholderProperty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupEventHandlers() {
        control.getEditor().textProperty().addListener((ob, o, n) -> field.userInputProperty().setValue(String.valueOf(n)));
    }
}
