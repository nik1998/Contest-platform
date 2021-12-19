package com.application.service;

import com.application.dto.ContestDto;
import com.application.model.Contest;
import com.application.model.User;
import com.application.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestRepository contestRepository;

    public Contest save(ContestDto contestDto, User user) {
        Contest contest = new Contest(contestDto.getContestName(), contestDto.getDescription(), contestDto.getStartDate(), contestDto.getDeadline(), contestDto.getPrize(), user.getOrganization());
        contest.addJury(user);
        contest.setCategories(contestDto.getCategories());
        contest.setEndVoteDate(contestDto.getEndVoteDate());
        contest.setPopularVoting(contestDto.popularVoting);
        contest = contestRepository.save(contest);
        return contest;
    }

    public Page<Contest> findAll(Pageable pageable) {
        return contestRepository.findAll(pageable);
    }

    public Optional<Contest> findById(Long id) {
        return contestRepository.findById(id);
    }

    public Contest update(Contest contest, ContestDto contestDto) {
        contest.setContestName(contestDto.getContestName());
        contest.setDescription(contestDto.getDescription());
        contest.setDeadline(contestDto.getDeadline());
        contest.setStartDate(contestDto.getStartDate());
        contest.setCategories(contestDto.getCategories());
        contest.setEndVoteDate(contestDto.getEndVoteDate());
        return contestRepository.save(contest);
    }

    public Contest updateImage(Contest contest, byte[] file) {
        contest.setPicByte(file);
        return contestRepository.save(contest);
    }
}
