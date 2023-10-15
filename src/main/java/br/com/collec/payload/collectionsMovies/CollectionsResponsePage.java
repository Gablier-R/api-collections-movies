package br.com.collec.payload.collectionsMovies;


import br.com.collec.payload.user.UserResponseDTO;

import java.util.List;

public class CollectionsResponsePage {

    private List<CollectionsResponseDTO> content;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean last;


    public CollectionsResponsePage(List<CollectionsResponseDTO> content, int pageNo, int pageSize, int totalPages, long totalElements, boolean last) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.last = last;
    }

    public CollectionsResponsePage() {

    }

    public List<CollectionsResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<CollectionsResponseDTO> content) {
        this.content = content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
