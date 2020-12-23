package com.maurer.example.springbootdemo.service;

import com.maurer.example.springbootdemo.models.CompanyUserDetails;
import com.maurer.example.springbootdemo.models.Role;
import com.maurer.example.springbootdemo.models.User;
import com.maurer.example.springbootdemo.models.enums.RoleType;
import com.maurer.example.springbootdemo.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTests {

    @InjectMocks
    UserDetailsService userDetailsService = new UserDetailsServiceImpl();

    @Mock
    UserRepository userRepository;


    private User user = new User();
    private Role role = new Role();
    private List<GrantedAuthority> authorities;
    private Set<Role> roles = new HashSet<>();

    @Before
    public void setUp() {

        role.setId(1);
        role.setName(RoleType.ROLE_USER);

        roles.add(role);

        user.setId(1);
        user.setUsername("username");
        user.setPassword("pass");
        user.setRoles(roles);

        authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

    }

    @Test
    public void shouldSuccessfullyLoadUserByExistingUsername() {

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        UserDetails expectedUserDetails = userDetailsService.loadUserByUsername("username");
        UserDetails actualUserDetails = new CompanyUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);

        assertThat(expectedUserDetails).isEqualTo(actualUserDetails);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowUsernameNotFoundExceptionWhenTryingToLoadNonExistingUser() {

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        userDetailsService.loadUserByUsername("user");
    }
}
