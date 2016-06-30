package hr.fer.zemris.java.hw12.trazilica;

import hr.fer.zemris.java.hw12.trazilica.tfidf.TfIdfVector;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * {@code Dictionary} represents dictionary which hold all defined words trough
 * all provided documents. When this object is created, {@link TfIdfVector}s for
 * all documents are being created and stored for further using. This dictionary
 * also holds stop-words for current language.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public class Dictionary {

    /**
     * {@code FileVisitor} used for filling this {@code Dictionary} with
     * vocabulary words defined by documents specified by root file path.
     * 
     * @author Domagoj Penic
     * @version 2.6.2015.
     *
     */
    private static class VocabularyCreator implements FileVisitor<Path> {

        /** Vocabulary collection. */
        private Set<String> vocabulary;
        /** Collection of stop-words of current language. */
        private Set<String> stopWords;
        /** Collection of paths associated with its words. */
        private Map<Path, List<String>> documents;

        /**
         * Creates new {@code VocabularyCreator} with specified collection of
         * vocabulary to be filled when some file is being visited.
         * 
         * @param vocabulary
         *            Collection of vocabulary words to be filled
         * @param stopWords
         *            Collection of stop-words
         * @param documents
         *            Paths associated with document words to be filled
         */
        public VocabularyCreator(Set<String> vocabulary, Set<String> stopWords,
                Map<Path, List<String>> documents) {
            this.vocabulary = vocabulary;
            this.stopWords = stopWords;
            this.documents = documents;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir,
                BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {
            // Read text from file
            String text = new String(Files.readAllBytes(file),
                    StandardCharsets.UTF_8);
            text = text.toLowerCase();

            // Extract words from file
            String[] words = text.replaceAll("[^a-zA-ZšđžčćŠĐŽČĆ]", " ").split(
                    "\\s+");
            List<String> documentWords = new ArrayList<>();

            for (String word : words) {
                word = word.trim();

                if (!word.isEmpty() && !stopWords.contains(word)) {
                    vocabulary.add(word);
                    documentWords.add(word);
                }
            }

            documents.put(file, documentWords);

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc)
                throws IOException {
            return FileVisitResult.CONTINUE;
        }

    }

    /** Vocabulary collection of all words found. */
    private Set<String> vocabulary;
    /** Stop-words of current language. */
    private Set<String> stopWords;

    /** Paths associated with all document words. */
    private Map<Path, List<String>> documents;
    /** Paths associated with file vectors. */
    private Map<Path, TfIdfVector> vectors;

    /** Results of last query specified by user. */
    private Map<Double, Path> currentResults;

    /**
     * Creates new {@code Dictionary} by specified language which is defined by
     * all files which root file is provided root directory path. As a part of
     * {@code Dictionary}, language stop-words have to be also provided.
     * 
     * @param rootDirectoryPath
     *            Root path of all text files
     * @param stopWordsPath
     *            Path of file which contains language stop-words
     * @throws IllegalArgumentException
     *             If one of paths is invalid
     * @throws IOException
     *             If IO error occurs
     */
    public Dictionary(String rootDirectoryPath, String stopWordsPath)
            throws IllegalArgumentException, IOException {
        Path rootDirectory = Paths.get(rootDirectoryPath);

        if (!Files.exists(rootDirectory) || !Files.isDirectory(rootDirectory)) {
            throw new IllegalArgumentException(String.format(
                    "Directory with specified path " + "does not exists: %s.",
                    rootDirectory));
        }

        Path stopWords = Paths.get(stopWordsPath);

        if (!Files.exists(rootDirectory) || !Files.isReadable(rootDirectory)) {
            throw new IllegalArgumentException(String.format(
                    "File with specified path " + "does not exists: %s.",
                    stopWords));
        }

        this.vocabulary = new HashSet<>();
        this.stopWords = new HashSet<>(Files.readAllLines(stopWords));
        this.documents = new HashMap<>();
        this.vectors = new HashMap<>();
        this.currentResults = new TreeMap<>(Collections.reverseOrder());

        // Create vocabulary
        VocabularyCreator visitor = new VocabularyCreator(this.vocabulary,
                this.stopWords, documents);
        Files.walkFileTree(rootDirectory, visitor);

        // Create vectors
        for (Path file : documents.keySet()) {
            vectors.put(file, new TfIdfVector(documents.get(file), vocabulary,
                    this.documents));
        }

    }

    /**
     * Returns vocabulary collection of this {@code Dictionary}.
     * 
     * @return This {@code Dictionary} vocabulary
     */
    public Set<String> getVocabulary() {
        return vocabulary;
    }

    /**
     * Returns all {@code Paths} associated with its collection of words.
     * 
     * @return Collection of all documents
     */
    public Map<Path, List<String>> getDocuments() {
        return documents;
    }

    /**
     * Returns all {@code Paths} associated with its {@code TfIdfVector}s
     * created when this {@code Dictionary} is being instanced.
     * 
     * @return Collection of all file vectors
     */
    public Map<Path, TfIdfVector> getVectors() {
        return vectors;
    }

    /**
     * Returns similarities associated with file paths as a result of last
     * query. If this call is made before query command, this collection will be
     * empty.
     * 
     * @return Collection of current results
     */
    public Map<Double, Path> getCurrentResults() {
        return currentResults;
    }

}
