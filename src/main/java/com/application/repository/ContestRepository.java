package com.application.repository;


import com.application.model.Contest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends PagingAndSortingRepository<Contest, Long> {
    @Override
    Page<Contest> findAll(Pageable pageable);
}
