package it.eng.saceriam.ws.restituzioneNewsApplicazione.dto;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Gilioli_P
 */
public class ListaNews implements Iterable<News> {
    private List<News> news;

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public Iterator<News> iterator() {
        return news.iterator();
    }
}
