package com.dlsc.formsfx.view.renderer;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.view.util.ViewMixin;

public interface FieldRenderer<F extends Field<?>> extends ViewMixin {

	void setField(F field);
}
