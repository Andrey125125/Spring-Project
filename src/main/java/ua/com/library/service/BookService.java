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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    BookRepository bookRepository;

    public Page<Book> resolvePages(int currentPage, int pageSize, String sortBy, String searchBy) {

        PageRequest sorted = PageRequest.of(currentPage - 1, pageSize, Sort.by(sortBy));
        if(searchBy.equals("")){
            return bookRepository.findAll(sorted);
        } else {
            return bookRepository.findAllByNameLike(searchBy, sorted);
        }


    }

    public Optional<List<Integer>> findPageNumbers(int quantity){

        List<Integer> pages = null;
        if (quantity > 1) {
            pages = IntStream
                    .rangeClosed(1, quantity)
                    .boxed()
                    .collect(Collectors.toList());
        }

        return Optional.ofNullable(pages);

    }

    public Optional<Book> findBookById(Long id){
        return bookRepository.findById(id);
    }
}
