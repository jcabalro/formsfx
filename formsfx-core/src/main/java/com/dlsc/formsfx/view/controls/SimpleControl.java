package com.dlsc.formsfx.view.controls;

/*-
 * ========================LICENSE_START=================================
 * FormsFX
 * %%
 * Copyright (C) 2017 DLSC Software & Consulting
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.view.renderer.FieldRenderer;

import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * This class provides a base for general purpose FormsFX controls.
 *
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
public abstract class SimpleControl<F extends Field<F>> implements FieldRenderer<F> {

  /**
   * This is the Field that is used for binding and update styling changes.
   */
  protected F field;

  /**
   * Tooltip to hold the error message.
   */
  protected Tooltip tooltip;

  /**
   * Pseudo classes for state changes.
   */
  protected static final PseudoClass REQUIRED_CLASS = PseudoClass.getPseudoClass("required");
  protected static final PseudoClass INVALID_CLASS = PseudoClass.getPseudoClass("invalid");
  protected static final PseudoClass CHANGED_CLASS = PseudoClass.getPseudoClass("changed");
  protected static final PseudoClass DISABLED_CLASS = PseudoClass.getPseudoClass("disabled");

  @Override
  public void setField(F field) {
    if (this.field != null) {
      throw new IllegalStateException("Cannot change a control's field once set.");
    }

    this.field = field;
    init();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initializeParts() {
    getStyleClass().add("simple-control");

    tooltip = new Tooltip();
    tooltip.getStyleClass().add("simple-tooltip");

    getStyleClass().addAll(field.getStyleClass());

    updateStyle(INVALID_CLASS, !field.isValid());
    updateStyle(REQUIRED_CLASS, field.isRequired());
    updateStyle(CHANGED_CLASS, field.hasChanged());
    updateStyle(DISABLED_CLASS, !field.isEditable());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupBindings() {
    getView().idProperty().bind(field.idProperty());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupValueChangedListeners() {
    field.validProperty().addListener((observable, oldValue, newValue) -> updateStyle(INVALID_CLASS, !newValue));
    field.requiredProperty().addListener((observable, oldValue, newValue) -> updateStyle(REQUIRED_CLASS, newValue));
    field.changedProperty().addListener((observable, oldValue, newValue) -> updateStyle(CHANGED_CLASS, newValue));
    field.editableProperty().addListener((observable, oldValue, newValue) -> updateStyle(DISABLED_CLASS, !newValue));

    field.getStyleClass().addListener((ListChangeListener<String>) c -> {
      while (c.next()) {
        if (c.wasRemoved()) {
          getStyleClass().removeAll(c.getRemoved());
        }

        if (c.wasAdded()) {
          getStyleClass().addAll(c.getAddedSubList());
        }
      }
    });
  }

  /**
   * Sets the error message as tooltip for the matching control and shows them
   * below the same control.
   *
   * @param reference The control which gets the tooltip.
   */
  protected void toggleTooltip(Region reference) {
    this.toggleTooltip(reference, reference);
  }

  /**
   * Sets the error message as tooltip for the matching control.
   *
   * @param below     The control needed for positioning the tooltip.
   * @param reference The control which gets the tooltip.
   */
  protected void toggleTooltip(Node reference, Region below) {
    String fieldTooltip = field.getTooltip();

    if ((reference.isFocused() || reference.isHover())
        && (!fieldTooltip.equals("") || field.getErrorMessages().size() > 0)) {
      tooltip
          .setText((!fieldTooltip.equals("") ? fieldTooltip + "\n" : "") + String.join("\n", field.getErrorMessages()));

      if (tooltip.isShowing()) {
        return;
      }

      Point2D p = below.localToScene(0.0, 0.0);
      Pane view = getView();
      tooltip.show(
          view.getScene().getWindow(),
          p.getX() + view.getScene().getX() + view.getScene().getWindow().getX(),
          p.getY() + view.getScene().getY() + view.getScene().getWindow().getY() + below.getHeight() + 5);
    } else {
      tooltip.hide();
    }
  }

  /**
   * Sets the css style for the defined properties.
   *
   * @param pseudo   The CSS pseudo class to toggle.
   * @param newValue Determines whether the CSS class should be applied.
   */
  protected void updateStyle(PseudoClass pseudo, boolean newValue) {
    getView().pseudoClassStateChanged(pseudo, newValue);
  }

  /**
   * Adds a style class to the control.
   *
   * @param name of the style class to be added to the control
   */
  public void addStyleClass(String name) {
    getStyleClass().add(name);
  }

  /**
   * Removes a style class from the control.
   *
   * @param name of the class to be removed from the control
   */
  public void removeStyleClass(String name) {
    getStyleClass().remove(name);
  }
}
