/*
 * Copyright 2015 Digitising Scotland project:
 * <http://digitisingscotland.cs.st-andrews.ac.uk/>
 *
 * This file is part of the module record_classification.
 *
 * record_classification is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * record_classification is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with record_classification. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package old.record_classification_old.datastructures.code;

/**
 * Thrown when a trying to instantiate a code with a string representation of that code which is
 * not valid.
 * 
 * Non valid codes are either null or do not conform to the type of that code.
 * 
 * Example, {@link OccCode}s are all numeric. CoD codes must start with a letter.
 * @author jkc25
 *
 */
public class CodeNotValidException extends Exception {

    /**
     * Generated serialVesionUID.
     */
    private static final long serialVersionUID = -363132943300791654L;

    /**
     * Thrown when a code is not valid for its type.
     * @param errorMessage Message to report to user.
     */
    public CodeNotValidException(final String errorMessage) {

        super(errorMessage);
        System.err.println(errorMessage);

    }
}
