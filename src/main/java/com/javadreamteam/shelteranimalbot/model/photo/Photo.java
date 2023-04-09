package com.javadreamteam.shelteranimalbot.model.photo;

import javax.persistence.*;

@MappedSuperclass
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String path;
    private long size;
    private String mediaType;

    @Lob
    private byte[] data;

}
