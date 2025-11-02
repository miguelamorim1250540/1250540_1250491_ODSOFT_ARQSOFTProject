package pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.infastructure;

public interface IsbnProvider {
    /**
     * Given a book title, tries to find its ISBN.
     * Returns null if not found.
     */
    String getIsbnByTitle(String title);
}
