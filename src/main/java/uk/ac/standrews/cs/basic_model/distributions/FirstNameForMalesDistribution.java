/*
 * Copyright 2014 Digitising Scotland project:
 * <http://digitisingscotland.cs.st-andrews.ac.uk/>
 *
 * This file is part of the module population_model.
 *
 * population_model is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * population_model is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with population_model. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package uk.ac.standrews.cs.basic_model.distributions;

import uk.ac.standrews.cs.basic_model.config.PopulationProperties;
import uk.ac.standrews.cs.basic_model.distributions.general.FileBasedEnumeratedDistribution;
import uk.ac.standrews.cs.basic_model.distributions.general.InconsistentWeightException;

import java.io.IOException;
import java.util.Random;

/**
 * Provides a distribution of male first names.
 * 
 * @author Alan Dearle (alan.dearle@st-andrews.ac.uk)
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 */
public class FirstNameForMalesDistribution extends FileBasedEnumeratedDistribution {

    private static final String MALE_FIRST_NAME_DISTRIBUTION_KEY = "male_first_name_distribution_filename";

    /**
     * Creates a distribution of male first names.
     * 
     * @param random Takes in random for use in creation of distribution.
     * @throws IOException Thrown in the event of an IOException.
     * @throws InconsistentWeightException Thrown when the weights in the underlying distribution are found to be inconsistent.
     */
    public FirstNameForMalesDistribution(final Random random) throws IOException, InconsistentWeightException {

        super(PopulationProperties.getProperties().getProperty(MALE_FIRST_NAME_DISTRIBUTION_KEY), random);
    }
}
