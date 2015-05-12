package uk.ac.standrews.cs.digitising_scotland.linkage;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.standrews.cs.jstore.impl.StoreFactory;
import uk.ac.standrews.cs.jstore.impl.TypeFactory;
import uk.ac.standrews.cs.jstore.impl.exceptions.BucketException;
import uk.ac.standrews.cs.jstore.impl.exceptions.IllegalKeyException;
import uk.ac.standrews.cs.jstore.impl.exceptions.RepositoryException;
import uk.ac.standrews.cs.jstore.impl.exceptions.StoreException;
import uk.ac.standrews.cs.jstore.interfaces.*;
import uk.ac.standrews.cs.digitising_scotland.linkage.factory.BirthFactory;
import uk.ac.standrews.cs.digitising_scotland.linkage.lxp_records.Birth;
import uk.ac.standrews.cs.digitising_scotland.linkage.stream_operators.filter.ExactMatch;
import uk.ac.standrews.cs.digitising_scotland.util.FileManipulation;
import uk.ac.standrews.cs.nds.persistence.PersistentObjectException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by al on 25/04/2014.
 */
public class PopulationTest {

    private static String birth_bucket_name1 = "BUCKET1";
    private static String birth_bucket_name2 = "BUCKET2";
    private static String generic_bucket_name1 = "BLOCKED-BUCKETS";
    private static String indexed_bucket_name1 = "INDEX";
    private static String types_name = "types";
    private static String store_path = "src/test/resources/STORE";
    private static final String BIRTH_RECORDS_PATH = "src/test/resources/1000_TEST_BIRTH_RECORDS.txt";
    private static final String BIRTHRECORDTYPETEMPLATE = "src/test/resources/birthType.jsn";

    private static IStore store;
    private static IRepository repo;
    private IBucket types;
    private IReferenceType birthlabel;

    @Before
    public void setUpEachTest() throws RepositoryException, IOException, StoreException {

        Path tempStore = Files.createTempDirectory(null);

        StoreFactory.setStorePath(tempStore);
        store = StoreFactory.makeStore();

        repo = store.makeRepository("repo");

        birthlabel = TypeFactory.getInstance().createType(Birth.class, "BIRTH");

        IBucket<Birth> b1 = repo.makeBucket(birth_bucket_name1, BucketKind.DIRECTORYBACKED);
        b1.setTypeLabelID(birthlabel.getId());
        IBucket<Birth> b2 = repo.makeBucket(birth_bucket_name2, BucketKind.DIRECTORYBACKED);
        b2.setTypeLabelID(birthlabel.getId());
        repo.makeBucket(generic_bucket_name1, BucketKind.DIRECTORYBACKED);
        IBucket<Birth> b3 = repo.makeBucket(indexed_bucket_name1, BucketKind.INDEXED);
        b3.setTypeLabelID(birthlabel.getId());
    }

    @After
    public void tearDown() throws IOException {

        deleteStore();
    }


    public void deleteStore() throws IOException {

        Path sp = Paths.get(store_path);

        if (Files.exists(sp)) {

            FileManipulation.deleteDirectory(store_path);

        }
    }

    @Test
    public synchronized void testReadingPopulationRecords() throws RepositoryException, RecordFormatException, BucketException, IOException, JSONException, PersistentObjectException, IllegalKeyException {

        IBucket<Birth> b = repo.getBucket(birth_bucket_name1, new BirthFactory(birthlabel.getId()));

        EventImporter.importDigitisingScotlandBirths(b, BIRTH_RECORDS_PATH, birthlabel);
    }

    @Test
    public synchronized void testSimpleMatchPopulationRecords() throws RepositoryException, RecordFormatException, JSONException, IOException, BucketException, PersistentObjectException, IllegalKeyException {


        IBucket<Birth> b = repo.getBucket(birth_bucket_name1, new BirthFactory(birthlabel.getId()));
        IBucket<Birth> b2 = repo.getBucket(birth_bucket_name2, new BirthFactory(birthlabel.getId()));

        EventImporter.importDigitisingScotlandBirths(b, BIRTH_RECORDS_PATH, birthlabel);

        ExactMatch filter = new ExactMatch(b.getInputStream(), b2.getOutputStream(), "surname", "GONTHWICK");
        filter.apply();
    }

}

