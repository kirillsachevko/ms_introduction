package com.epam.introduction.song_service.repository;

import com.epam.introduction.song_service.model.Mp3Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface Mp3MetadataRepository extends JpaRepository<Mp3Metadata, Long> {
    @Query("select s.id from Mp3Metadata s where s.id in :ids")
    List<Long> findExistingIdsByIdIn(@Param("ids") Collection<Long> ids);
}
