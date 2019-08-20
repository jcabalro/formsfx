package com.dlsc.formsfx.view.util;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * This interface defines lifecycle of a FormsFX view.
 *
 * @author Dieter Holz
 */
public interface ViewMixin {

  Pane getView();

  /**
   * This method calls all the other methods, so that it can be initialized
   * easier.
   */
  default void init() {
    initializeSelf();
    initializeParts();
    layoutParts();
    setupEventHandlers();
    setupValueChangedListeners();
    setupBindings();
  }

  /**
   * This method can be used to initialize the parts of the same class.
   */
  default void initializeSelf() {
  }

  /**
   * This method is used to initializes all the properties of a class.
   */
  void initializeParts();

  /**
   * This method is used to align the parts of a class.
   */
  void layoutParts();

  /**
   * This method is used to set up event handlers.
   */
  default void setupEventHandlers() {
  }

  /**
   * This method is used to set up value change listeners.
   */
  default void setupValueChangedListeners() {
  }

  /**
   * This method is used to configure the bindings of the properties.
   */
  default void setupBindings() {
  }

  /**
   * This method holds a list of children.
   *
   * @return List of stylesheets.
   */
  default ObservableList<Node> getChildren() {
    return getView().getChildren();
  }

  /**
   * This method holds a list of stylesheets.
   *
   * @return List of stylesheets.
   */
  default ObservableList<String> getStylesheets() {
    return getView().getStylesheets();
  }

  /**
   * This method holds a list of style classes.
   *
   * @return List of stylesheets.
   */
  default ObservableList<String> getStyleClass() {
    return getView().getStyleClass();
  }

  /**
   * This method adds the stylesheet files to the getStylesheets method.
   *
   * @param stylesheetFile List of stylesheet files
   */
  default void addStylesheetFiles(String... stylesheetFile) {
    for (String file : stylesheetFile) {
      String stylesheet = getClass().getResource(file).toExternalForm();
      getView().getStylesheets().add(stylesheet);
    }
  }

}
