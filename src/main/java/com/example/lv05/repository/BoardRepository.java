package com.example.lv05.repository;

import com.example.lv05.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByCreateAtDesc();

    Board findByIdAndUsername(Long id, String username);
}
