import edu.uwb.css143b2020fall.service.Indexer;
import edu.uwb.css143b2020fall.service.IndexerImpl;
import edu.uwb.css143b2020fall.service.Searcher;
import edu.uwb.css143b2020fall.service.SearcherImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private Indexer indexer;
    private Searcher searcher;

    @Before
    public void setUp() {
        indexer = new IndexerImpl();
        searcher = new SearcherImpl();
    }

    @Test
    public void testIntegration() {
        List<TestCase> cases = getTestCase();
        for (TestCase testCase : cases) {
            List<Integer> actual = searcher.search(
                    testCase.target,
                    indexer.index(testCase.documents)
            );
            assertEquals(testCase.expect, actual);
        }
    }

    @Test
    public void anotherTestIntegration(){
        List<TestCase> cases = getNewTestCase();
        for(TestCase case_ : cases){
            List<Integer> actual = searcher.search(
                    case_.target,
                    indexer.index(case_.documents)
            );
            assertEquals(case_.expect, actual);
        }
    }

    private List<TestCase> getTestCase() {
        List<String> documents = Util.getDocumentsForIntTest();

        List<TestCase> testCases = new ArrayList<>(Arrays.asList(
                new TestCase(
                        documents,
                        "",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "hello world",
                        new ArrayList<>(Arrays.asList(0, 1, 6))
                ),
                new TestCase(
                        documents,
                        "llo wor",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "wor",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "hello",
                        new ArrayList<>(Arrays.asList(0, 1, 2, 4, 5, 6))
                ),
                new TestCase(
                        documents,
                        "just world",
                        new ArrayList<>(Arrays.asList(0))
                ),
                new TestCase(
                        documents,
                        "sunday",
                        new ArrayList<>(Arrays.asList(6))
                ),
                new TestCase(
                        documents,
                        "hello world fun",
                        new ArrayList<>(Arrays.asList(6))
                ),
                new TestCase(
                        documents,
                        "world world fun",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "office",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "ryan murphy",
                        Util.emptyResult()
                ),
                new TestCase(
                        documents,
                        "new macbook",
                        new ArrayList<>(Arrays.asList(7))
                ),
                new TestCase(
                        documents,
                        "is awesome",
                        new ArrayList<>(Arrays.asList(7))
                )
        ));

        return testCases;
    }

    private List<TestCase> getNewTestCase(){
        List<String> documentNames = new ArrayList<>(
                Arrays.asList(
                "this is a document",           //0 regular name
                "this is a test",               //1 matching some words from the last name
                "this this this is a repeat",   //2 single repeating word
                "test a document",              //3 regular name
                "document",                     //4 single word
                "this is   this",               //5 white space and repeating word
                "this a this a test document"   //6 repeating phrase
            )
        );

        List<TestCase> cases = new ArrayList<>(Arrays.asList(
                new TestCase(
                        documentNames,
                        "this",
                        new ArrayList<>(Arrays.asList(0, 1, 2, 5, 6))
                ),
                new TestCase(
                        documentNames,
                        "document",
                        new ArrayList<>(Arrays.asList(0, 3, 4, 6))
                ),
                new TestCase(
                        documentNames,
                        "this this",
                        new ArrayList<>(Arrays.asList(2))
                ),
                new TestCase(
                        documentNames,
                        "is this",
                        new ArrayList<>(Arrays.asList(5))
                ),
                new TestCase(
                        documentNames,
                        "this is a document",
                        new ArrayList<>(Arrays.asList(0))
                ),
                new TestCase(
                        documentNames,
                        "this does not exist",
                        new ArrayList<>()
                )
            )
        );

        return cases;
    }

    private class TestCase {
        private List<String> documents;
        private String target;
        private List<Integer> expect;

        public TestCase(List<String> documents, String target, List<Integer> expect) {
            this.documents = documents;
            this.target = target;
            this.expect = expect;
        }
    }
}