package com.sb.nearby.dto;

import com.sb.nearby.model.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotNull
    @NotEmpty(message = "You need to enter name.")
    private String name;

    private String description;

    private Float price;

    @NotNull
    @NotEmpty(message = "You need to enter category.")
    private Category category;

    private Double latitude;
    private Double longitude;
    private MultipartFile image;
    private Integer views;
}
