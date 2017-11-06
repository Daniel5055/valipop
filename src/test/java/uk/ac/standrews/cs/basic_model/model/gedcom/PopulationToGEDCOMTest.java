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
package uk.ac.standrews.cs.basic_model.model.gedcom;

import org.junit.Before;
import org.junit.Test;
import uk.ac.standrews.cs.basic_model.model.IPopulation;
import uk.ac.standrews.cs.basic_model.model.IPopulationWriter;
import uk.ac.standrews.cs.basic_model.model.PopulationConverter;
import uk.ac.standrews.cs.basic_model.model.in_memory.AbstractExporterTest;
import uk.ac.standrews.cs.utilities.FileManipulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Tests of GEDCOM export.
 *
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 */
public class PopulationToGEDCOMTest extends AbstractExporterTest {

    protected static final String INTENDED_SUFFIX = "_intended.ged";

    public PopulationToGEDCOMTest(final IPopulation population, final String file_name) throws Exception {

        super(population, file_name);
    }

    @Test
    public void test() throws Exception {

        final IPopulationWriter population_writer = new GEDCOMPopulationWriter(actual_output);

        try (PopulationConverter converter = new PopulationConverter(population, population_writer)) {
            converter.convert();
        }

        FileManipulation.assertThatFilesHaveSameContent(actual_output, intended_output);
    }

    @Before
    public void setup() throws IOException {

        actual_output = Files.createTempFile(null, ".ged");
        intended_output = Paths.get(TEST_DIRECTORY_PATH_STRING, "gedcom", file_name_root + INTENDED_SUFFIX);
    }
}
