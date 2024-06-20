package com.api.springrest.services;

import com.api.springrest.controller.PersonController;
import com.api.springrest.entity.Person;
import com.api.springrest.exceptions.RequiredObjectIsNullException;
import com.api.springrest.exceptions.ResourceNotFoundException;
import com.api.springrest.repository.PersonRepository;
import com.api.springrest.vo.PersonVO;
import com.api.springrest.vo.mapper.DozerMapper;
import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());
    private final PersonRepository repository;

    public List<PersonVO> findAll(){
        logger.info("Finding all people!");

        var persons = DozerMapper.parseListObject(repository.findAll(), PersonVO.class) ;
        persons
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findbyId(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonVO findById(Long id){
        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findbyId(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person){
        if (person == null) {
           throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        }

        logger.info("Create one person");

        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findbyId(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person){
        if (person == null) {
            throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        }

        logger.info("Updating one person");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findbyId(vo.getKey())).withSelfRel());
        return vo;
    }

    @Transactional
    public PersonVO disablePerson(Long id){
        logger.info("Disabling one person!");

        repository.disablePerson(id);

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findbyId(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting one person");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
