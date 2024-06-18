package com.api.springrest.services;

import com.api.springrest.controller.BookController;
import com.api.springrest.entity.Book;
import com.api.springrest.exceptions.RequiredObjectIsNullException;
import com.api.springrest.exceptions.ResourceNotFoundException;
import com.api.springrest.repository.BookRepository;
import com.api.springrest.vo.BookVO;
import com.api.springrest.vo.mapper.DozerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class BookServices {

    private final Logger logger = Logger.getLogger(BookServices.class.getName());
    private final BookRepository repository;

    public List<BookVO> findAll() {
        logger.info("Finding all books!");

        List<BookVO> books = DozerMapper.parseListObject(repository.findAll(), BookVO.class);

        for (BookVO book : books) {
            book.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).findbyId(book.getKey())).withSelfRel());
        }

        return books;
    }

    public BookVO findById(Long id) {
        logger.info("Finding book by id: " + id);

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).findbyId(vo.getKey())).withSelfRel());

        return vo;
    }

    public BookVO create(BookVO book) {
        if (book == null) {
            throw new RequiredObjectIsNullException("Cannot create a null book object!");
        }

        logger.info("Creating a new book");

        Book entity = DozerMapper.parseObject(book, Book.class);
        BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).findbyId(vo.getKey())).withSelfRel());

        return vo;
    }

    public BookVO update(BookVO book) {
        if (book == null) {
            throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        }
        logger.info("Updating book with id: " + book.getKey());

        Book entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + book.getKey()));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setPrice(book.getPrice());
        entity.setLaunchDate(book.getLaunchDate());

        BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).findbyId(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting book with id: " + id);

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        repository.delete(entity);
    }
}
