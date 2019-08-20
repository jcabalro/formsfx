package com.dlsc.formsfx.view.controls;

import java.util.ArrayList;
import java.util.List;

import com.dlsc.formsfx.model.structure.Field;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public abstract class LabeledListRenderer<F extends Field<F>> extends SimpleControl<F> {

	protected List<Control> controls = new ArrayList<>();
	protected Label fieldLabel;
	protected VBox box;

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
        box = new VBox();
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
            add(box, 0, 1, columns, 1);
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
            add(box, 0, rowIndex++, columns, 1);
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
            add(box, 2, 0, columns - 2, 1);
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
        controls.stream().forEach(c -> {
        	c.disableProperty().bind(field.editableProperty().not());
        	c.managedProperty().bind(c.visibleProperty());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();
		controls.stream().forEach(c -> focusedProperty().addListener((v, o, n) -> toggleTooltip(box)));
        field.errorMessagesProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(box));
        field.errorMessagesProperty().addListener((ob, o, n) -> toggleTooltip(box));
        field.tooltipProperty().addListener((ob, o, n) -> toggleTooltip(box));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupEventHandlers() {
        box.setOnMouseEntered(event -> toggleTooltip(box));
        box.setOnMouseExited(event -> toggleTooltip(box));
    }

}
