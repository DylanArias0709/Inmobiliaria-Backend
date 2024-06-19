package com.g2inmobiliaria.app.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbComment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdComment", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser")
    private User idUser;

    @Column(name = "CommentContent", length = 200)
    private String commentContent;

    @Column(name = "CommentPublicationDate")
    private LocalDate commentPublicationDate;

    @Column(name = "Status", columnDefinition = "tinyint not null")
    private Short status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdVisit")
    private Visit idVisit;

}