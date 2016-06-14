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
package analytic;

import model.IPartnership;
import model.IPerson;
import model.IPopulation;
import uk.ac.standrews.cs.digitising_scotland.util.ArrayManipulation;

import java.util.List;

/**
 * An analytic class to analyse the distribution of child_ids.
 *
 * @author Alan Dearle (alan.dearle@st-andrews.ac.uk)
 */
public class ChildrenAnalytics {

    private static final int MAX_CHILDREN = 10;
    private static final int ONE_HUNDRED = 100;

    private final int[] children_per_marriage = new int[MAX_CHILDREN]; // tracks family size
    private final IPopulation population;

    /**
     * Creates an analytic instance to analyse child_ids in a population.
     *
     * @param population the population to analyse
     * @throws Exception if the analysis cannot be completed
     */
    public ChildrenAnalytics(final IPopulation population) throws Exception {

        this.population = population;
        analyseChildren();
    }

    /**
     * Prints out all analyses.
     */
    public void printAllAnalytics() {

        final int sum = ArrayManipulation.sum(children_per_marriage);

        System.out.println("Children per marriage sizes:");
        for (int i = 0; i < children_per_marriage.length; i++) {
            if (children_per_marriage[i] != 0) {
                System.out.println("\t" + children_per_marriage[i] + " Marriages with " + i + " child_ids" + " = " + String.format("%.1f", children_per_marriage[i] / (double) sum * ONE_HUNDRED) + '%');
            }
        }
    }

    /**
     * Analyses child_ids of marriages for the population.
     *
     * @throws Exception if the analysis cannot be completed
     */
    public void analyseChildren() throws Exception {

        for (final IPerson person : population.getPeople()) {

            final List<IPartnership> partnerships = person.getPartnerships();
            if (partnerships != null) {

                for (final IPartnership partnership : partnerships) {

                    final List<IPerson> child_ids = partnership.getChildren();

                    if (child_ids != null) {
                        children_per_marriage[child_ids.size()]++;
                    }
                }
            }
        }
    }
}
