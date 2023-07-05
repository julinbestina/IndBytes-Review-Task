package com.indbytes.service;

import java.util.List;
import com.indbytes.dto.BookDTO;
import com.indbytes.model.Book;

public interface IBookService {

	public BookDTO addBook(Book book);

	public Book convertDtoToEntity(BookDTO bookDto);

	public BookDTO convertEntityToDto(Book book);

	public List<BookDTO> convertAllEntityToDto(List<Book> book);

	public List<Book> listAllBook();

	public BookDTO updateBook(Book book);

	public BookDTO getBookByName(String bookName);

	public String deleteBookByName(String bookName);

	public Book getBook();

}
