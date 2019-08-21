package com.dlsc.formsfx.model.structure;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class NumberField extends DataField<Property<Number>, Number, NumberField> {

  private Class<? extends Number> type;
  private SimpleObjectProperty<Number> max = new SimpleObjectProperty<Number>(this, "max");
  private SimpleObjectProperty<Number> min = new SimpleObjectProperty<Number>(this, "min");

  protected NumberField(Class<? extends Number> type, Property<Number> valueProperty,
      Property<Number> persistentValueProperty) {
    super(valueProperty, persistentValueProperty);
    this.type = type;
    if (Double.class.equals(type)) {
      max.set(Double.MAX_VALUE);
      min.set(-Double.MAX_VALUE);
      stringConverter = new AbstractStringConverter<Number>() {
        @Override
        public Number fromString(String string) {
          return Double.parseDouble(string);
        }
      };
    } else if (Integer.class.equals(type)) {
      max.set(Integer.MAX_VALUE);
      min.set(Integer.MIN_VALUE);
      stringConverter = new AbstractStringConverter<Number>() {
        @Override
        public Number fromString(String string) {
          return Integer.parseInt(string);
        }
      };
    } else {
      throw new UnsupportedOperationException("Number fields only accept: Integer.class and Double.class");
    }
    userInput.set(stringConverter.toString(value.getValue()));
  }

  public Class<? extends Number> getType() {
    return type;
  }

  public final SimpleObjectProperty<Number> maxProperty() {
    return this.max;
  }

  public final Number getMax() {
    return this.maxProperty().get();
  }

  public final void setMax(final Number max) {
    this.maxProperty().set(max);
  }

  public final SimpleObjectProperty<Number> minProperty() {
    return this.min;
  }

  public final Number getMin() {
    return this.minProperty().get();
  }

  public final void setMin(final Number min) {
    this.minProperty().set(min);
  }

}
