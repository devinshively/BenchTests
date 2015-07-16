package com.shively.example.documents.service;

import com.shively.example.documents.domain.Document;
import com.shively.example.documents.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * User: devinshively
 * Created On: 9/27/14 7:21 PM
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Inject
    DocumentRepository documentRepository;

    @Override
    public Document createOrUpdate(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public Document find(Long noteId) {
        return null;
    }

    @Override
    public void delete(Long userId) {
        documentRepository.delete(userId);
    }


}
