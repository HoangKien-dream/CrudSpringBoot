package com.example.crudangular.service;
import com.example.crudangular.dto.AccountDTO;
import com.example.crudangular.dto.RegisterDTO;
import com.example.crudangular.entity.Account;
import com.example.crudangular.entity.Role;
import com.example.crudangular.repository.AccountRepository;
import com.example.crudangular.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private static final String USER_ROLE = "user";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        Account account = accountOptional.orElse(null);
        if (account == null) {
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role:
             account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        UserDetails userDetail
                = new User(account.getUsername(), account.getPassword(), authorities);
        return
                userDetail;
    }

    public AccountDTO addRole(String username,String name){
        Role role = roleRepository.findByName(name).orElse(null);
        Account account = accountRepository.findByUsername(username).orElse(null);
        if (account!=null){
            account.getRoles().add(role);
            return new AccountDTO(account);
        }
        return null;
    }

    public AccountDTO saveAccount(RegisterDTO registerDTO) {
        //create new user role if not exist
        Set<Role> roleSet = new HashSet<>();
        Optional<Role> userRoleOptional = roleRepository.findByName(USER_ROLE);
        Role userRole = userRoleOptional.orElse(null);
        if (userRole == null) {
            //create new role
            userRole.setName(USER_ROLE);
        }
        roleSet.add(userRole);
        //check if username has exist
        Optional<Account> byUsername = accountRepository.findByUsername(registerDTO.getUsername());
        if (byUsername.isPresent()) {
            return null;
        }
        Set<Account> accountSet = new HashSet<>();
        Account account = new Account();
        account.setUsername(registerDTO.getUsername());
        account.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        account.setRoles(roleSet);
        account.setStatus(1);
        accountSet.add(account);
        userRole.setAccounts(accountSet);
        Account save = accountRepository.save(account);
        return new AccountDTO(save);
    }

    public Account getAccount(String username) {
        Optional<Account> byUsername = accountRepository.findByUsername(username);
        return byUsername.orElse(null);
    }
}
