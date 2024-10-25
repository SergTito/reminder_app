package com.example.reminder_app.repository;

import com.example.reminder_app.entity.ReminderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<ReminderEntity, Long> {


    @Query("SELECT r FROM ReminderEntity r WHERE " +
           "LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :description, '%')) OR " +
           "r.remind = :dateTime ")
    List<ReminderEntity> findAllByTitleAndDescriptionAndRemind(
            @Param("title") String title,
            @Param("description") String description,
            @Param("dateTime") LocalDateTime  dateTime,
            Pageable pageable);



    List<ReminderEntity> findAllByUserId(Long id);

}
