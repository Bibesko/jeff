/*
 * Copyright 2009 Nemanja Jovanovic
 *
 * This file is part of JEFF (Java Explanation Facility Framework).
 *
 * JEFF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JEFF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with JEFF.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.goodoldai.jeff.report.txt;

import org.goodoldai.jeff.AbstractJeffTest;
import org.goodoldai.jeff.explanation.DataExplanationChunk;
import org.goodoldai.jeff.explanation.TextExplanationChunk;
import org.goodoldai.jeff.explanation.data.Dimension;
import org.goodoldai.jeff.explanation.data.OneDimData;
import org.goodoldai.jeff.explanation.data.SingleData;
import org.goodoldai.jeff.explanation.data.ThreeDimData;
import org.goodoldai.jeff.explanation.data.Triple;
import org.goodoldai.jeff.explanation.data.Tuple;
import org.goodoldai.jeff.explanation.data.TwoDimData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author Nemanja Jovanovic
 */
public class TXTDataChunkBuilderTest extends AbstractJeffTest {

    DataExplanationChunk singleDataChunk;
    DataExplanationChunk singleDataChunk1;
    DataExplanationChunk oneDimDataChunk;
    DataExplanationChunk twoDimDataChunk;
    DataExplanationChunk threeDimDataChunk;
    BufferedReader br;
    PrintWriter pw;
    TXTDataChunkBuilder instance;

