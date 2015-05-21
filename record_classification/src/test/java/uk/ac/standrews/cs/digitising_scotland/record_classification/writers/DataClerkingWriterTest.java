/*
 * Copyright 2015 Digitising Scotland project:
 * <http://digitisingscotland.cs.st-andrews.ac.uk/>
 *
 * This file is part of the module record_classification.
 *
 * record_classification is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * record_classification is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with record_classification. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package uk.ac.standrews.cs.digitising_scotland.record_classification.writers;

import org.junit.*;
import uk.ac.standrews.cs.digitising_scotland.record_classification.classifiers.ClassifierTestingHelper;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.bucket.Bucket;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.code.CodeNotValidException;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.records.Record;
import uk.ac.standrews.cs.digitising_scotland.record_classification.datastructures.records.RecordFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * The Class DataClerkingWriterTest.
 */
@Ignore
public class DataClerkingWriterTest {

    /** The helper. */
    private ClassifierTestingHelper helper = new ClassifierTestingHelper();

    /** The Constant OCCBUCKET. */
    private static final String OCCBUCKET = "target/OccRecordWriteTest.txt";

    /** The Constant CODDATA. */
    private static final String CODDATA = "/DataClerkingWriterTestCOD.txt";

    /** The Constant CODBUCKET. */
    private static final String CODBUCKET = "target/CODRecordWriteTest.txt";

    /** The Constant HICODBUCKET. */
    private static final String HICODBUCKET = "target/HICODRecordWriteTest.txt";

    /** The Constant MULTICODBUCKET. */
    private static final String MULTICODBUCKET = "target/MultipleCODRecordWriteTest.txt";

    @Before
    public void setUp() {

        RecordFactory.resetIdCount();

    }

    /**
     * Clean up.
     */
    @AfterClass
    public static void cleanUp() {

                File file = new File(OCCBUCKET);
                Assert.assertTrue(file.delete());
                file = new File(CODBUCKET);
                Assert.assertTrue(file.delete());
                file = new File(HICODBUCKET);
                Assert.assertTrue(file.delete());
                file = new File(MULTICODBUCKET);
                Assert.assertTrue(file.delete());
    }

    /**
     * Test write occupation records with codes.
     *
     * @throws Exception the exception
     */
    @Test
    public void testWriteOcc() throws Exception, CodeNotValidException {

        String occDataFile = "/DataClerkingWriterTestOcc.txt";
        File writeFile = createAndWriteOccBucketWithClassificationTriplesToFile(OCCBUCKET, occDataFile);
        String correctOccBucketFile = "/OccRecordWriteCorrect.txt";
        checkFileAgainstKnownCorrect(correctOccBucketFile, writeFile);
    }

    /**
     * Test write cod.
     *
     * @throws Exception the exception
     */
    @Ignore
    @Test
    public void testWriteCOD() throws Exception {

        File writeFile = createAndWriteCODBucketToFile(CODBUCKET, CODDATA);
        String correctCODBucketFile = "/CODRecordWriteCorrect.txt";
        checkFileAgainstKnownCorrect(correctCODBucketFile, writeFile);
    }

    /**
     * Test write hicod.
     *
     * @throws Exception the exception
     */
    @Ignore
    @Test
    public void testWriteHICOD() throws Exception {

        File writeFile = createAndWriteHICODBucketToFile(HICODBUCKET, CODDATA);
        String correctHICODBucketFile = "/HICODRecordWriteCorrect.txt";
        checkFileAgainstKnownCorrect(correctHICODBucketFile, writeFile);
    }

    /**
     * Test write multiple cod.
     *
     * @throws Exception the exception
     */
    @Ignore
    @Test
    public void testWriteMultipleCOD() throws Exception {

        File writeFile = createAndWriteMultipleCODBucketToFile(MULTICODBUCKET, CODDATA);
        String correctMultipleCODBucketFile = "/MultipleCODWriteCorrect.txt";
        checkFileAgainstKnownCorrect(correctMultipleCODBucketFile, writeFile);
    }

    /**
     * Creates the and write multiple cod bucket to file.
     *
     * @param writeFileName the write file name
     * @param readFileName the read file name
     * @return the file
     * @throws Exception the exception
     */
    private File createAndWriteMultipleCODBucketToFile(final String writeFileName, final String readFileName) throws Exception {

        File writeFile = new File(writeFileName);
        DataClerkingWriter dataClerkingWriter = new DataClerkingWriter(writeFile);
        Bucket bucket = helper.getTrainingBucket(readFileName);
        bucket = addMultipleCODCodes(bucket);
        writeToFile(dataClerkingWriter, bucket);
        return writeFile;
    }

    /**
     * Creates the and write hicod bucket to file.
     *
     * @param writeFileName the write file name
     * @param readFileName the read file name
     * @return the file
     * @throws Exception the exception
     */
    private File createAndWriteHICODBucketToFile(final String writeFileName, final String readFileName) throws Exception {

        File writeFile = new File(writeFileName);
        DataClerkingWriter dataClerkingWriter = new DataClerkingWriter(writeFile);
        Bucket bucket = helper.getTrainingBucket(readFileName);
        bucket = addHICODCodes(bucket);
        writeToFile(dataClerkingWriter, bucket);
        return writeFile;
    }

