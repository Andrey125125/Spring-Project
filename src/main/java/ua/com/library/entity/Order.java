package ua.com.library.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;



    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne/*(targetEntity = Book.class)*/
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "returning_date", nullable = false)
    private java.sql.Date returningDate;

    public enum Status {
        REQUESTED, ADMITTED;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Order.Status status;
}
