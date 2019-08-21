package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.Field;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public abstract class LabeledRenderer<F extends Field<F>, C extends Control> extends SimpleControl<F> {

  protected C control;
  protected Label fieldLabel;
  protected GridPane view;

  @Override
  public GridPane getView() {
    return view;
  }

  @Override
  public void setField(F field) {
    view = new GridPane();
    super.setField(field);
  }

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
    GridPane view = getView();
    Node labelDescription = field.getLabelDescription();
    Node valueDescription = field.getValueDescription();

    view.setAlignment(Pos.CENTER_LEFT);

    int columns = field.getSpan();

    for (int i = 0; i < columns; i++) {
      ColumnConstraints colConst = new ColumnConstraints();
      colConst.setPercentWidth(100.0 / columns);
      view.getColumnConstraints().add(colConst);
    }

    if (fieldLabel == null) {
      view.add(control, 0, 1, columns, 1);
      if (valueDescription != null) {
        GridPane.setValignment(valueDescription, VPos.TOP);
        view.add(valueDescription, 0, 2, columns, 1);
      }
    } else if (field.getLabelPos() == Pos.TOP_LEFT) {
      int rowIndex = 0;
      view.add(fieldLabel, 0, rowIndex++, columns, 1);
      if (labelDescription != null) {
        GridPane.setValignment(labelDescription, VPos.TOP);
        view.add(labelDescription, 0, rowIndex++, columns, 1);
      }
      view.add(control, 0, rowIndex++, columns, 1);
      if (valueDescription != null) {
        GridPane.setValignment(valueDescription, VPos.TOP);
        view.add(valueDescription, 0, rowIndex, columns, 1);
      }
    } else if (field.getLabelPos() == Pos.CENTER_LEFT) {
      view.add(fieldLabel, 0, 0, 2, 1);
      if (labelDescription != null) {
        GridPane.setValignment(labelDescription, VPos.TOP);
        view.add(labelDescription, 0, 1, 2, 1);
      }
      view.add(control, 2, 0, columns - 2, 1);
      if (valueDescription != null) {
        GridPane.setValignment(valueDescription, VPos.TOP);
        view.add(valueDescription, 2, 1, columns - 2, 1);
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
