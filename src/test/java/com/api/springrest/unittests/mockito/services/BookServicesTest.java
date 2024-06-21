/*package com.api.springrest.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.springrest.entity.Book;
import com.api.springrest.exceptions.RequiredObjectIsNullException;
import com.api.springrest.repository.BookRepository;
import com.api.springrest.services.BookServices;
import com.api.springrest.unittests.mocks.MockBook;
import com.api.springrest.vo.BookVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	@Mock
	private BookRepository repository;

	@InjectMocks
	private BookServices services;

	private MockBook mockBook;

	@BeforeEach
	void setUp() {
		mockBook = new MockBook();
	}

	@Test
	void testFindById() {
		Book entity = MockBook.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		BookVO result = services.findById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getKey());
		assertNotNull(result.getLinks());
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(BigDecimal.valueOf(25D), result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testCreate() {
		Book entity = MockBook.mockEntity(1);
		entity.setId(1L);
		when(repository.save(any(Book.class))).thenReturn(entity);

		BookVO vo = mockBook.mockVO(1);
		vo.setKey(1L);

		BookVO result = services.create(vo);

		assertNotNull(result);
		assertEquals(1L, result.getKey());
		assertNotNull(result.getLinks());
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(BigDecimal.valueOf(25D), result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testCreateWithNullBook() {
		assertThrows(RequiredObjectIsNullException.class, () -> services.create(null));
	}

	@Test
	void testUpdate() {
		Book entity = MockBook.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(any(Book.class))).thenReturn(entity);

		BookVO vo = mockBook.mockVO(1);
		vo.setKey(1L);

		BookVO result = services.update(vo);

		assertNotNull(result);
		assertEquals(1L, result.getKey());
		assertNotNull(result.getLinks());
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(BigDecimal.valueOf(25D), result.getPrice());
		assertNotNull(result.getLaunchDate());
	}

	@Test
	void testUpdateWithNullBook() {
		assertThrows(RequiredObjectIsNullException.class, () -> services.update(null));
	}

	@Test
	public void testDelete() {
		Book book = MockBook.mockEntity(1);

		when(repository.findById(1L)).thenReturn(Optional.of(book));

		services.delete(1L);
	}

	@Test
	void testFindAll() {
		List<Book> list = mockBook.mockEntityList();
		when(repository.findAll()).thenReturn(list);

		List<BookVO> result = services.findAll();

		assertNotNull(result);
		assertEquals(14, result.size());

		BookVO bookOne = result.get(1);
		assertNotNull(bookOne);
		assertEquals(1L, bookOne.getKey());
		assertNotNull(bookOne.getLinks());
		assertEquals("Some Author1", bookOne.getAuthor());
		assertEquals("Some Title1", bookOne.getTitle());
		assertEquals(BigDecimal.valueOf(25D), bookOne.getPrice());
		assertNotNull(bookOne.getLaunchDate());

		BookVO bookFour = result.get(4);
		assertNotNull(bookFour);
		assertEquals(4L, bookFour.getKey());
		assertNotNull(bookFour.getLinks());
		assertEquals("Some Author4", bookFour.getAuthor());
		assertEquals("Some Title4", bookFour.getTitle());
		assertEquals(BigDecimal.valueOf(25D), bookFour.getPrice());
		assertNotNull(bookFour.getLaunchDate());

		BookVO bookSeven = result.get(7);
		assertNotNull(bookSeven);
		assertEquals(7L, bookSeven.getKey());
		assertNotNull(bookSeven.getLinks());
		assertEquals("Some Author7", bookSeven.getAuthor());
		assertEquals("Some Title7", bookSeven.getTitle());
		assertEquals(BigDecimal.valueOf(25D), bookSeven.getPrice());
		assertNotNull(bookSeven.getLaunchDate());
	}
}*/
