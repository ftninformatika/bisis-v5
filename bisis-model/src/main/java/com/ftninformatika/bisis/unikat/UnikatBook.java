package com.ftninformatika.bisis.unikat;

import com.ftninformatika.bisis.opac.books.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnikatBook {
    private Book book;
    private List<UnikatBookRef> unikatBookRefs = new ArrayList<>();
}
