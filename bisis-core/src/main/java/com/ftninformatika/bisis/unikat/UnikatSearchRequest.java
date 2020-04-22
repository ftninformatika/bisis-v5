package com.ftninformatika.bisis.unikat;

import com.ftninformatika.bisis.opac2.search.ResultPageSearchRequest;
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
public class UnikatSearchRequest extends ResultPageSearchRequest {
    private List<String> selectedLibs = new ArrayList<>();
}
