package ua.com.library.entity;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String publisher;

    @Column
    private String description;

    @Column(name = "description_ua")
    private String descriptionUa;


    @Column(columnDefinition = "int default 1", nullable = false)
    private Integer quantity;

    @Column(name = "publishing_date", nullable = false)
    private java.sql.Date publishingDate;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
                            CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "author_to_book",
                joinColumns = @JoinColumn(name= "book_id"),
                inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;


    @OneToMany(mappedBy = "book")
    private List<Order> orders;



    public void addAuthor(Author author){
        if (authors==null){
            authors = new ArrayList<Author>();
        }

        authors.add(author);
        author.addBook(this);
    }
}
