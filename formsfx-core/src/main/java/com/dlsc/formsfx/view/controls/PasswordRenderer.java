package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.PasswordField;

public class PasswordRenderer extends LabeledRenderer<PasswordField, javafx.scene.control.PasswordField>{

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        super.initializeParts();
        getStyleClass().add("simple-password-control");
        control =  new javafx.scene.control.PasswordField();
        control.setPromptText(field.placeholderProperty().getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupBindings() {
        super.setupBindings();
        control.textProperty().bindBidirectional(field.userInputProperty());
        control.promptTextProperty().bind(field.placeholderProperty());
    }
}
