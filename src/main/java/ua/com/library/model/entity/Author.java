package ua.com.library.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
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
}
