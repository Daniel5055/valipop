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
package uk.ac.standrews.cs.digitising_scotland.population_model.population_representations.adapted_db;

import uk.ac.standrews.cs.digitising_scotland.population_model.population_representations.adapted_interfaces.*;
import uk.ac.standrews.cs.digitising_scotland.util.*;
import uk.ac.standrews.cs.nds.util.*;
import uk.ac.standrews.cs.nds.util.Diagnostic;
import uk.ac.standrews.cs.util.tools.*;

/**
 * Generates a population in a series of independent batches, and exports to the database.
 *
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 */
public abstract class LinkedPopulationToDB {

	private static final String BATCH_SIZE_FLAG = "-b";
	private static final String NUMBER_OF_BATCHES_FLAG = "-n";
	private static final String NUMBER_OF_PROGRESS_UPDATES_FLAG = "-u";

	public static final int DEFAULT_BATCH_SIZE = 1000;
	public static final int DEFAULT_NUMBER_OF_BATCHES = 1;
	public static final int DEFAULT_NUMBER_OF_PROGRESS_UPDATES = 10;

	public abstract ILinkedPopulation getPopulation(int batch_size, ProgressIndicator indicator) throws Exception;

	protected void export(final String[] args) throws Exception {

		final int batch_size = CommandLineArgs.extractIntFromCommandLineArgs(args, BATCH_SIZE_FLAG, DEFAULT_BATCH_SIZE);
		final int number_of_batches = CommandLineArgs.extractIntFromCommandLineArgs(args, NUMBER_OF_BATCHES_FLAG, DEFAULT_NUMBER_OF_BATCHES);
		final int number_of_progress_updates = CommandLineArgs.extractIntFromCommandLineArgs(args, NUMBER_OF_PROGRESS_UPDATES_FLAG, DEFAULT_NUMBER_OF_PROGRESS_UPDATES);

		if (batch_size > 0 && number_of_batches > 0 && number_of_progress_updates > 0) {

			showInfo(batch_size, number_of_batches);

			for (int batch_number = 0; batch_number < number_of_batches; batch_number++) {
				generateBatch(batch_size, batch_number, number_of_progress_updates);
			}

			showInfo(batch_size, number_of_batches);

		} else {
			usage();
		}
	}

	private static void showInfo(final int batch_size, final int number_of_batches) {

		Diagnostic.traceNoSource(number_of_batches + " batch" + (number_of_batches > 1 ? "es" : "") + " of population size " + batch_size);
	}

	private void generateBatch(final int batch_size, final int batch_number, final int number_of_progress_updates) throws Exception {

		Diagnostic.traceNoSource("Generating batch " + (batch_number + 1));

		long start_time = System.currentTimeMillis();

		final ILinkedPopulation population_interface = getPopulation(batch_size, new PercentageProgressIndicator(number_of_progress_updates));
		final LinkedPopulationConverter converter = new LinkedPopulationConverter(population_interface, new DBPopulationWriter(), new PercentageProgressIndicator(number_of_progress_updates));
		TimeManipulation.reportElapsedTime(start_time);

		Diagnostic.traceNoSource("Exporting to database");
		start_time = System.currentTimeMillis();
		converter.convert();
		TimeManipulation.reportElapsedTime(start_time);
	}

	private void usage() {

		System.out.println("Usage: java " + getClass().getSimpleName() + " " +
				BATCH_SIZE_FLAG + "<batch size> " +
				NUMBER_OF_BATCHES_FLAG + "<number of batches> " +
				NUMBER_OF_PROGRESS_UPDATES_FLAG + "<number of progress updates>");
	}
}
