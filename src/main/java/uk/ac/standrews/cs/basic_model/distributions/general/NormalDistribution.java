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

import java.util.Random;

/**
 * An approximately normal distribution of numbers.
 * 
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 * @author Tom Dalton (tsd4@st-andrews.ac.uk)
 */
public class NormalDistribution extends RestrictedDistribution<Double> {

	private final static double PREEMPTIVE_RANGE = 0.01;
	
	private final Random random;
	private final double mean;
	private final double standard_deviation;

	/**
	 * Creates a normal distribution with specified characteristics.
	 * @param mean the mean of the distribution
	 * @param standard_deviation the standard deviation of the distribution
	 * @param random the random number generator to be used
	 * @throws NegativeDeviationException if the standard deviation is negative
	 */
	public NormalDistribution(final double mean, final double standard_deviation, final Random random) throws NegativeDeviationException {

		if (standard_deviation < 0.0) {
			throw new NegativeDeviationException("negative standard deviation: " + standard_deviation);
		}

		this.mean = mean;
		this.standard_deviation = standard_deviation;
		this.random = random;
	}

	/**
	 * Creates a normal distribution with specified characteristics.
	 * Also sets up the distribution to allow it to be called with restricted values. 
	 * 
	 * @param mean the mean of the distribution
	 * @param standard_deviation the standard deviation of the distribution
	 * @param random the random number generator to be used
	 * @param minimumReturnValue The smallest value that the distribution is able to return
	 * @param maximumReturnValue The largest value that the distribution is able to return
	 * @throws NegativeDeviationException if the standard deviation is negative
	 */
	public NormalDistribution(final double mean, final double standard_deviation, final Random random, final double minimumReturnValue, final double maximumReturnValue) throws NegativeDeviationException {
		this(mean, standard_deviation, random);
		this.minimumSpecifiedValue = minimumReturnValue;
		this.maximumSpecifiedValue = maximumReturnValue;
	}

	@Override
	public Double getSample() {

		return mean + random.nextGaussian() * standard_deviation;
	}

	@Override
	public Double getSample(final double earliestReturnValue, final double latestReturnValue) throws NoPermissableValueException, NotSetUpAtClassInitilisationException {
		if (minimumSpecifiedValue == (Double) null || maximumSpecifiedValue == (Double) null) {
			throw new NotSetUpAtClassInitilisationException();
		}

		if (earliestReturnValue >= maximumSpecifiedValue || latestReturnValue <= minimumSpecifiedValue) {
			throw new NoPermissableValueException();
		} else {
			if (unusedSampleValues.size() != 0) {
				int j = 0;
				for (double d : unusedSampleValues) {
					if (inRange(d, earliestReturnValue, latestReturnValue)) {
						unusedSampleValues.remove(j);
						return d;
					}
					j++;
				}
			}
		}

		if(rangeProbability(earliestReturnValue, latestReturnValue) < PREEMPTIVE_RANGE) {
			double rValue = randomValueIn(earliestReturnValue, latestReturnValue);
			//			preemptiveSampleValues.add(rValue);
			//			System.out.println(this + " PESV Size: " + preemptiveSampleValues.size());
			return rValue;

		} else {
			double v = getSample();
			while (!inRange(v, earliestReturnValue, latestReturnValue)) {
				if (unusedSampleValues.size() < 10000000) {
					unusedSampleValues.add(v);
				}
				v = getSample();
			}
			return v;
		}
		
	}

	/**
	 * Check if the given double d falls between the two given values.
	 * 
	 * @param d The double to be considered.
	 * @param earliestReturnValue The smaller value.
	 * @param latestReturnValue The larger value.
	 * @return Boolean value of true if d falls inbetween the two given values else false.
	 */
	protected static boolean inRange(final double d, final double earliestReturnValue, final double latestReturnValue) {
		return earliestReturnValue <= d && d <= latestReturnValue;
	}

	@Override
	public int[] getWeights() {
		int[] ret = {1, 21, 136, 341, 341, 136, 21, 1};
		return ret;
	}
	
	public int[] getCumlativeWeights() {
		int[] ret = {1, 22, 158, 499, 840, 976, 997, 998};
		return ret;
	}
	
	
	private double randomValueIn(double smallestPermissableReturnValue, double largestPermissableReturnValue) {

		double a = largestPermissableReturnValue - smallestPermissableReturnValue;
		int b = (int) (a * 100000);
		int rand = random.nextInt(b);
		double c = rand / 100000.0;
		double val = c + smallestPermissableReturnValue;

		return val;
	}

	// -------------------------------------------------------------------------------------------------------

	private double rangeProbability(double smallestPermissableReturnValue, double largestPermissableReturnValue) {

		double bucket_size = (maximumSpecifiedValue - minimumSpecifiedValue) / 8;
		
		int topBucket =  (int) (largestPermissableReturnValue / bucket_size);
		int bottomBucket =  (int) (smallestPermissableReturnValue / bucket_size);

		if(largestPermissableReturnValue > maximumSpecifiedValue) {
			topBucket =  (int) (maximumSpecifiedValue / bucket_size) - 1;
		}

		if(smallestPermissableReturnValue < minimumSpecifiedValue) {
			bottomBucket =  (int) (minimumSpecifiedValue / bucket_size) - 1;
		}

		return (getCumlativeWeights()[topBucket] - getCumlativeWeights()[bottomBucket])/1000.0;
	}
}
