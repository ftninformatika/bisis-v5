package com.ftninformatika.bisis.rest_service.controller.unikat;

import com.ftninformatika.bisis.rest_service.service.implementations.UnikatService;
import com.ftninformatika.bisis.unikat.UnikatBook;
import com.ftninformatika.bisis.unikat.UnikatSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/unikat")
public class UnikatController {

    @Autowired UnikatService unikatService;

    @PostMapping("search")
    public ResponseEntity<Page<List<UnikatBook>>> unikatSearch(
            @RequestBody UnikatSearchRequest resultPageSearchRequest,
            @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        return null;
    }
}
