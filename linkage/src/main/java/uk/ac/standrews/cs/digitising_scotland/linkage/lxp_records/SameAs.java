package uk.ac.standrews.cs.digitising_scotland.linkage.lxp_records;

import uk.ac.standrews.cs.jstore.impl.exceptions.IllegalKeyException;
import uk.ac.standrews.cs.jstore.types.LXPBaseType;
import uk.ac.standrews.cs.jstore.types.LXP_REF;
import uk.ac.standrews.cs.jstore.types.LXP_SCALAR;
import uk.ac.standrews.cs.nds.persistence.PersistentObjectException;
import uk.ac.standrews.cs.nds.rpc.stream.JSONReader;

/**
 * Created by al on 03/10/2014.
 */
public class SameAs extends AbstractLXP {

    @LXP_REF(type = "Person")
    public static final String FIRST = "first";
    @LXP_REF(type = "Person")
    public static final String SECOND = "second";
    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static final String RELATIONSHIP = "relationship";
    @LXP_SCALAR(type = LXPBaseType.DOUBLE)
    public static final String CONFIDENCE = "confidence";


    public SameAs(Role first, Role second, String relationship, float confidence) {

        super();
        try {
            put(FIRST, Long.toString(first.getId()));
            put(SECOND, Long.toString(second.getId()));
            put(RELATIONSHIP, relationship);
            put(CONFIDENCE, Float.toString(confidence));
        } catch (IllegalKeyException e) {
            // cannot occur so ignore
        }
    }

    public SameAs(long persistent_Object_id, JSONReader reader, long required_label_id) throws PersistentObjectException, IllegalKeyException {

        super(persistent_Object_id, reader);
    }

    //TODO write methods.

}

// When these types were encoded as JSON and read in this was the definition from the file same-asType.jsn
//{"first","lxp",
//        "second","lxp",
//        "resolver","string",
//        "relationship","string",
//        "confidence","float"}

