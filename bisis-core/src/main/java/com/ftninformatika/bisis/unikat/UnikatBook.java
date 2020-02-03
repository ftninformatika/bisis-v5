package com.ftninformatika.bisis.unikat;

import com.ftninformatika.bisis.opac2.books.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnikatBook {
    private Book book;
    private String lib;
}
