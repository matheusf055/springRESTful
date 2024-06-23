package com.api.springrest.integrationtests.testcontainers.wrapper;

import java.io.Serializable;
import java.util.List;

import com.api.springrest.integrationtests.testcontainers.vo.BookVO;
import com.fasterxml.jackson.annotation.JsonProperty;


public class BookEmbeddedVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("bookVOList")
    private List<BookVO> books;

    public BookEmbeddedVO() {}

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }
}