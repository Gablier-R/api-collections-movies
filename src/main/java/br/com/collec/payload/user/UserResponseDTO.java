package br.com.collec.payload.user;

import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;

import java.util.List;

public class UserResponseDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private List<CollectionsResponseDTO> collectionsMovies;


    public UserResponseDTO(String id, String firstName, String lastName, String email, List<CollectionsResponseDTO> collectionsMovies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.collectionsMovies = collectionsMovies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CollectionsResponseDTO> getCollectionsMovies() {
        return collectionsMovies;
    }

    public void setCollectionsMovies(List<CollectionsResponseDTO> collectionsMovies) {
        this.collectionsMovies = collectionsMovies;
    }
}
