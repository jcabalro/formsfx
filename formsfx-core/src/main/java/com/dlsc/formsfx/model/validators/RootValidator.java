package com.dlsc.formsfx.model.validators;

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
 * The RootValidator contains helper methods for concrete validator
 * implementations.
 *
 * @author Sacha Schmid
 * @author Rinesch Murugathas
 */
abstract class RootValidator<T> implements Validator<T> {

  private String errorMessage;

  protected RootValidator(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Creates a {@link ValidationResult} based on the validation result.
   *
   * @param result The result of the validation.
   *
   * @return Returns a new ValidationResult containing result and message.
   */
  protected ValidationResult createResult(boolean result) {
    return new ValidationResult(result, errorMessage);
  }

}
