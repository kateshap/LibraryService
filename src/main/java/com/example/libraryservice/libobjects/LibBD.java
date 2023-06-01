package com.example.libraryservice.libobjects;

import lombok.Data;
import java.util.Date;
import javax.persistence.*;


@Data
@Entity
@Table(name = "reservation")
public class LibBD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titleBD;

    @Column(nullable = false)
    private String authorBD;
}