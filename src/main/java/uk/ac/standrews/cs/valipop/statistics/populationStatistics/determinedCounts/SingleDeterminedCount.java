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
package uk.ac.standrews.cs.valipop.statistics.populationStatistics.determinedCounts;

import uk.ac.standrews.cs.valipop.statistics.populationStatistics.statsKeys.StatsKey;

/**
 * A data structure implementing determined counts for single values
 * 
 * @author Tom Dalton (tsd4@st-andrews.ac.uk)
 */
public class SingleDeterminedCount implements DeterminedCount<Integer, Double, Integer, Integer> {

    private StatsKey<Integer, Integer> key;
    private int determinedCount;
    private int fulfilledCount;

    private double rawUncorrectedCount;
    private double rawCorrectedCount;

    public SingleDeterminedCount(StatsKey<Integer, Integer> key, int determinedCount, double rawCorrectedCount, double rawUncorrectedCount) {
        this.key = key;
        this.determinedCount = determinedCount;
        this.rawCorrectedCount = rawCorrectedCount;
        this.rawUncorrectedCount = rawUncorrectedCount;
    }

    public Integer getDeterminedCount() {
        return determinedCount;
    }

    public StatsKey<Integer, Integer> getKey() {
        return key;
    }

    public Integer getFulfilledCount() {
        return fulfilledCount;
    }

    @Override
    public Double getRawCorrectedCount() {
        return rawCorrectedCount;
    }

    @Override
    public Double getRawUncorrectedCount() {
        return rawUncorrectedCount;
    }

    public void setFulfilledCount(Integer fulfilledCount) {
        this.fulfilledCount = fulfilledCount;
    }
}
