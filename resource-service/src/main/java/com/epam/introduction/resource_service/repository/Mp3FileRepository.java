package com.epam.introduction.resource_service.repository;

import com.epam.introduction.resource_service.model.Mp3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface Mp3FileRepository extends JpaRepository<Mp3Entity, Long> {
    @Query("select s.id from Mp3Entity s where s.id in :ids")
    List<Long> findExistingIdsByIdIn(@Param("ids") Collection<Long> ids);
}
