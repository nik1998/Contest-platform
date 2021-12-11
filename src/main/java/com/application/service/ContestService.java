package com.application.service;

import com.application.dto.ContestDto;
import com.application.model.Contest;
import com.application.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContestService {


    Contest save(ContestDto contest, Organization org);

    Page<Contest> findAll(Pageable pageable);

}
