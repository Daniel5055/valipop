package uk.ac.standrews.cs.digitising_scotland.linkage.blocking;


import uk.ac.standrews.cs.jstore.impl.TypeFactory;
import uk.ac.standrews.cs.jstore.impl.exceptions.BucketException;
import uk.ac.standrews.cs.jstore.impl.exceptions.KeyNotFoundException;
import uk.ac.standrews.cs.jstore.impl.exceptions.RepositoryException;
import uk.ac.standrews.cs.jstore.impl.exceptions.TypeMismatchFoundException;
import uk.ac.standrews.cs.jstore.interfaces.IBucket;
import uk.ac.standrews.cs.jstore.interfaces.IRepository;
import uk.ac.standrews.cs.digitising_scotland.linkage.factory.BirthFactory;
import uk.ac.standrews.cs.digitising_scotland.linkage.lxp_records.Birth;
import uk.ac.standrews.cs.digitising_scotland.linkage.stream_operators.sharder.Blocker;
import uk.ac.standrews.cs.digitising_scotland.util.ErrorHandling;

import java.io.IOException;

/**
 * This class blocks based on persons' first name, last name and first name of parents over streams of Birth records
 * Created by al on 02/05/2014. x
 */
public class FNLFFMFOverBirths extends Blocker<Birth> {

    public FNLFFMFOverBirths(final IBucket<Birth> birthsBucket,
                             final IRepository output_repo) throws BucketException, RepositoryException, IOException {

        super(birthsBucket.getInputStream(), output_repo, new BirthFactory(TypeFactory.getInstance().typeWithname("Birth").getId()));
    }

    @Override
    public String[] determineBlockedBucketNamesForRecord(final Birth record) {

        // Note will concat nulls into key if any fields are null - working hypothesis - this doesn't matter.

        StringBuilder builder = new StringBuilder();

        try {
            builder.append(record.getString(Birth.FORENAME));
            builder.append(record.getString(Birth.SURNAME));
            builder.append(record.getString(Birth.FATHERS_FORENAME));
            builder.append(record.getString(Birth.MOTHERS_FORENAME));
            return new String[]{removeNasties(builder.toString())};

        } catch (KeyNotFoundException e) {
            ErrorHandling.exceptionError(e, "Key not found");
            return new String[]{};
        } catch (TypeMismatchFoundException e) {
            ErrorHandling.exceptionError(e, "Type mismatch");
            return new String[]{};
        }
    }

    /**
     * @param key - a String key to be made into an acceptable bucket name
     * @return the cleaned up String
     */
    private String removeNasties(final String key) {
        return key.replace("/", "");
    }

}

