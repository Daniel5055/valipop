package uk.ac.standrews.cs.digitising_scotland.linkage.factory;


import uk.ac.standrews.cs.jstore.impl.exceptions.IllegalKeyException;
import uk.ac.standrews.cs.jstore.interfaces.ILXPFactory;
import uk.ac.standrews.cs.digitising_scotland.linkage.lxp_records.Marriage;
import uk.ac.standrews.cs.nds.persistence.PersistentObjectException;
import uk.ac.standrews.cs.nds.rpc.stream.JSONReader;

/**
 * Created by al on 03/10/2014.
 */
public class MarriageFactory extends TFactory<Marriage> implements ILXPFactory<Marriage> {


    public MarriageFactory(long marriagelabelID) {
        this.required_type_labelID = marriagelabelID;
    }


    @Override
    public Marriage create(long label_id, JSONReader reader) throws PersistentObjectException, IllegalKeyException {
        return new Marriage(label_id, reader);
    }

}
