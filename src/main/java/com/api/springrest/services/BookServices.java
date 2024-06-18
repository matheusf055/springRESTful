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

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class BookServices {

    private final Logger logger = Logger.getLogger(BookServices.class.getName());
    private final BookRepository repository;

    public List<BookVO> findAll(){
        logger.info("Finding all people!");

        var book = DozerMapper.parseListObject(repository.findAll(), BookVO.class) ;
        book
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findbyId(p.getKey())).withSelfRel()));
        return book;
    }

    public BookVO findById(Long id){
        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findbyId(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book){
        if (book == null) {
            throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        }
        logger.info("Create one book");

        var entity = DozerMapper.parseObject(book, Book.class);
        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findbyId(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book){
        logger.info("Updating one person");

        var entity = repository.findById(book.getKey())
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setPrice(book.getPrice());
        entity.setLaunchDate(book.getLaunchDate());

        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findbyId(vo.getKey())).withSelfRel());
        return vo;
        }

        public void delete(Long id){
            logger.info("Deleting one person");

            var entity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
            repository.delete(entity);
        }
}
