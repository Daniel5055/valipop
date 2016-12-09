package uk.ac.standrews.cs.digitising_scotland.linkage.lxp_records;

import uk.ac.standrews.cs.nds.persistence.PersistentObjectException;
import uk.ac.standrews.cs.nds.rpc.stream.JSONReader;
import uk.ac.standrews.cs.storr.impl.exceptions.IllegalKeyException;
import uk.ac.standrews.cs.storr.interfaces.IBucket;
import uk.ac.standrews.cs.storr.interfaces.IRepository;
import uk.ac.standrews.cs.storr.types.LXPBaseType;
import uk.ac.standrews.cs.storr.types.LXP_SCALAR;

/**
 * Created by al on 03/10/2014.
 */
public class Birth extends AbstractLXP {

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String ORIGINAL_ID = "ORIGINAL_ID";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String FORENAME = "forename";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String CHANGED_FORENAME = "changed_forename";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String SURNAME = "surname";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String CHANGED_SURNAME = "changed_surname";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String SEX = "sex";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String FATHERS_FORENAME = "fathers_forename";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String FATHERS_SURNAME = "fathers_surname";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String MOTHERS_FORENAME = "mothers_forename";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String MOTHERS_SURNAME = "mothers_surname";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String MOTHERS_MAIDEN_SURNAME = "mothers_maiden_surname";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String CHANGED_MOTHERS_MAIDEN_SURNAME = "changed_mothers_maiden_surname";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String FATHERS_OCCUPATION = "fathers_occupation";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String YEAR_OF_REGISTRATION = "YEAR_OF_REGISTRATION";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String ENTRY = "ENTRY";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String REGISTRATION_DISTRICT_SUFFIX = "REGISTRATION_DISTRICT_SUFFIX";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String REGISTRATION_DISTRICT_NUMBER = "REGISTRATION_DISTRICT_NUMBER";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String CORRECTED_ENTRY = "corrected_entry";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String IMAGE_QUALITY = "image_quality";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String BIRTH_DAY = "birth_day";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String BIRTH_MONTH = "birth_month";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String BIRTH_YEAR = "birth_year";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String BIRTH_ADDRESS = "birth_address";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String ILLEGITIMATE_INDICATOR = "illegitimate_indicator";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String ADOPTION = "adoption";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String PARENTS_DAY_OF_MARRIAGE = "parents_day_of_marriage";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String PARENTS_MONTH_OF_MARRIAGE = "parents_month_of_marriage";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String PARENTS_YEAR_OF_MARRIAGE = "parents_year_of_marriage";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String PARENTS_PLACE_OF_MARRIAGE = "parents_place_of_marriage";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String INFORMANT_DID_NOT_SIGN = "informant_did_not_sign";

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String INFORMANT = "informant";


    public Birth() {
        super();
    }

    public Birth(long persistent_Object_id, JSONReader reader, IRepository repository, IBucket bucket) throws PersistentObjectException, IllegalKeyException {

        super(persistent_Object_id, reader, repository, bucket);

    }

    public String get_fathers_forename() {
        return this.getString(FATHERS_FORENAME);
    }

    public String get_fathers_surname() {
        return this.getString(FATHERS_SURNAME);
    }

    public String get_mothers_forename() {
        return this.getString(MOTHERS_FORENAME);
    }

    public String get_mothers_maiden_surname() {
        return this.getString(MOTHERS_MAIDEN_SURNAME);
    }

    public String get_POM() {
        return this.getString(PARENTS_PLACE_OF_MARRIAGE);
    }

    public String get_DOM() {
        //TODO This needs cleaned properly since it is in weird formats all over.
        return this.getString(PARENTS_DAY_OF_MARRIAGE) + "/" + this.getString(PARENTS_MONTH_OF_MARRIAGE) + "/" + this.getString(PARENTS_YEAR_OF_MARRIAGE);
    }
}

// When these types were encoded as JSON and read in this was the definition from the file birthType.jsn
//{"changed_surname":"string",
//        "sex":"string",
//        "parents_month_of_marriage":"string",
//        "surname":"string",
//        "image_quality":"string",
//        "illegitimate_indicator":"string",
//        "fathers_surname":"string",
//        "fathers_occupation":"string",
//        "REGISTRATION_DISTRICT_SUFFIX":"string",
//        "ENTRY":"string",
//        "parents_place_of_marriage":"string",
//        "mothers_surname":"string",
//        "adoption":"string",
//        "forename":"string",
//        "birth_address":"string",
//        "parents_year_of_marriage":"string",
//        "REGISTRATION_DISTRICT_NUMBER":"string",
//        "birth_year":"string",
//        "informant_did_not_sign":"string",
//        "informant":"string",
//        "mothers_maiden_surname":"string",
//        "birth_month":"string",
//        "corrected_entry":"string",
//        "birth_day":"string",
//        "changed_mothers_maiden_surname":"string",
//        "changed_forename":"string",
//        "mothers_forename":"string",
//        "parents_day_of_marriage":"string",
//        "YEAR_OF_REGISTRATION":"string",
//        "fathers_forename":"string"}
