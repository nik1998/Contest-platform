package com.application.repository;

import com.application.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgRepository extends JpaRepository<Organization, Long> {
    Organization findByEmail(String email);

    Organization findByCompanyName(String companyName);
}
