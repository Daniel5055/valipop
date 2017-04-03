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
package uk.ac.standrews.cs.digitising_scotland.population_model.population_representations.adapted_interfaces;

/**
 * Interface to be implemented by classes that process information from a population.
 *
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 * @see PopulationConverter
 */
public interface IPopulationWriter extends AutoCloseable {

    /**
     * Records a given person from the population.
     *
     * @param person the person
     * @throws Exception if anything goes wrong
     */
    void recordPerson(ILinkedPerson person) throws Exception;

    /**
     * Records a given partnership from the population.
     *
     * @param partnership the person
     * @throws Exception if anything goes wrong
     */
    void recordPartnership(ILinkedChildbearingPartnership partnership) throws Exception;
}
