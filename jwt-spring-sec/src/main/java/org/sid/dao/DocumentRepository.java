package org.sid.dao;

import org.sid.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends JpaRepository<Document, Long>, CrudRepository<Document, Long> {
 public Document findByDocumentName (String name);
}
