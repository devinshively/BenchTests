package com.shively.example.documents.controller;

import com.shively.example.documents.domain.Document;
import com.shively.example.documents.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * User: devinshively
 * Created On: 9/22/14 7:32 PM
 */
@RestController
public class DocumentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private static final String GREETING = "Hello World!";
    @Inject
    DocumentService documentService;

    @RequestMapping(value="/documents/hello", method = RequestMethod.GET)
    public String hello() {
        return GREETING;
    }

    @RequestMapping(value="/documents", method = RequestMethod.POST)
    public Document createOrUpdate(@RequestBody @Valid final Document document) {
        LOGGER.debug("Received request to create document with title: " + document.getTitle());
        return documentService.createOrUpdate(document);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public List<Document> findAll() {
        LOGGER.debug("Received request to find all documents");
        return documentService.findAll();
    }

    @RequestMapping(value = "/documents/{id}", method = RequestMethod.GET)
    public Document find(@PathVariable(value="id") Long id) {
        LOGGER.debug("Received request to find note by id: " + id);
        return documentService.find(id);
    }

    @RequestMapping(value = "/documents/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value="id") Long id) {
        LOGGER.debug("Received request to find note by id: " + id);
        documentService.delete(id);
    }

}
