package com.indbytes.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indbytes.dto.BookDTO;
import com.indbytes.model.Book;
import com.indbytes.service.IBookService;
import com.indbytes.utils.JWTUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("bookstore")
public class BookController {

	@Autowired
	IBookService bookService;

	@Autowired
	JWTUtil jwtUtil;

	@PostMapping
	public ResponseEntity<String> addBook(@RequestHeader String token, @RequestBody BookDTO bookDto) {
		jwtUtil.verify(token);
		Book book = bookService.convertDtoToEntity(bookDto);
		bookService.addBook(book);
		return ResponseEntity.status(HttpStatus.OK).body("Book Details Added Successfully");
	}

	@PutMapping
	public ResponseEntity<String> updateBook(@RequestHeader String token, @RequestBody BookDTO bookDto) {
		jwtUtil.verify(token);
		Book book = bookService.convertDtoToEntity(bookDto);
		bookService.updateBook(book);
		return ResponseEntity.status(HttpStatus.OK).body("Book Details updated Successfully");
	}

	@GetMapping
	public ResponseEntity<List<Book>> listAllBooks(@RequestHeader String token) {
		jwtUtil.verify(token);
		return ResponseEntity.status(HttpStatus.OK).body(bookService.listAllBook());
	}

	@GetMapping("/{bookName}")
	public ResponseEntity<BookDTO> getBookByName(@RequestHeader String token, @PathVariable String bookName)
			throws IllegalArgumentException, UnsupportedEncodingException {
		jwtUtil.verify(token);
		return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookByName(bookName));
	}

	@DeleteMapping("/{bookName}")
	public ResponseEntity<String> deleteBook(@RequestHeader String token, @PathVariable String bookName) {
		jwtUtil.verify(token);
		return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBookByName(bookName));

	}

	@GetMapping("/randombook")
	@ApiOperation("Suggest a book on daily basis")
	public ResponseEntity<BookDTO> getBook(@RequestHeader String token) {
		jwtUtil.verify(token);
		return ResponseEntity.status(HttpStatus.OK).body(bookService.convertEntityToDto(bookService.getBook()));

	}

}