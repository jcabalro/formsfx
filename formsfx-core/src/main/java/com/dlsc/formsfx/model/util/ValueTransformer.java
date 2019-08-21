package com.dlsc.formsfx.model.util;

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

/**
 * A value transformer takes a string as an input and returns a parsed type.
 *
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 * @deprecated Use {@code StringConverter} instead.
 * @see javafx.util.StringConverter
 */
@Deprecated
public interface ValueTransformer<T> {

  T transform(String input);

}
