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
package uk.ac.standrews.cs.valipop.export.gedcom;

import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import uk.ac.standrews.cs.utilities.MappedIterator;
import uk.ac.standrews.cs.utilities.Mapper;
import uk.ac.standrews.cs.valipop.simulationEntities.partnership.IPartnership;
import uk.ac.standrews.cs.valipop.simulationEntities.person.IPerson;
import uk.ac.standrews.cs.valipop.simulationEntities.population.IPopulation;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Iterator;

/**
 * Provides abstract interface to a population represented in a GEDCOM file.
 *
 * @author Alan Dearle (alan.dearle@st-andrews.ac.uk)
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 */
public class GEDCOMPopulationAdapter implements IPopulation {

    private final GedcomParser parser;
    private String description;

    /**
     * Initialises the adapter for a given GEDCOM file.
     *
     * @param path the path of the GEDCOM file
     * @throws IOException           if the file cannot be accessed
     * @throws GedcomParserException if the GEDCOM file is not well formed
     */
    public GEDCOMPopulationAdapter(final Path path) throws IOException, GedcomParserException {

        parser = new GedcomParser();
        parser.load(path.toString());
    }

    @Override
    public Iterable<IPerson> getPeople() {

        final Iterator<Individual> gedcom_iterator = parser.gedcom.individuals.values().iterator();

        return new Iterable<IPerson>() {

            private final Mapper<Individual, IPerson> mapper = individual -> {
                try {
                    return new GEDCOMPerson(individual);

                } catch (final ParseException e) {
                    throw new RuntimeException(e.getMessage());
                }
            };

            @Override
            public Iterator<IPerson> iterator() {
                return new MappedIterator<>(gedcom_iterator, mapper);
            }
        };
    }

    @Override
    public Iterable<IPartnership> getPartnerships() {

        final Iterator<Family> gedcom_iterator = parser.gedcom.families.values().iterator();

        return new Iterable<IPartnership>() {

            private final Mapper<Family, IPartnership> mapper = family -> {
                try {
                    return new GEDCOMPartnership(family);
                } catch (final ParseException e) {
                    throw new RuntimeException(e.getMessage());
                }
            };

            @Override
            public Iterator<IPartnership> iterator() {
                return new MappedIterator<>(gedcom_iterator, mapper);
            }
        };
    }

    @Override
    public IPerson findPerson(final int id) {

        for (final IPerson person : getPeople()) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    @Override
    public IPartnership findPartnership(final int id) {

        for (final IPartnership partnership : getPartnerships()) {
            if (partnership.getId() == id) {
                return partnership;
            }
        }
        return null;
    }

    @Override
    public int getNumberOfPeople() {

        return parser.gedcom.individuals.values().size();
    }

    @Override
    public int getNumberOfPartnerships() {

        return parser.gedcom.families.values().size();
    }

    @Override
    public void setDescription(final String description) {

        this.description = description;
    }

    @Override
    public String toString() {

        return description;
    }
}
