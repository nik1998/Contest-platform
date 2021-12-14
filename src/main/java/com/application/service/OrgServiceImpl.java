package com.application.service;

import com.application.dto.OrganizationDto;
import com.application.model.Organization;
import com.application.model.Role;
import com.application.model.User;
import com.application.repository.OrgRepository;
import com.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Organization findByCompanyName(String name) {
        return orgRepository.findByCompanyName(name);
    }

    @Override
    public List<Organization> findAll() {
        return orgRepository.findAll();
    }

    @Override
    public Organization save(OrganizationDto registration) {
        String encPassword = passwordEncoder.encode(registration.getPassword());
        User user = new User();
        user.setFirstName(registration.getCompanyName());
        user.setLastName("");
        user.setEmail(registration.getEmail());
        user.setPassword(encPassword);
        user.setRoles(Arrays.asList(new Role("ORG_ADMIN")));
        user = userRepository.save(user);

        Organization org = new Organization();
        org.setCompanyName(registration.getCompanyName());
        org.setUrl(registration.getUrl());
        org.setDescription(registration.getDescription());
        try {
            org.setPicByte(registration.getPicByte().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        org.addUser(user);
        return orgRepository.save(org);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
