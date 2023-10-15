package br.com.collec.payload.movie;

public class MoviesDataDTO {

    private String url;

    public MoviesDataDTO(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
