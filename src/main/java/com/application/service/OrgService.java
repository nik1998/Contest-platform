package com.application.service;

import com.application.dto.OrganizationDto;
import com.application.model.Organization;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface OrgService extends UserDetailsService {
    Organization findByEmail(String email);

    Organization findByCompanyName(String email);

    Organization save(OrganizationDto registration);

    List<Organization> findAll();
}
