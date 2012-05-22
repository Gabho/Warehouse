package topology.activeobject;

import java.io.Serializable;
import java.util.List;

/**
 * Trieda reprezentujúca výsledok vyhľadávania.
 *
 * @author Gabriel Cervenak
 */
public class SearchResult implements Serializable {

    private List<String> searchResult;

    /**
     * Vytvorenie nového výsledku vyhľadávania.
     *
     * @param masterData zoznam reťazcov, ktoré sú výsledkom vyhľadávania.
     */
    public SearchResult(List<String> searchResult) {
        this.searchResult = searchResult;
    }

    /**
     * Vracia zoznam výsledkov vyhľadávania..
     *
     * @return zoznam reťazcov, predstavujúci výsledok vyhľadávania.
     */
    public List<String> getSearchResult() {
        return searchResult;
    }
}
