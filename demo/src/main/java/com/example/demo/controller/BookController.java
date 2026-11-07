package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management System")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @Operation(summary = "View a list of available books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Endpoint de paginación (Desafío 3)
    @GetMapping("/page")
    @Operation(summary = "Get paginated list of books")
    public Page<Book> getBooksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return bookService.getAllBooksPaginated(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by Id")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // Endpoints de Búsqueda (Desafío 2)
    @GetMapping("/search/title")
    @Operation(summary = "Search books by title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

    @GetMapping("/search/author")
    @Operation(summary = "Search books by author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

    // Validación aplicada con @Valid (Desafío 1)
    @PostMapping
    @Operation(summary = "Add a new book")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        Book existingBook = bookService.getBookById(id);
        if (existingBook != null) {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            return bookService.saveBook(existingBook);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}