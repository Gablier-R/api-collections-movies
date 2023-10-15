package br.com.collec.payload.collectionsMovies;

public class CollectionsUpdateDTO {

    private String name;
    private String resume;

    public CollectionsUpdateDTO(String name, String resume) {
        this.name = name;
        this.resume = resume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
