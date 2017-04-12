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
package uk.ac.standrews.cs.digitising_scotland.population_model.tools;

import uk.ac.standrews.cs.digitising_scotland.population_model.config.PopulationProperties;
import uk.ac.standrews.cs.digitising_scotland.population_model.model.database.DBInitialiser;
import uk.ac.standrews.cs.utilities.archive.Diagnostic;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Sets up a database ready to contain population data, overwriting any existing data.
 *
 * @author Ilia Shumailov (is33@st-andrews.ac.uk)
 * @author Alan Dearle (alan.dearle@st-andrews.ac.uk)
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 */
public class InitialiseDB {

    /**
     * Sets up a database ready to contain population data.
     *
     * @param args ignored
     * @throws java.io.IOException
     */
    public static void main(final String[] args) throws IOException, SQLException {

        new DBInitialiser().setupDB();

        Diagnostic.traceNoSource("Database " + PopulationProperties.getDatabaseName() + " initialised");

        // TODO check for existing db content
    }
}
