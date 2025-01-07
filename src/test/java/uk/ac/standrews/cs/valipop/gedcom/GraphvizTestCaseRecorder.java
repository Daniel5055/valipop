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
package uk.ac.standrews.cs.valipop.gedcom;

import uk.ac.standrews.cs.valipop.simulationEntities.IPersonCollection;
import uk.ac.standrews.cs.valipop.export.IPopulationWriter;
import uk.ac.standrews.cs.valipop.export.gedcom.GEDCOMPopulationWriter;
import uk.ac.standrews.cs.valipop.export.graphviz.GraphvizPopulationWriter;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Generates test cases for  export.
 *
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 */
public class GraphvizTestCaseRecorder extends AbstractTestCaseRecorder {

    // The generated GEDCOM files can be checked for validity at: http://ged-inline.elasticbeanstalk.com

    public static void main(final String[] args) throws Exception {

        new GraphvizTestCaseRecorder().recordTestCase();
    }

    @Override
    protected IPopulationWriter getPopulationWriter(final Path path, final IPersonCollection population) throws IOException {

        return new GraphvizPopulationWriter(population, path);
    }

    @Override
    protected String getIntendedOutputFileSuffix() {

        return ".dot";
    }

    @Override
    protected String getDirectoryName() {
        return "graphviz";
    }
}
