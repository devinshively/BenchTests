package com.shively.example;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: devinshively
 * Created On: 10/10/14 7:59 AM
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {}
