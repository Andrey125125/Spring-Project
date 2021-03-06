package ua.com.library.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // set this to all later
    private Long id;


    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;


    public void addBook(Book book){
        if (books==null){
            books= new ArrayList<Book>();
        }

        books.add(book);
    }
}
