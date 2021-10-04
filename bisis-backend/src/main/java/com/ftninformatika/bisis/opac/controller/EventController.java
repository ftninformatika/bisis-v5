package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.Event;
import com.ftninformatika.bisis.opac.admin.dto.EventsFilterDTO;
import com.ftninformatika.bisis.opac.repository.EventRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    LibraryPrefixProvider prefixProvider;
    @Autowired
    GridFsTemplate gridFsTemplate;

    @GetMapping()
    public ResponseEntity<List<Event>> getEventsAndroid() {
        List<Event> events = this.eventRepository.findAll();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Event>> getEvents(@RequestHeader("Library") String lib,
                                                 @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                                 @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<Event> events = this.eventRepository.findAllByOrderByDateDesc(paging);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Event>> searchEvents(@RequestHeader("Library") String lib,
                                                    @RequestBody EventsFilterDTO eventsFilterDTO,
                                                    @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                                    @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());
        Page<Event> events;
        if (eventsFilterDTO.getTo() == null && eventsFilterDTO.getFrom() == null) {
            events = this.eventRepository.searchTextOnly(eventsFilterDTO.getSearchText(), paging);
        } else if (eventsFilterDTO.getTo() == null) {
            events = this.eventRepository.searchFromDateOnly(eventsFilterDTO.getSearchText(), eventsFilterDTO.getFrom(), paging);
        } else {
            events = this.eventRepository.search(eventsFilterDTO.getSearchText(), eventsFilterDTO.getFrom(),
                    eventsFilterDTO.getTo(), paging);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("{eventId}")
    public Event getEvent(@PathVariable("eventId") String eventId) {
        return eventRepository.findById(eventId).get();
    }

    @GetMapping("/image/{eventId}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getCover(@PathVariable("eventId") String eventId) {
        GridFsResource gridFSFile = getEventImage(eventId, prefixProvider.getLibPrefix());
        if (gridFSFile == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            return ResponseEntity.ok()
                    .contentLength(gridFSFile.contentLength())
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_GIF_VALUE))
                    .body(new InputStreamResource(gridFSFile.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/add")
    //TODO umesto ModelAttribute staviti RequestBody i promeniti da file nije obavezan
    public ResponseEntity<Event> addEvent(@ModelAttribute Event event,
                                          @RequestPart(name = "file", required = false) MultipartFile file) {
        try {
            String library = prefixProvider.getLibPrefix();
            Event savedEvent = eventRepository.save(event);
            if (savedEvent.get_id() == null) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (file != null && !file.isEmpty()) {
                uploadImage(savedEvent.get_id(), library, file);
            }
            return new ResponseEntity<>(savedEvent, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GridFsResource getEventImage(String eventId, String library) {
        Query query = new Query();
        query.addCriteria(Criteria.where("metadata.eventId").is(eventId).and("metadata.library").is(library));
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);
        if (gridFSFile == null) return null;
        return gridFsTemplate.getResource(gridFSFile);
    }

    private void uploadImage(String eventId, String library, MultipartFile file) throws IOException {
        BasicDBObject metaData = new BasicDBObject();
        metaData.put("eventId", eventId);
        metaData.put("library", library);
        gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), "image", metaData);
    }

    @DeleteMapping("{eventId}")
    private ResponseEntity<Boolean> deleteEvent(@PathVariable("eventId") String eventId) {
        if (!eventRepository.findById(eventId).isPresent())
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        try {
            eventRepository.deleteById(eventId);
            String library = prefixProvider.getLibPrefix();
            deleteImage(eventId, library);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void deleteImage(String eventId, String library) {
        Query query = new Query();
        query.addCriteria(Criteria.where("metadata.eventId").is(eventId).and("metadata.library").is(library));
        gridFsTemplate.delete(query);
    }

    @PutMapping(value = "{eventId}")
    //TODO umesto ModelAttribute staviti RequestBody
    public ResponseEntity<Event> editEvent(@ModelAttribute Event editedEvent, @RequestPart(name = "file", required = false) MultipartFile file,
                                           @PathVariable("eventId") String eventId) {
        try {
            Event event = editEvent(eventId, editedEvent);
            String library = prefixProvider.getLibPrefix();
            if (file != null && !file.isEmpty()) {
                deleteImage(eventId, library);
                uploadImage(eventId, library, file);
            }
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Event editEvent(String eventId, Event editedEvent) {
        Event event = eventRepository.getBy_id(eventId);
        event.setContent(editedEvent.getContent());
        event.setTitle(editedEvent.getTitle());
        event.setDate(editedEvent.getDate());
        return eventRepository.save(event);
    }
}
