package com.application.service;

import com.application.dto.ContestDto;
import com.application.model.Category;
import com.application.model.Contest;
import com.application.model.Organization;
import com.application.repository.CategoryRepository;
import com.application.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Contest save(ContestDto contestDto, Organization org) {
        Contest contest = new Contest(contestDto.getContestName(), contestDto.getDescription(), new Date(), new Date(), contestDto.getPrize(), org);
        contest = contestRepository.save(contest);
        categoryRepository.save(new Category(contestDto.getCategory(), contest));
        return contest;
    }

    public Page<Contest> findAll(Pageable pageable) {
        return contestRepository.findAll(pageable);
    }
}
