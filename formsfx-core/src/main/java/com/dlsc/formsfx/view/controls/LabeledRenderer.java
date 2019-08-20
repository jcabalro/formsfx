package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.Field;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public abstract class LabeledRenderer<F extends Field<F>, C extends Control> extends SimpleControl<F> {

	protected C control;
	protected Label fieldLabel;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParts() {
        super.initializeParts();
        String label = field.getLabel();
        if (label != null) {
        	fieldLabel = new Label(label);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void layoutParts() {
        super.layoutParts();

        Node labelDescription = field.getLabelDescription();
        Node valueDescription = field.getValueDescription();

        int columns = field.getSpan();

        if (fieldLabel == null) {
            add(control, 0, 1, columns, 1);
            if (valueDescription != null) {
                GridPane.setValignment(valueDescription, VPos.TOP);
                add(valueDescription, 0, 2, columns, 1);
            }
        } else if (field.getLabelPos() == Pos.TOP_LEFT) {
            int rowIndex = 0;
            add(fieldLabel, 0, rowIndex++, columns, 1);
            if (labelDescription != null) {
                GridPane.setValignment(labelDescription, VPos.TOP);
                add(labelDescription, 0, rowIndex++, columns, 1);
            }
            add(control, 0, rowIndex++, columns, 1);
            if (valueDescription != null) {
                GridPane.setValignment(valueDescription, VPos.TOP);
                add(valueDescription, 0, rowIndex, columns, 1);
            }
        } else if (field.getLabelPos() == Pos.CENTER_LEFT) {
            add(fieldLabel, 0, 0, 2, 1);
            if (labelDescription != null) {
                GridPane.setValignment(labelDescription, VPos.TOP);
                add(labelDescription, 0, 1, 2, 1);
            }
            add(control, 2, 0, columns - 2, 1);
            if (valueDescription != null) {
                GridPane.setValignment(valueDescription, VPos.TOP);
                add(valueDescription, 2, 1, columns - 2, 1);
            }
        } else {
        	throw new UnsupportedOperationException(field.getLabelPos() + " not supported yet.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupBindings() {
        super.setupBindings();
        fieldLabel.textProperty().bind(field.labelProperty());
        control.managedProperty().bind(control.visibleProperty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();
		field.editableProperty().addListener((v, o, n) -> control.setDisable(!n));
		control.focusedProperty().addListener((v, o, n) -> toggleTooltip(control));
        field.errorMessagesProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(control));
        control.setOnMouseEntered(event -> toggleTooltip(control));
        control.setOnMouseExited(event -> toggleTooltip(control));
        field.errorMessagesProperty().addListener((ob, o, n) -> toggleTooltip(control));
        field.tooltipProperty().addListener((ob, o, n) -> toggleTooltip(control));
    }

}
