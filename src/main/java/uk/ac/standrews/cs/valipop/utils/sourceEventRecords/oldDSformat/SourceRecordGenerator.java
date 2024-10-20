/*
 * Copyright 2017 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
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
package uk.ac.standrews.cs.valipop.utils.sourceEventRecords.oldDSformat;

import uk.ac.standrews.cs.utilities.FileManipulation;
import uk.ac.standrews.cs.utilities.PercentageProgressIndicator;
import uk.ac.standrews.cs.utilities.ProgressIndicator;
import uk.ac.standrews.cs.utilities.TimeManipulation;
import uk.ac.standrews.cs.utilities.archive.CommandLineArgs;
import uk.ac.standrews.cs.valipop.simulationEntities.IPopulation;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Generates birth/death/marriage records for all the people in the database.
 *
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 * @author Alan Dearle (al@st-andrews.ac.uk)
 */
public class SourceRecordGenerator {

    private static final String BIRTH_RECORDS_PATH = "birth_records.txt";
    private static final String DEATH_RECORDS_PATH = "death_records.txt";
    private static final String MARRIAGE_RECORDS_PATH = "marriage_records.txt";

    // TODO allow output file paths to be configured, add -i option to output to console

    private static final int DEFAULT_NUMBER_OF_PROGRESS_UPDATES = 10;
    private static final String NUMBER_OF_PROGRESS_UPDATES_FLAG = "-u";

    private final IPopulation population;
    private final Path outputDir;

    public SourceRecordGenerator(IPopulation population, Path outputDir) {

        this.population = population;
        this.outputDir = outputDir;

    }

    public void generateEventRecords(final String[] args) throws Exception {

        final long start_time = System.currentTimeMillis();

        final int number_of_progress_updates = CommandLineArgs.extractIntFromCommandLineArgs(args, NUMBER_OF_PROGRESS_UPDATES_FLAG, DEFAULT_NUMBER_OF_PROGRESS_UPDATES);

        exportRecords(SourceRecordIterator.getBirthRecordIterator(population), outputDir.resolve(BIRTH_RECORDS_PATH), population.getNumberOfPeople(), number_of_progress_updates);
        TimeManipulation.reportElapsedTime(start_time);

        // The population size is an overestimate of the number of death records, but it doesn't really matter.
        exportRecords(SourceRecordIterator.getDeathRecordIterator(population), outputDir.resolve(DEATH_RECORDS_PATH), population.getNumberOfPeople(), number_of_progress_updates);
        TimeManipulation.reportElapsedTime(start_time);

        exportRecords(SourceRecordIterator.getMarriageRecordIterator(population), outputDir.resolve(MARRIAGE_RECORDS_PATH), population.getNumberOfPartnerships(), number_of_progress_updates);
        TimeManipulation.reportElapsedTime(start_time);
    }

    private static void exportRecords(final Iterable<? extends SourceRecord> records, final Path records_path, int number_of_records, final int number_of_progress_updates) throws IOException {

        FileManipulation.createParentDirectoryIfDoesNotExist(records_path);

        ProgressIndicator progress_indicator = new PercentageProgressIndicator(number_of_progress_updates);
        progress_indicator.setTotalSteps(number_of_records);

        try (final PrintWriter writer = new PrintWriter(Files.newBufferedWriter(records_path, FileManipulation.FILE_CHARSET))) {

            boolean first = true;

            for (final SourceRecord record : records) {

                if(first) {
                    writer.println(record.getHeaders());
                    first = false;
                }

                writer.println(record);
                progress_indicator.progressStep();
            }
        }

        if (progress_indicator.getProportionComplete() < 1.0) {
            progress_indicator.indicateProgress(1.0);
        }
    }
}
