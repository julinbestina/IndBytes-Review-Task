package com.indbytes.service;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.indbytes.dto.BookDTO;
import com.indbytes.exception.CustomerException;
import com.indbytes.model.Book;
import com.indbytes.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Service
public class BookService implements IBookService {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	ModelMapper mapper;

	HashMap<LocalDate, Integer> bookMap = new HashMap<LocalDate, Integer>();

	@Override
	public Book convertDtoToEntity(BookDTO bookDto) {
		Book book = mapper.map(bookDto, Book.class);
		return book;
	}

	@Override
	public BookDTO convertEntityToDto(Book book) {
		BookDTO bookDto = mapper.map(book, BookDTO.class);
		return bookDto;
	}

	@Override
	public List<BookDTO> convertAllEntityToDto(List<Book> book) {
		return book.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	@Override
	public BookDTO addBook(Book book) {
		bookRepo.findBybookName(book.getBookName()).ifPresent(existBook -> {
			throw new CustomerException("Book already added");
		});
		return convertEntityToDto(bookRepo.save(book));
	}

	@Override
	public List<Book> listAllBook() {
		if (bookRepo.findAll().isEmpty()) {
			throw new CustomerException("No book details to show");
		}

//		return convertAllEntityToDto(bookRepo.findAll());
		return bookRepo.findAll();
	}

	@Override
	public BookDTO updateBook(Book book) {
		Book savedBook = bookRepo.findBybookName(book.getBookName()).get();
		savedBook.setBookName(book.getBookName());
		savedBook.setAuthor(book.getAuthor());
		savedBook.setPrice(book.getPrice());
		savedBook.setImage(book.getImage());
		savedBook.setDescription(book.getDescription());
		return convertEntityToDto(bookRepo.save(savedBook));
	}

	@Override
	public BookDTO getBookByName(String bookName) {
		bookRepo.findBybookName(bookName)
				.orElseThrow(() -> new CustomerException("Book with the given Name not found"));
		return convertEntityToDto(bookRepo.findBybookName(bookName).get());
	}

	@Override
	public String deleteBookByName(String bookName) {

		Book book = bookRepo.findBybookName(bookName).get();
		bookRepo.findBybookName(bookName)
				.orElseThrow(() -> new CustomerException("Book with the given Name not found"));
		bookRepo.deleteById(book.getBookId());
		return "Book with the given name is deleted Successfully";
	}

	@Override
	public Book getBook() {

//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		String date1 = "12/07/2023";
//		LocalDate date  = LocalDate.parse(date1, formatter);

		LocalDate date = LocalDate.now();

		if (bookMap.entrySet().stream().anyMatch(entry -> entry.getKey().equals(date))) {
			return bookRepo.findById((int) bookMap.get(date)).get();
		} else {
			Random random = new Random();
			int id = bookRepo.findAll().size();
			// log.info("size" + id);

			int bookId = random.nextInt(id + 1);
			// log.info("id" + bookId);
			bookMap.put(date, bookId);
			Book book = bookRepo.findById(bookId).get();
			// log.info("book" + book);
			return book;
		}
	}

}
