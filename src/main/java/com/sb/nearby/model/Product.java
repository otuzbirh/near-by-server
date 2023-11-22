package com.sb.nearby.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Float price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", nullable=false)
    private Category category;

    private Double latitude;
    private Double longitude;
    private String image;
    private Integer views;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="product", cascade = CascadeType.ALL)
    private List<PriceHistory> priceHistoryList;

}