    /**
     * Creates the and write occ bucket to file.
     *
     * @param writeFileName the write file name
     * @param readFileName the read file name
     * @return the file
     * @throws Exception the exception
     */
    private File createAndWriteOccBucketToFile(final String writeFileName, final String readFileName) throws Exception, CodeNotValidException {

        File writeFile = new File(writeFileName);
        DataClerkingWriter dataClerkingWriter = new DataClerkingWriter(writeFile);
        Bucket bucket = helper.getTrainingBucket(readFileName);
        bucket = addOccCodes(bucket);
        writeToFile(dataClerkingWriter, bucket);
        return writeFile;
    }

    /**
     * Creates the and write occ bucket to file.
     *
     * @param writeFileName the write file name
     * @param readFileName the read file name
     * @return the file
     * @throws Exception the exception
     */
    private File createAndWriteOccBucketWithClassificationTriplesToFile(final String writeFileName, final String readFileName) throws Exception, CodeNotValidException {

        File writeFile = new File(writeFileName);
        DataClerkingWriter dataClerkingWriter = new DataClerkingWriter(writeFile);
        Bucket bucket = helper.getTrainingBucket(readFileName);
        bucket = addOccCodes(bucket);
        bucket = addOccClassificationTriples(bucket);
        writeToFile(dataClerkingWriter, bucket);
        return writeFile;
    }

    /**
     * Creates the and write cod bucket to file.
     *
     * @param writeFileName the write file name
     * @param readFileName the read file name
     * @return the file
     * @throws Exception the exception
     */
    private File createAndWriteCODBucketToFile(final String writeFileName, final String readFileName) throws Exception {

        File writeFile = new File(writeFileName);
        DataClerkingWriter dataClerkingWriter = new DataClerkingWriter(writeFile);
        Bucket bucket = helper.getTrainingBucket(readFileName);
        bucket = addCODCodes(bucket);
        writeToFile(dataClerkingWriter, bucket);
        return writeFile;
    }

    /**
     * Check file against known correct.
     *
     * @param correctFileName the correct file name
     * @param writeFile the write file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void checkFileAgainstKnownCorrect(final String correctFileName, final File writeFile) throws IOException {

        File correctFile = new File(getClass().getResource(correctFileName).getFile());
        byte[] f1 = Files.readAllBytes(writeFile.toPath());
        byte[] f2 = Files.readAllBytes(correctFile.toPath());
        Assert.assertArrayEquals(f2, f1);
    }

    /**
     * Adds the cod codes.
     *
     * @param bucket the bucket
     * @return the bucket
     * @throws URISyntaxException the URI syntax exception
     * @throws IOException 
     */
    private Bucket addCODCodes(final Bucket bucket) throws URISyntaxException, IOException {

        return helper.giveBucketTestingCODCodes(bucket);
    }

    /**
     * Adds the hicod codes.
     *
     * @param bucket the bucket
     * @return the bucket
     * @throws URISyntaxException the URI syntax exception
     * @throws IOException 
     */
    private Bucket addHICODCodes(final Bucket bucket) throws URISyntaxException, IOException {

        return helper.giveBucketTestingHICODCodes(bucket, "I6191");
    }

    /**
     * Adds the multiple cod codes.
     *
     * @param bucket the bucket
     * @return the bucket
     * @throws URISyntaxException the URI syntax exception
     * @throws IOException 
     */
    private Bucket addMultipleCODCodes(final Bucket bucket) throws URISyntaxException, IOException {

        String code = "R99";
        helper.giveBucketTestingHICODCodes(bucket, code);

        code = "I6191";
        helper.giveBucketTestingHICODCodes(bucket, code);

        code = "X59";
        helper.giveBucketTestingHICODCodes(bucket, code);

        return bucket;
    }

    /**
     * Adds the occ codes.
     *
     * @param bucket the bucket
     * @return the bucket
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws CodeNotValidException the code not valid exception
     * @throws URISyntaxException the URI syntax exception
     */
    private Bucket addOccCodes(final Bucket bucket) throws IOException, CodeNotValidException, URISyntaxException {

        return helper.giveBucketTestingOccCodes(bucket);
    }

    /**
     * Adds the occ codes.
     *
     * @param bucket the bucket
     * @return the bucket
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws CodeNotValidException the code not valid exception
     * @throws URISyntaxException the URI syntax exception
     */
    private Bucket addOccClassificationTriples(final Bucket bucket) throws IOException, CodeNotValidException, URISyntaxException {

        return helper.giveBucketTestingOccClassificationTriples(bucket);
    }

    /**
     * Write to file.
     *
     * @param dataClerkingWriter the data clerking writer
     * @param bucket the bucket
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writeToFile(final DataClerkingWriter dataClerkingWriter, final Bucket bucket) throws IOException {

        for (Record record : bucket) {
            dataClerkingWriter.write(record);
        }
        dataClerkingWriter.close();
    }
}
