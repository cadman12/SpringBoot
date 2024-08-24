package edu.pnu.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
//@DiscriminatorValue("B") // 생략가능 : default=클래스명
public class Book extends Item {
    private String author;
    private String isbn;
}
