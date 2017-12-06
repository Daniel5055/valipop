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
package uk.ac.standrews.cs.basic_model.distributions.general;

import org.apache.commons.math3.random.RandomGenerator;
import uk.ac.standrews.cs.basic_model.distributions.util.FileDistributionGenerator;
import uk.ac.standrews.cs.utilities.FileManipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A distribution of strings specified in a file mapping Strings to probabilities.
 *
 * @author Alan Dearle (alan.dearle@st-andrews.ac.uk)
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 */
public class FileBasedEnumeratedDistribution extends EnumeratedDistribution {

    private static final String TAB = "\t";

    /**
     * Creates a File Based Enumerated distribution.
     *
     * @param path_string Path of file.
     * @param random      Takes in random for use in creation of distribution.
     * @throws IOException                 Thrown in the event of an IOException.
     * @throws InconsistentWeightException Thrown when the weights in the underlying distribution are found to be inconsistent.
     */
    public FileBasedEnumeratedDistribution(final String path_string, final RandomGenerator random) throws IOException, InconsistentWeightException {

        super(random);

        final Map<String, Double> item_probabilities = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path_string), FileManipulation.FILE_CHARSET))) {

            String line = reader.readLine();
            if (line != null && line.startsWith(FileDistributionGenerator.COMMENT_INDICATOR)) {
                line = reader.readLine();
            }

            while (line != null) {

                final String[] strings = line.split(TAB);

                double probability = Double.parseDouble(strings[1]);
                item_probabilities.put(strings[0], probability);
                line = reader.readLine();
            }
        }

        configureProbabilities(item_probabilities);
    }
}
