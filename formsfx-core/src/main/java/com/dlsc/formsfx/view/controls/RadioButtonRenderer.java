package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.SingleSelectionField;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class RadioButtonRenderer<V> extends LabeledListRenderer<SingleSelectionField<V>> {

  protected ToggleGroup toggleGroup;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initializeParts() {
    super.initializeParts();
    getStyleClass().add("simple-radio-control");
    toggleGroup = new ToggleGroup();
    createRadioButtons();
  }

  /**
   * This method creates radio buttons and adds them to radioButtons and is used
   * when the itemsProperty on the field changes.
   */
  protected void createRadioButtons() {
    box.getChildren().clear();
    controls.clear();
    for (int i = 0; i < field.getItems().size(); i++) {
      RadioButton rb = new RadioButton();
      rb.setText(field.getItems().get(i).toString());
      rb.setToggleGroup(toggleGroup);
      controls.add(rb);
    }
    if (field.getSelection() != null) {
      getSelected().setSelected(true);
    }
    box.getChildren().addAll(controls);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupValueChangedListeners() {
    super.setupValueChangedListeners();

    field.itemsProperty().addListener((ob, o, n) -> {
      createRadioButtons();
      setupBindings();
      setupValueChangedListeners();
    });

    field.selectionProperty().addListener((ob, o, n) -> {
      if (field.getSelection() != null) {
        getSelected().setSelected(true);
      } else {
        toggleGroup.getSelectedToggle().setSelected(false);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupEventHandlers() {
    super.setupEventHandlers();
    for (int i = 0; i < controls.size(); i++) {
      final int j = i;
      ((RadioButton) controls.get(j)).setOnAction(event -> field.select(j));
    }
  }

  public RadioButton getSelected() {
    return (RadioButton) controls.get(field.getItems().indexOf(field.getSelection()));
  }
}
