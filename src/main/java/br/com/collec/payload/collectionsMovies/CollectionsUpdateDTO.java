package br.com.collec.payload.collectionsMovies;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CollectionsUpdateDTO {

    @NotBlank(message = "name must not be empty")
    @Size(min = 3, message = "name must be greater than 3 letters")
    private String name;

    @NotBlank(message = "name must not be empty")
    @Size(min = 3, message = "name must be greater than 3 letters")
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
