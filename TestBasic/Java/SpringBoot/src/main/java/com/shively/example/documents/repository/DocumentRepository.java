package com.shively.example.documents.repository;

import com.shively.example.documents.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: devinshively
 * Created On: 9/27/14 3:44 PM
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
