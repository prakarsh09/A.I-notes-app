package com.example.notesapp.repo;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.notesapp.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes,Integer>{
   @Query("from Notes as n where n.empl.id=:uid")
    List<Notes> findNotesByUser(@Param("uid") int uid);

    @Query("from Notes as n where n.empl.email=:email and n.title=:title")
    List<Notes> findNotesByUserandtitle(@Param("email")String email,@Param("title") String title);
    
}
