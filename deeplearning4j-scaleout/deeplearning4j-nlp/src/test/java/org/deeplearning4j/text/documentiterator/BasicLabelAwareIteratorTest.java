package org.deeplearning4j.text.documentiterator;

import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.UimaSentenceIterator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by fartovii on 27.11.15.
 */
public class BasicLabelAwareIteratorTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testHasNextDocument1() throws Exception {

        File inputFile = new ClassPathResource("/big/raw_sentences.txt").getFile();
        SentenceIterator iter = new BasicLineIterator(inputFile.getAbsolutePath());

        BasicLabelAwareIterator iterator = new BasicLabelAwareIterator.Builder(iter)
                .setLabelTemplate("DOCZ_")
                .build();

        int cnt = 0;
        while (iterator.hasNextDocument()) {
            iterator.nextDocument();
            cnt++;
        }

        assertEquals(97162, cnt);

        LabelsSource generator = iterator.getLabelsSource();

        assertEquals(97162, generator.getLabels().size());
        assertEquals("DOCZ_0", generator.getLabels().get(0));
    }

    @Test
    public void testHasNextDocument2() throws Exception {

        File inputFile = new ClassPathResource("/big/raw_sentences.txt").getFile();
        SentenceIterator iter = new BasicLineIterator(inputFile.getAbsolutePath());

        BasicLabelAwareIterator iterator = new BasicLabelAwareIterator.Builder(iter)
                .setLabelTemplate("DOCZ_")
                .build();

        int cnt = 0;
        while (iterator.hasNextDocument()) {
            iterator.nextDocument();
            cnt++;
        }

        assertEquals(97162, cnt);

        iterator.reset();

        cnt = 0;
        while (iterator.hasNextDocument()) {
            iterator.nextDocument();
            cnt++;
        }

        assertEquals(97162, cnt);

        LabelsSource generator = iterator.getLabelsSource();

        // this is important moment. Iterator after reset should not increase number of labels attained
        assertEquals(97162, generator.getLabels().size());
        assertEquals("DOCZ_0", generator.getLabels().get(0));
    }
}