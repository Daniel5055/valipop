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
package uk.ac.standrews.cs.valipop.utils.sourceEventRecords.processingVisualiserFormat;

import uk.ac.standrews.cs.valipop.simulationEntities.IPartnership;
import uk.ac.standrews.cs.valipop.simulationEntities.IPerson;
import uk.ac.standrews.cs.valipop.simulationEntities.IPersonCollection;
import uk.ac.standrews.cs.valipop.utils.sourceEventRecords.IndividualSourceRecord;

import java.time.LocalDate;
import java.util.Random;

/**
 * A representation of a BirthFamilyGT Record in the form used by the Digitising Scotland Project.
 *
 * @author Alan Dearle (alan.dearle@st-andrews.ac.uk)
 * @author Graham Kirby (graham.kirby@st-andrews.ac.uk)
 * @author Tom Dalton (tsd4@st-andrews.ac.uk)
 */
public class SimplifiedBirthSourceRecord extends IndividualSourceRecord {

    private LocalDate birth_date;
    private String birth_address;

    private LocalDate parents_marriage_date;
    private String parents_place_of_marriage;

    private String illegitimate_indicator;
    private String informant;
    private String informant_did_not_sign;
    private String adoption;


    public SimplifiedBirthSourceRecord(final IPerson person, IPersonCollection population) {

        // Attributes associated with individual
        setUid(String.valueOf(person.getId()));
        setSex(String.valueOf(person.getSex()));
        setForename(person.getFirstName());
        setSurname(person.getSurname());

        birth_date = person.getBirthDate();

        final IPartnership parents_partnership = person.getParents();

        if (parents_partnership != null) {

            // Attributes associated with individual's parents' marriage.
            parents_marriage_date = parents_partnership.getMarriageDate();

            // added into to allow for the record generator to work with the
            // organic population model which uses the partnership class with
            // no marriage date to represent a cohabitation and thus no
            // record should be generated.
            if (parents_marriage_date != null) {

                setParentsPlaceOfMarriage(parents_partnership.getMarriagePlace());

                // TODO this will need to change to reflect however we choose to model current location in geographical model
                setBirthAddress(parents_partnership.getMarriagePlace());

                setParentAttributes(person, population, parents_partnership);
            }
        }
    }

    public void setBirthAddress(final String birth_address) {
        this.birth_address = birth_address;
    }

    public void setParentsPlaceOfMarriage(final String parents_place_of_marriage) {
        this.parents_place_of_marriage = parents_place_of_marriage;
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder();
        int rnd;

        if (fathers_id != null) {
            rnd = new Random().nextInt(101);
            RelationshipsTable.relationshipsFather.add(new String[]{"Father", String.valueOf(uid), String.valueOf(fathers_id), String.valueOf(rnd), birth_date.getDayOfMonth() + "." + birth_date.getMonth() + "." + birth_date.getYear()});
        }

        if (mothers_id != null) {
            rnd = new Random().nextInt(101);
            RelationshipsTable.relationshipsMother.add(new String[]{"Mother", String.valueOf(uid), String.valueOf(mothers_id), String.valueOf(rnd), birth_date.getDayOfMonth() + "." + birth_date.getMonth() + "." + birth_date.getYear()});
        }

        if (fathers_id != null && mothers_id != null) {
            rnd = new Random().nextInt(101);
            RelationshipsTable.relationshipsMarriage.add(new String[]{"Marriage", String.valueOf(fathers_id), String.valueOf(mothers_id), String.valueOf(rnd), birth_date.getDayOfMonth() + "." + birth_date.getMonth() + "." + birth_date.getYear()});
        }

        append(builder, uid, forename + " " + surname, sex, fathers_id, fathers_forename + " " + fathers_surname,
                mothers_id, mothers_forename + " " + mothers_surname,
                birth_date.getDayOfMonth() + "." + birth_date.getMonth() + "." + birth_date.getYear(),
                birth_address, registration_district_suffix);

        return builder.toString();
    }

    @Override
    public String getHeaders() {
        final StringBuilder builder = new StringBuilder();

        append(builder, "uid", "full_name", "sex", "fathers_id", "fathers_name", "mothers_id", "mothers_name",
                "birth_date", "birth_address", "registration_district_suffix");

        return builder.toString();
    }
}
