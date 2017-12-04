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
package uk.ac.standrews.cs.basic_model.organic.logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import uk.ac.standrews.cs.basic_model.organic.FamilyType;
import uk.ac.standrews.cs.basic_model.organic.OrganicPartnership;
import uk.ac.standrews.cs.basic_model.organic.DivorceInstigation;
import uk.ac.standrews.cs.basic_model.organic.DivorceReason;
import uk.ac.standrews.cs.basic_model.organic.OrganicPerson;
import uk.ac.standrews.cs.basic_model.organic.OrganicPopulation;

public class LoggingControl {

    private static CountLogger populationLogger;
    private static CountLogger affairCountLogger;
    private static CountLogger cohabCountLogger;
    private static CountLogger cohabThenMarriageCountLogger;
    private static CountLogger marriageCountLogger;

    private static TemporalIntegerLogger numberOfChildrenFromAffairsDistributionLogger;
    private static TemporalIntegerLogger numberOfChildrenFromCohabitationDistributionLogger;
    private static TemporalIntegerLogger numberOfChildrenFromCohabThenMarriageDistributionLogger;
    private static TemporalIntegerLogger numberOfChildrenFromMarriagesDistributionLogger;

    private static TemporalIntegerLogger numberOfChildrenInMaterityDistributionLogger;

    private static TemporalIntegerLogger timeFromCohabToMarriageDistributionLogger;
    private static TemporalIntegerLogger cohabitationLengthDistributionLogger;

    private static TemporalIntegerLogger ageAtDivorceMaleDistributionLogger;
    private static TemporalIntegerLogger ageAtDivorceFemaleDistributionLogger;
    private static TemporalIntegerLogger ageAtDeathDistributionLogger;
    
    private static TemporalIntegerLogger ageAtCohabitationFemaleDistributionLogger;
    private static TemporalIntegerLogger ageAtCohabitationMaleDistributionLogger;
    private static TemporalIntegerLogger ageAtCohabitationThenMarriageFemaleDistributionLogger;
    private static TemporalIntegerLogger ageAtCohabitationThenMarriageMaleDistributionLogger;
    private static TemporalIntegerLogger ageAtMarriageFemaleDistributionLogger;
    private static TemporalIntegerLogger ageAtMarriageMaleDistributionLogger;

    private static TemporalEnumLogger<DivorceInstigation> divorceInstiagetionByGenderDistributionLogger;
    private static TemporalEnumLogger<DivorceReason> divorceReasonFemaleDistributionLogger;
    private static TemporalEnumLogger<DivorceReason> divorceReasonMaleDistributionLogger;

    private static TemporalEnumLogger<FamilyType> familyCharacteristicDistributionLogger;
    
    // Hard to log efficiently in current implementation
//  public static TemporalEnumLogger<FamilyType> remarriageFamilyCharacteristicDistributionLogger;

