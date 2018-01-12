package  SearchEngine;

import DataSchema.BooksEntity;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa zarządzająca wyszukiwaniem książek
 */
public class BookSearchEngine {
    /**
     *
     * @param books lista książek z bazy danuych
     * @param input wprowadzone hasło na podstawie którego wyszukiwane będą książki
     * @return zwraca mapę znalezionych książek
     */
    public static Map<String,FoundBook> findBook(List<BooksEntity> books, String input) throws IOException, ParseException {
        // 0. Specify the analyzer for tokenizing text.
        //    The same analyzer should be used for indexing and searching
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);

        // 1. create the index
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);

        IndexWriter w = new IndexWriter(index, config);
        for (BooksEntity book: books) {
            addDoc(w, book.getTitle(), book.getIsbn(),book.getAuthors());
        }
        /*addDoc(w, "Lord of the Ring", "193398817");
        addDoc(w, "Lucene for Dummies", "55320055Z");
        addDoc(w, "Managing Gigabytes", "55063554A");
        addDoc(w, "The Art of Computer Science", "9900333X");*/
        w.close();

        // 2. query
        //String querystr = args.length > 0 ? args[0] : "ring";
        Map<String, FoundBook> allFoundBooks = new HashMap<>();
        // the "title" arg specifies the default field to use
        // when no field is explicitly specified in the query.
        //System.out.println(input);
        String[] splitInput = input.split(" ");
        for (String split:splitInput) {
            if(split.length() > 3){
                Query q = new QueryParser(Version.LUCENE_40, "title", analyzer).parse(split);
                int hitsPerPage = 10;
                IndexReader reader = DirectoryReader.open(index);
                IndexSearcher searcher = new IndexSearcher(reader);
                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
                searcher.search(q, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;
                //ystem.out.println("Found " + hits.length + " hits.");
                for(int i=0;i<hits.length;++i) {
                    int docId = hits[i].doc;
                    Document d = searcher.doc(docId);
                    FoundBook tmp = new FoundBook(d.get("title"),d.get("authors"),d.get("isbn"));
                    allFoundBooks.put(d.get("isbn"),tmp);
                    //System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
                }

                // reader can only be closed when there
                // is no need to access the documents any more.
                reader.close();
            }

        }
        for (String split:splitInput) {
            if(split.length() > 3){
                Query q = new QueryParser(Version.LUCENE_40, "authors", analyzer).parse(split);
                int hitsPerPage = 10;
                IndexReader reader = DirectoryReader.open(index);
                IndexSearcher searcher = new IndexSearcher(reader);
                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
                searcher.search(q, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;
                //System.out.println("Found " + hits.length + " hits.");
                for(int i=0;i<hits.length;++i) {
                    int docId = hits[i].doc;
                    Document d = searcher.doc(docId);
                    FoundBook tmp = new FoundBook(d.get("title"),d.get("authors"),d.get("isbn"));
                    allFoundBooks.put(d.get("isbn"),tmp);
                    //System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
                }

                // reader can only be closed when there
                // is no need to access the documents any more.
                reader.close();
            }

        }
        //Query q = new QueryParser(Version.LUCENE_40, "title", analyzer).parse(querystr);

        // 3. search


        // 4. display results

        return allFoundBooks;
    }

    private static void addDoc(IndexWriter w, String title, String isbn, String authors) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new TextField("authors", authors, Field.Store.YES));
        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        w.addDocument(doc);
    }
}