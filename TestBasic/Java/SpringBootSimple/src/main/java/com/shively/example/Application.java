package com.shively.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) { SpringApplication.run(Application.class, args); }

    @Inject DocumentRepository documentRepository;

    @RequestMapping(value="/documents", method = RequestMethod.POST)
    public Document createOrUpdate(@RequestBody @Valid final Document document) {
        return documentRepository.save(document);
    }

    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @RequestMapping(value = "/documents/{id}", method = RequestMethod.GET)
    public Document find(@PathVariable(value="id") Long id) {
        return documentRepository.findOne(id);
    }

    @RequestMapping(value = "/documents/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value="id") Long id) {
        documentRepository.delete(id);
    }
}