    public static void setUpLogger() {
        LoggingControl.populationLogger = new CountLogger(OrganicPopulation.getStartYear(), OrganicPopulation.getEndYear(), "Population", "Population Change Over Time", "Population");
        LoggingControl.affairCountLogger = new CountLogger(OrganicPopulation.getStartYear(), OrganicPopulation.getEndYear(), "Affair", "Affairs Over Time", "Number of Affairs");
        LoggingControl.cohabCountLogger = new CountLogger(OrganicPopulation.getStartYear(), OrganicPopulation.getEndYear(), "Cohabitation", "Cohabitations Over Time", "Number of Cohabitations");
        LoggingControl.cohabThenMarriageCountLogger = new CountLogger(OrganicPopulation.getStartYear(), OrganicPopulation.getEndYear(), "CohabitationTheMarriage", "Cohabitation then Marriages Over Time", "Number of Cohabiation then Marriages");
        LoggingControl.marriageCountLogger = new CountLogger(OrganicPopulation.getStartYear(), OrganicPopulation.getEndYear(), "Marriage", "Marriages Over Time", "Number of Marriages");

        LoggingControl.numberOfChildrenFromAffairsDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalAffairNumberOfChildrenDistribution(), "ChildrenNumberOfAffairs", "Number of Children Distribution - Affairs", "Number of Children", false);
        LoggingControl.numberOfChildrenFromCohabitationDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalChildrenNumberOfInCohabDistribution(), "ChildrenNumberOfCohab", "Number of Children Distribution - Cohabitation", "Number of Children", false);
        LoggingControl.numberOfChildrenFromCohabThenMarriageDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalChildrenNumberOfInCohabThenMarriageDistribution(), "ChildrenNumberOfCohabTheMarriage", "Number of Children Distribution - Cohabitation Then Marriage", "Number of Children", false);
        LoggingControl.numberOfChildrenFromMarriagesDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalChildrenNumberOfInMarriageDistribution(), "ChildrenNumberOfMarriage", "Number of Children Distribution - Marriage", "Number of Children", false);

        LoggingControl.numberOfChildrenInMaterityDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalChildrenNumberOfInMaternityDistribution(), "ChildrenInMaternity", "Number of Children In Maternity Distribution", "Number of Children in Maternity", false);

        LoggingControl.timeFromCohabToMarriageDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalCohabitaitonToMarriageTimeDistribution(), "TimeFromCohabToMarriage", "Time from Cohabitation to Marriage Distribution", "Time from Cohabiation to Marriage Distribution", true);
        LoggingControl.cohabitationLengthDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalCohabitationLengthDistribution(), "CohabitationLength", "Length of Cohabitation Distribution", "Length OF Cohabitation", true);

        LoggingControl.ageAtDivorceMaleDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalDivorceAgeForMaleDistribution(), "AgeAtDivorceMale", "Age at Divorce for Males", "Age at Divorce", true);
        LoggingControl.ageAtDivorceFemaleDistributionLogger = new TemporalIntegerLogger(OrganicPartnership.getTemporalDivorceAgeForFemaleDistribution(), "AgeAtDivorceFemale", "Age at Divorce for Females", "Age at Divorce", true);
        LoggingControl.ageAtDeathDistributionLogger = new TemporalIntegerLogger(OrganicPerson.getDeathAgeAtDistribution(), "AgeAtDeath", "Age at Death Distribution", "Age at Death", true);
        
        LoggingControl.ageAtCohabitationFemaleDistributionLogger = new TemporalIntegerLogger(OrganicPerson.getTemporalCohabitationAgeForFemalesDistribution(), "CohabitationAgeFemales", "Age at Cohabitation for Females", "Age at Cohabitation", true);
        LoggingControl.ageAtCohabitationMaleDistributionLogger = new TemporalIntegerLogger(OrganicPerson.getTemporalCohabitationAgeForMalesDistribution(), "CohabitationAgeMales", "Age at Cohabitation for Males", "Age at Cohabitation", true);
        LoggingControl.ageAtCohabitationThenMarriageFemaleDistributionLogger = new TemporalIntegerLogger(OrganicPerson.getTemporalCohabitationAgeForFemalesDistribution(), "CohabitationThenMarriageAgeFemales", "Age at Cohabitation then Marriage for Females", "Age at Cohabitation", true);
        LoggingControl.ageAtCohabitationThenMarriageMaleDistributionLogger = new TemporalIntegerLogger(OrganicPerson.getTemporalCohabitationAgeForMalesDistribution(), "CohabitationThenMarriageAgeMales", "Age at Cohabitation then Marriage for Males", "Age at Cohabitation", true);
        LoggingControl.ageAtMarriageFemaleDistributionLogger = new TemporalIntegerLogger(OrganicPerson.getTemporalMarriageAgeForFemalesDistribution(), "MarriageAgeFemales", "Age at Marriage for Females", "Age at Marriage", true);
        LoggingControl.ageAtMarriageMaleDistributionLogger = new TemporalIntegerLogger(OrganicPerson.getTemporalMarriageAgeForMalesDistribution(), "MarriageAgeMales", "Age at Marriage for Males", "Age At Marriage", true);

        LoggingControl.divorceInstiagetionByGenderDistributionLogger = new TemporalEnumLogger<>(OrganicPartnership.getTemporalDivorceInstigatedByGenderDistribution(), "DivorceInstigationByGender", "Divorce Instigation By Gender", "Divorce Instigation By");
        LoggingControl.divorceReasonFemaleDistributionLogger = new TemporalEnumLogger<>(OrganicPartnership.getTemporalDivorceReasonFemaleDistribution(), "DivorceReasonFemale", "Divorce Reason For Females", "Divorce Reason");
        LoggingControl.divorceReasonMaleDistributionLogger = new TemporalEnumLogger<>(OrganicPartnership.getTemporalDivorceReasonMaleDistribution(), "DivorceReasonMale", "Divorce Reason For Males", "Divorce Reason");
