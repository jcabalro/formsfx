package com.dlsc.formsfx.view.controls;

import java.util.ArrayList;
import java.util.List;

import com.dlsc.formsfx.model.structure.Field;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class LabeledListRenderer<F extends Field<F>> extends SimpleControl<F> {

  protected Pane view;
  protected List<Control> controls = new ArrayList<>();
  protected Label fieldLabel;
  protected VBox box;

  private boolean compact = false;

  @Override
  public Pane getView() {
    return view;
  }

  @Override
  public void setField(F field) {
    view = compact ? new AnchorPane() : new GridPane();
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
    box = new VBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void layoutParts() {
    if (compact) {
      layoutPartsCompact();
    } else {
      layoutPartsGrid();
    }
  }

  public void layoutPartsCompact() {
    AnchorPane view = (AnchorPane) getView();
    Node labelDescription = field.getLabelDescription();
    Node valueDescription = field.getValueDescription();

  }

  public void layoutPartsGrid() {
    GridPane view = (GridPane) getView();
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
      view.add(box, 0, 1, columns, 1);
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
      view.add(box, 0, rowIndex++, columns, 1);
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
      view.add(box, 2, 0, columns - 2, 1);
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
    controls.stream().forEach(c -> getView().focusedProperty().addListener((v, o, n) -> toggleTooltip(box)));
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
