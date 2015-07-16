package com.shively.example.documents.service;

import com.shively.example.documents.domain.Document;

import java.util.List;

/**
 * User: devinshively
 * Created On: 9/27/14 7:18 PM
 */
public interface DocumentService {
    Document createOrUpdate(Document document);
    List<Document> findAll();
    Document find(Long noteId);
    void delete(Long userId);
}