//        LoggingControl.remarriageFamilyCharacteristicDistributionLogger = new TemporalEnumLogger<>(OrganicPerson.getTemporalRemarriagePartnershipCharacteristicDistribution(), "RemarriagePartnershipCharacteristic", "Remarriage Partnership Characteristic", "Partnership Characteristic");
        LoggingControl.familyCharacteristicDistributionLogger = new TemporalEnumLogger<FamilyType>(OrganicPerson.getTemporalPartnershipCharacteristicDistribution(), "PartnershipCharacteristic", "Partnership Characteristic", "Partnership Characteristic");

    }

    private static void output() {
        LoggingControl.populationLogger.outputToGnuPlotFormat();
        LoggingControl.affairCountLogger.outputToGnuPlotFormat();
        LoggingControl.cohabCountLogger.outputToGnuPlotFormat();
        LoggingControl.cohabThenMarriageCountLogger.outputToGnuPlotFormat();
        LoggingControl.marriageCountLogger.outputToGnuPlotFormat();

        LoggingControl.numberOfChildrenFromAffairsDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.numberOfChildrenFromCohabitationDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.numberOfChildrenFromCohabThenMarriageDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.numberOfChildrenFromMarriagesDistributionLogger.outputToGnuPlotFormat();

        LoggingControl.numberOfChildrenInMaterityDistributionLogger.outputToGnuPlotFormat();

        LoggingControl.timeFromCohabToMarriageDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.cohabitationLengthDistributionLogger.outputToGnuPlotFormat();

        LoggingControl.ageAtDivorceMaleDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.ageAtDivorceFemaleDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.ageAtDeathDistributionLogger.outputToGnuPlotFormat();

        LoggingControl.ageAtCohabitationFemaleDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.ageAtCohabitationMaleDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.ageAtCohabitationThenMarriageFemaleDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.ageAtCohabitationThenMarriageMaleDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.ageAtMarriageFemaleDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.ageAtMarriageMaleDistributionLogger.outputToGnuPlotFormat();

        LoggingControl.divorceInstiagetionByGenderDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.divorceReasonFemaleDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.divorceReasonMaleDistributionLogger.outputToGnuPlotFormat();
//        LoggingControl.remarriageFamilyCharacteristicDistributionLogger.outputToGnuPlotFormat();
        LoggingControl.familyCharacteristicDistributionLogger.outputToGnuPlotFormat();

    }

    public static void createGnuPlotOutputFilesAndScript() {
        output();
        PrintWriter writer;
        try {
            String filePath = "src/main/resources/output/gnu/log_output_script.p";
            writer = new PrintWriter(filePath, "UTF-8");
            writer.println("# This file is called log_output_script.p");
            writer.println("reset");

            writer.println("set terminal pdf");
            writer.println("set output '~/output" + System.nanoTime() + ".pdf'");

            LoggingControl.populationLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.cohabCountLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.cohabThenMarriageCountLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.marriageCountLogger.generateGnuPlotScriptLines(writer);

            LoggingControl.numberOfChildrenFromAffairsDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.numberOfChildrenFromCohabitationDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.numberOfChildrenFromCohabThenMarriageDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.numberOfChildrenFromMarriagesDistributionLogger.generateGnuPlotScriptLines(writer);

            LoggingControl.numberOfChildrenInMaterityDistributionLogger.generateGnuPlotScriptLines(writer);

            LoggingControl.timeFromCohabToMarriageDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.cohabitationLengthDistributionLogger.generateGnuPlotScriptLines(writer);

            LoggingControl.ageAtDivorceMaleDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.ageAtDivorceFemaleDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.ageAtDeathDistributionLogger.generateGnuPlotScriptLines(writer);

            LoggingControl.ageAtCohabitationFemaleDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.ageAtCohabitationMaleDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.ageAtCohabitationThenMarriageFemaleDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.ageAtCohabitationThenMarriageMaleDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.ageAtMarriageFemaleDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.ageAtMarriageMaleDistributionLogger.generateGnuPlotScriptLines(writer);

            LoggingControl.divorceInstiagetionByGenderDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.divorceReasonFemaleDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.divorceReasonMaleDistributionLogger.generateGnuPlotScriptLines(writer);
//            LoggingControl.remarriageFamilyCharacteristicDistributionLogger.generateGnuPlotScriptLines(writer);
            LoggingControl.familyCharacteristicDistributionLogger.generateGnuPlotScriptLines(writer);

            writer.println("set terminal png");
            writer.println("reset");

            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new Error(e);
        }
    }
    
    public static void logPartnershipEndByDeath(final OrganicPartnership partnership, final int currentDay) {
        switch (partnership.getFamilyType()) {
            case AFFAIR:
                LoggingControl.affairCountLogger.decCount();
                break;
            case COHABITATION:
                LoggingControl.cohabitationLengthDistributionLogger.log(currentDay, currentDay - partnership.getParntershipDay());
                LoggingControl.cohabCountLogger.decCount();
                break;
            case COHABITATION_THEN_MARRIAGE:
                LoggingControl.cohabThenMarriageCountLogger.decCount();
                break;
            case MARRIAGE:
                LoggingControl.marriageCountLogger.decCount();
                break;
            default:
                break;
        }
    }
    
    public static void logPartnershipEnd(final OrganicPartnership partnership, final int currentDay, final OrganicPerson male, final OrganicPerson female) {
        switch (partnership.getFamilyType()) {
            case AFFAIR:
                LoggingControl.affairCountLogger.decCount();
                break;
            case COHABITATION:
                LoggingControl.cohabitationLengthDistributionLogger.log(currentDay, currentDay - partnership.getParntershipDay());
                LoggingControl.cohabCountLogger.decCount();
                break;
            case COHABITATION_THEN_MARRIAGE:           
                LoggingControl.ageAtDivorceMaleDistributionLogger.log(currentDay, OrganicPopulation.getCurrentDay() - male.getBirthDay());
                LoggingControl.ageAtDivorceFemaleDistributionLogger.log(currentDay, OrganicPopulation.getCurrentDay() - female.getBirthDay());
                LoggingControl.cohabThenMarriageCountLogger.decCount();
                break;
            case MARRIAGE:
                LoggingControl.ageAtDivorceMaleDistributionLogger.log(currentDay, OrganicPopulation.getCurrentDay() - male.getBirthDay());
                LoggingControl.ageAtDivorceFemaleDistributionLogger.log(currentDay, OrganicPopulation.getCurrentDay() - female.getBirthDay());
                LoggingControl.marriageCountLogger.decCount();
                break;
            default:
                break;
        }
    }
    
    public static void logPartnership(final FamilyType familyType, final int currentDay, final int maleDays, final int femaleDays) {
        switch(familyType) {
            case AFFAIR:
                logAffair(currentDay, maleDays, femaleDays);
                break;
            case COHABITATION:
                logCohabitation(currentDay, maleDays, femaleDays);
                break;
            case COHABITATION_THEN_MARRIAGE:
                logCohabitationthenMarriage(currentDay, maleDays, femaleDays);
                break;
            case MARRIAGE:
                logMarriage(currentDay, maleDays, femaleDays);
                break;
            default:
                break;
        }
    }

    public static void logAffair(final int currentDay, final int maleDays, final int femaleDays) {
        LoggingControl.affairCountLogger.incCount();
    }

    public static void logCohabitation(final int currentDay, final int maleDays, final int femaleDays) {
        LoggingControl.cohabCountLogger.incCount();
        LoggingControl.ageAtCohabitationMaleDistributionLogger.log(currentDay, maleDays);
        LoggingControl.ageAtCohabitationFemaleDistributionLogger.log(currentDay, femaleDays);
    }

    public static void logCohabitationthenMarriage(final int currentDay, final int maleDays, final int femaleDays) {
        LoggingControl.cohabThenMarriageCountLogger.incCount();
        LoggingControl.ageAtCohabitationThenMarriageMaleDistributionLogger.log(currentDay, maleDays);
        LoggingControl.ageAtCohabitationThenMarriageFemaleDistributionLogger.log(currentDay, femaleDays);
    }

    public static void logMarriage(final int currentDay, final int maleDays, final int femaleDays) {
        LoggingControl.marriageCountLogger.incCount();
        LoggingControl.ageAtMarriageMaleDistributionLogger.log(currentDay, maleDays);
        LoggingControl.ageAtMarriageFemaleDistributionLogger.log(currentDay, femaleDays);
    }

    public static void yearEndLog(int currentDay) {
        LoggingControl.populationLogger.log(currentDay);
        LoggingControl.affairCountLogger.log(currentDay);
        LoggingControl.cohabCountLogger.log(currentDay);
        LoggingControl.cohabThenMarriageCountLogger.log(currentDay);
        LoggingControl.marriageCountLogger.log(currentDay);
    }

    /**
     * @return the populationLogger
     */
    public static CountLogger getPopulationLogger() {
        return populationLogger;
    }

    /**
     * @return the affairCountLogger
     */
    public static CountLogger getAffairCountLogger() {
        return affairCountLogger;
    }

    /**
     * @return the cohabCountLogger
     */
    public static CountLogger getCohabCountLogger() {
        return cohabCountLogger;
    }

    /**
     * @return the cohabThenMarriageCountLogger
     */
    public static CountLogger getCohabThenMarriageCountLogger() {
        return cohabThenMarriageCountLogger;
    }

    /**
     * @return the marriageCountLogger
     */
    public static CountLogger getMarriageCountLogger() {
        return marriageCountLogger;
    }

    /**
     * @return the numberOfChildrenFromAffairsDistributionLogger
     */
    public static TemporalIntegerLogger getNumberOfChildrenFromAffairsDistributionLogger() {
        return numberOfChildrenFromAffairsDistributionLogger;
    }

    /**
     * @return the numberOfChildrenFromCohabitationDistributionLogger
     */
    public static TemporalIntegerLogger getNumberOfChildrenFromCohabitationDistributionLogger() {
        return numberOfChildrenFromCohabitationDistributionLogger;
    }

    /**
     * @return the numberOfChildrenFromCohabThenMarriageDistributionLogger
     */
    public static TemporalIntegerLogger getNumberOfChildrenFromCohabThenMarriageDistributionLogger() {
        return numberOfChildrenFromCohabThenMarriageDistributionLogger;
    }

    /**
     * @return the numberOfChildrenFromMarriagesDistributionLogger
     */
    public static TemporalIntegerLogger getNumberOfChildrenFromMarriagesDistributionLogger() {
        return numberOfChildrenFromMarriagesDistributionLogger;
    }

    /**
     * @return the numberOfChildrenInMaterityDistributionLogger
     */
    public static TemporalIntegerLogger getNumberOfChildrenInMaterityDistributionLogger() {
        return numberOfChildrenInMaterityDistributionLogger;
    }

    /**
     * @return the timeFromCohabToMarriageDistributionLogger
     */
    public static TemporalIntegerLogger getTimeFromCohabToMarriageDistributionLogger() {
        return timeFromCohabToMarriageDistributionLogger;
    }

    /**
     * @return the cohabitationLengthDistributionLogger
     */
    public static TemporalIntegerLogger getCohabitationLengthDistributionLogger() {
        return cohabitationLengthDistributionLogger;
    }

    /**
     * @return the ageAtDivorceMaleDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtDivorceMaleDistributionLogger() {
        return ageAtDivorceMaleDistributionLogger;
    }

    /**
     * @return the ageAtDivorceFemaleDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtDivorceFemaleDistributionLogger() {
        return ageAtDivorceFemaleDistributionLogger;
    }

    /**
     * @return the ageAtDeathDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtDeathDistributionLogger() {
        return ageAtDeathDistributionLogger;
    }

    /**
     * @return the ageAtCohabitationFemaleDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtCohabitationFemaleDistributionLogger() {
        return ageAtCohabitationFemaleDistributionLogger;
    }

    /**
     * @return the ageAtCohabitationMaleDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtCohabitationMaleDistributionLogger() {
        return ageAtCohabitationMaleDistributionLogger;
    }

    /**
     * @return the ageAtCohabitationThenMarriageFemaleDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtCohabitationThenMarriageFemaleDistributionLogger() {
        return ageAtCohabitationThenMarriageFemaleDistributionLogger;
    }

    /**
     * @return the ageAtCohabitationThenMarriageMaleDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtCohabitationThenMarriageMaleDistributionLogger() {
        return ageAtCohabitationThenMarriageMaleDistributionLogger;
    }

    /**
     * @return the ageAtMarriageFemaleDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtMarriageFemaleDistributionLogger() {
        return ageAtMarriageFemaleDistributionLogger;
    }

    /**
     * @return the ageAtMarriageMaleDistributionLogger
     */
    public static TemporalIntegerLogger getAgeAtMarriageMaleDistributionLogger() {
        return ageAtMarriageMaleDistributionLogger;
    }

    /**
     * @return the divorceInstiagetionByGenderDistributionLogger
     */
    public static TemporalEnumLogger<DivorceInstigation> getDivorceInstiagetionByGenderDistributionLogger() {
        return divorceInstiagetionByGenderDistributionLogger;
    }

    /**
     * @return the divorceReasonFemaleDistributionLogger
     */
    public static TemporalEnumLogger<DivorceReason> getDivorceReasonFemaleDistributionLogger() {
        return divorceReasonFemaleDistributionLogger;
    }

    /**
     * @return the divorceReasonMaleDistributionLogger
     */
    public static TemporalEnumLogger<DivorceReason> getDivorceReasonMaleDistributionLogger() {
        return divorceReasonMaleDistributionLogger;
    }

    /**
     * @return the familyCharacteristicDistributionLogger
     */
    public static TemporalEnumLogger<FamilyType> getFamilyCharacteristicDistributionLogger() {
        return familyCharacteristicDistributionLogger;
    }

}
