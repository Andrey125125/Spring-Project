package ua.com.library.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.library.entity.Book;
import ua.com.library.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    BookRepository bookRepository;

    public Page<Book> showPageList(int currentPage, int pageSize, String sortBy) {
        PageRequest sorted = PageRequest.of(currentPage - 1, pageSize, Sort.by(sortBy));
        Page<Book> books = bookRepository.findAll(sorted);
        List<Book> result = books
                .stream()
                .collect(Collectors.toList());

        return new PageImpl<Book>(result, sorted, bookRepository.count());
    }
}
