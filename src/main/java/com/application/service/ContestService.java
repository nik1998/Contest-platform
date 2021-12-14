package com.application.service;

import com.application.dto.ContestDto;
import com.application.model.Contest;
import com.application.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContestService {


    Contest save(ContestDto contest, User user);

    Page<Contest> findAll(Pageable pageable);

    Optional<Contest> findById(Long id);

    Contest update(Contest contest, ContestDto contestDto);

    Contest register(Contest contest, User user);

    Contest updateImage(Contest contest, byte[] file);
}