    /**
     * Creates instances that are used for testing
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();

        instance = new TXTDataChunkBuilder();

        String[] tags = {"tag1", "tag2"};
        singleDataChunk1 = new DataExplanationChunk(new SingleData(new Dimension("testName"), "value"));
        singleDataChunk = new DataExplanationChunk(-10, "testGroup", "testRule", tags,
                new SingleData(new Dimension("testName", "testUnit"), "value"));

        ArrayList<Object> values = new ArrayList<Object>();
        values.add("value1");
        values.add("value2");
        oneDimDataChunk = new DataExplanationChunk(-10, "testGroup", "testRule", tags,
                new OneDimData(new Dimension("testName", "testUnit"), values));


        ArrayList<Tuple> tupleValues = new ArrayList<Tuple>();
        tupleValues.add(new Tuple("value1", "value2"));
        twoDimDataChunk = new DataExplanationChunk(-10, "testGroup", "testRule", tags,
                new TwoDimData(new Dimension("testName1", "testUnit1"), new Dimension("testName2", "testUnit2"), tupleValues));

        ArrayList<Triple> tripleValues = new ArrayList<Triple>();
        tripleValues.add(new Triple("value1", "value2", "value3"));
        tripleValues.add(new Triple("value4", "value5", "value6"));
        threeDimDataChunk = new DataExplanationChunk(-10, "testGroup", "testRule", tags, new ThreeDimData(
                new Dimension("testName1", "testUnit1"),
                new Dimension("testName2", "testUnit2"),
                new Dimension("testName3", "testUnit3"),
                tripleValues));

        pw = new PrintWriter(new File("tekst.txt"));
        br = new BufferedReader(new FileReader("tekst.txt"));


    }

    @After
    public void tearDown() throws IOException {

        pw.close();
        br.close();

        instance = null;
        new File("tekst.txt").delete();

        singleDataChunk = null;
        oneDimDataChunk = null;
        twoDimDataChunk = null;
        threeDimDataChunk = null;


    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: unsuccessful building of a chunk because of the null arguments
     */
    @Test
    public void testBuildReportChunkAllNullArguments() {
        try {
            instance.buildReportChunk(null, null, false);
            fail("Exception should have been thrown, but it wasn't");
        } catch (Exception e) {
            String result = e.getMessage();
            String expResult = "All of the arguments are mandatory, so they can not be null";
            assertTrue(e instanceof org.goodoldai.jeff.explanation.ExplanationException);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: unsuccessful building of a chunk because of the first null argument
     */
    @Test
    public void testBuildReportChunkMissingFirstArgumant() {
        try {
            instance.buildReportChunk(null, pw, false);
            fail("Exception should have been thrown, but it wasn't");
        } catch (Exception e) {
            String result = e.getMessage();
            String expResult = "The argument 'echunk' is mandatory, so it can not be null";
            assertTrue(e instanceof org.goodoldai.jeff.explanation.ExplanationException);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: unsuccessful building of a chunk because of the second null argument
     */
    @Test
    public void testBuildReportChunkMissingSecondArgumant() {
        try {
            instance.buildReportChunk(singleDataChunk, null, false);
            fail("Exception should have been thrown, but it wasn't");
        } catch (Exception e) {
            String result = e.getMessage();
            String expResult = "The argument 'stream' is mandatory, so it can not be null";
            assertTrue(e instanceof org.goodoldai.jeff.explanation.ExplanationException);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: unsuccessful building of a chunk because of the wrong
     * type of the first argument
     */
    @Test
    public void testBuildReportChunkWrongTypeFirsArgumant() {
        try {
            instance.buildReportChunk(new TextExplanationChunk("test"), pw, false);
            fail("Exception should have been thrown, but it wasn't");
        } catch (Exception e) {
            String result = e.getMessage();
            String expResult = "The ExplanationChunk must be type of DataExplanationChunk";
            assertTrue(e instanceof org.goodoldai.jeff.explanation.ExplanationException);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: unsuccessful building of a chunk because of the wrong
     * type of the second argument
     */
    @Test
    public void testBuildReportChunkWrongTypeSecondArgumant() {
        try {
            instance.buildReportChunk(singleDataChunk, "test", false);
            fail("Exception should have been thrown, but it wasn't");
        } catch (Exception e) {
            String result = e.getMessage();
            String expResult = "The argument 'stream' must be the type of java.io.PrintWriter";
            assertTrue(e instanceof org.goodoldai.jeff.explanation.ExplanationException);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: successful insertion of data using the ExplanationChunk constructor
     * that has only content and the type is SingleDimDataChunk.
     */
    @Test
    public void testBuildReportChunkFirsttConstructorSingleData() throws IOException {
        instance.buildReportChunk(singleDataChunk1, pw, true);
        pw.close();

        pw.close();

        //checks if the file is created
        assertTrue(new File("tekst.txt").exists());

        //skips the lines in document that are tested elsewhere
        br.readLine();
        
        //checks the content
        assertEquals("testName", br.readLine());
        assertEquals("-------------------", br.readLine());
        assertEquals("value", br.readLine());
        assertEquals("", br.readLine());

        //checks if anyting eles has been writen to file by mistake
        assertEquals(null, br.readLine());
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: successful insertion of data using the ExplanationChunk constructor
     * that has all elements and the type is SingleDimDataChunk.
     */
    @Test
    public void testBuildReportChunkFirsttConstructorSingleDataAllData() throws IOException {
        instance.buildReportChunk(singleDataChunk, pw, true);
        pw.close();

        pw.close();

        //checks if the file is created
        assertTrue(new File("tekst.txt").exists());

        //skips the lines in document that are tested else were
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
         
        //checks the content
        assertEquals("testName [testUnit]", br.readLine());
        assertEquals("-------------------", br.readLine());
        assertEquals("value", br.readLine());
        assertEquals("", br.readLine());

        //checks if anyting eles has been writen to file by mistake
        assertEquals(null, br.readLine());
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: successful insertion of data using the ExplanationChunk constructor
     * that has all elements and the type is SingleDimDataChunk, but with no headers.
     */
    @Test
    public void testBuildReportChunkFirsttConstructorSingleDataAllDataNoHeaders() throws IOException {
        instance.buildReportChunk(singleDataChunk, pw, false);
        pw.close();

        pw.close();

        //checks if the file is created
        assertTrue(new File("tekst.txt").exists());

        //checks the content
        assertEquals("testName [testUnit]", br.readLine());
        assertEquals("-------------------", br.readLine());
        assertEquals("value", br.readLine());
        assertEquals("", br.readLine());

        //checks if anyting eles has been writen to file by mistake
        assertEquals(null, br.readLine());
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: successful insertion of data using the ExplanationChunk constructor
     * that has all elements and the type is OneDimDataChunk.
     */
    @Test
    public void testBuildReportChunkSecondConstructorOneDimData() throws IOException {
        instance.buildReportChunk(oneDimDataChunk, pw, true);
        pw.close();

        pw.close();

        //checks if the file is created
        assertTrue(new File("tekst.txt").exists());

        //skips the lines in document that are tested else were
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
        
        //checks the content
        assertEquals("testName [testUnit]", br.readLine());
        assertEquals("-------------------", br.readLine());
        assertEquals("value1", br.readLine());
        assertEquals("value2", br.readLine());
        assertEquals("", br.readLine());

        //checks if anyting else has been writen to file by mistake
        assertEquals(null, br.readLine());
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: successful insertion of data using the ExplanationChunk constructor
     * that has all elements and the type is TwoDimDataChunk.
     */
    @Test
    public void testBuildReportChunkSecondConstructorTwoDimData() throws IOException {
        instance.buildReportChunk(twoDimDataChunk, pw, true);
        pw.close();

        pw.close();

        //checks if the file is created
        assertTrue(new File("tekst.txt").exists());

        //skips the lines in document that are tested else were
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
        
        //checks the content
        assertEquals("testName1 [testUnit1]       testName2 [testUnit2]", br.readLine());
        assertEquals("-------------------", br.readLine());
        assertEquals("value1       value2", br.readLine());
        assertEquals("", br.readLine());

        //checks if anyting eles has been writen to file by mistake
        assertEquals(null, br.readLine());
    }

    /**
     * Test of buildReportChunk method, of class TXTDataChunkBuilder.
     * Test case: successful insertion of data using the ExplanationChunk constructor
     * that has all elements and the type is ThreeDimDataChunk.
     */
    @Test
    public void testBuildReportChunkSecondConstructorThreeDimData() throws IOException {
        instance.buildReportChunk(threeDimDataChunk, pw, true);
        pw.close();

        pw.close();

        //checks if the file is created
        assertTrue(new File("tekst.txt").exists());

        //skips the lines in document that are tested else were
        br.readLine();
        br.readLine();
        br.readLine();
        br.readLine();
        
        //checks the content
        assertEquals("testName1 [testUnit1]       testName2 [testUnit2]       testName3 [testUnit3]", br.readLine());
        assertEquals("-------------------", br.readLine());
        assertEquals("value1       value2       value3", br.readLine());
        assertEquals("value4       value5       value6", br.readLine());
        assertEquals("", br.readLine());

        //checks if anyting eles has been writen to file by mistake
        assertEquals(null, br.readLine());
    }
}
