package com.api.springrest.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.api.springrest.entity.Person;
import com.api.springrest.exceptions.RequiredObjectIsNullException;
import com.api.springrest.repository.PersonRepository;
import com.api.springrest.services.PersonServices;
import com.api.springrest.unittests.MockPerson;
import com.api.springrest.vo.PersonVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	@Mock
	private PersonRepository repository;

	@InjectMocks
	private PersonServices services;

	private MockPerson mockPerson = new MockPerson();

	@Test
	void testFindById() {
		Person entity = mockPerson.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		PersonVO result = services.findById(1L);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testCreate() {
		Person entity = mockPerson.mockEntity(1);
		entity.setId(1L);

		Person persisted = entity;
		persisted.setId(1L);

		PersonVO vo = mockPerson.mockVO(1);
		vo.setKey(1L);

		when(repository.save(entity)).thenReturn(persisted);

		var result = services.create(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			services.create(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Person entity = mockPerson.mockEntity(1);

		Person persisted = entity;
		persisted.setId(1L);

		PersonVO vo = mockPerson.mockVO(1);
		vo.setKey(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = services.update(vo);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			services.update(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testDelete() {
		Person person = mockPerson.mockEntity(1);

		when(repository.findById(1L)).thenReturn(Optional.of(person));

		services.delete(1L);
	}

	@Test
	void testFindAll() {
		List<Person> list = mockPerson.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var people = services.findAll();

		assertNotNull(people);
		assertEquals(14, people.size());

		var personOne = people.get(1);

		assertNotNull(personOne);
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.getLinks());
		assertEquals("Addres Test1", personOne.getAddress());
		assertEquals("First Name Test1", personOne.getFirstName());
		assertEquals("Last Name Test1", personOne.getLastName());
		assertEquals("Female", personOne.getGender());

		var personFour = people.get(4);

		assertNotNull(personFour);
		assertNotNull(personFour.getKey());
		assertNotNull(personFour.getLinks());
		assertEquals("Addres Test4", personFour.getAddress());
		assertEquals("First Name Test4", personFour.getFirstName());
		assertEquals("Last Name Test4", personFour.getLastName());
		assertEquals("Male", personFour.getGender());

		var personSeven = people.get(7);

		assertNotNull(personSeven);
		assertNotNull(personSeven.getKey());
		assertNotNull(personSeven.getLinks());
		assertEquals("Addres Test7", personSeven.getAddress());
		assertEquals("First Name Test7", personSeven.getFirstName());
		assertEquals("Last Name Test7", personSeven.getLastName());
		assertEquals("Female", personSeven.getGender());
	}
}
