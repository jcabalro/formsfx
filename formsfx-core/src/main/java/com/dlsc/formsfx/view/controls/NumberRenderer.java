package com.dlsc.formsfx.view.controls;

import com.dlsc.formsfx.model.structure.NumberField;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class NumberRenderer extends LabeledRenderer<NumberField, Spinner<Number>> {

  @Override
  public void initializeParts() {
    super.initializeParts();
    control = new Spinner<>();
    control.setEditable(true);
    if (field.getType().equals(Integer.class)) {
      control.setValueFactory(new IntegerValueFactory());
    } else if (field.getType().equals(Double.class)) {
      control.setValueFactory(new DoubleValueFactory());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupBindings() {
    super.setupBindings();
    control.getEditor().textProperty().bindBidirectional(field.userInputProperty());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupEventHandlers() {
    super.setupEventHandlers();
    control.getEditor().setOnKeyPressed(event -> {
      switch (event.getCode()) {
      case UP:
        control.increment(1);
        break;
      case DOWN:
        control.decrement(1);
        break;
      default:
        break;
      }
    });
  }

  public class DoubleValueFactory extends SpinnerValueFactory<Number> {

    DoubleSpinnerValueFactory vf;

    public DoubleValueFactory() {
      vf = new SpinnerValueFactory.DoubleSpinnerValueFactory((Double) field.getMin(), (Double) field.getMax(),
          (Double) field.getValue());
      vf.maxProperty().bind(field.maxProperty());
      vf.minProperty().bind(field.minProperty());
    }

    @Override
    public void decrement(int steps) {
      vf.decrement(steps);
      setValue(vf.getValue());
    }

    @Override
    public void increment(int steps) {
      vf.increment(steps);
      setValue(vf.getValue());
    }
  }

  public class IntegerValueFactory extends SpinnerValueFactory<Number> {

    IntegerSpinnerValueFactory vf;

    public IntegerValueFactory() {
      vf = new SpinnerValueFactory.IntegerSpinnerValueFactory((Integer) field.getMin(), (Integer) field.getMax(),
          (Integer) field.getValue());
      vf.maxProperty().bind(field.maxProperty());
      vf.minProperty().bind(field.minProperty());
    }

    @Override
    public void decrement(int steps) {
      vf.decrement(steps);
      setValue(vf.getValue());
    }

    @Override
    public void increment(int steps) {
      vf.increment(steps);
      setValue(vf.getValue());
    }
  }
}
