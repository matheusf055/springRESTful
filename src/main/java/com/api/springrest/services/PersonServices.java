package com.api.springrest.services;

import com.api.springrest.entity.Person;
import com.api.springrest.exceptions.ResourceNotFoundException;
import com.api.springrest.repository.PersonRepository;
import com.api.springrest.vo.PersonVO;
import com.api.springrest.vo.mapper.DozerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());
    private final PersonRepository repository;

    public List<PersonVO> findAll(){
        logger.info("Finding all people!");

        return DozerMapper.parseListObject(repository.findAll(), PersonVO.class) ;
    }

    public PersonVO findById(Long id){
        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person){
        logger.info("Create one person");

        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        return vo;
    }

    public PersonVO update(PersonVO person){
        logger.info("Updating one person");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting one person");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
