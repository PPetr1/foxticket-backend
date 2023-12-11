package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n WHERE lower(n.title) LIKE %:searchTerm% OR lower(n.content) LIKE %:searchTerm%")
    List<News> searchInTitleAndContentIgnoreCase(String searchTerm);
}
