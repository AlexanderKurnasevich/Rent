package me.ride.service;

import me.ride.entity.Role;
import me.ride.entity.User;
import me.ride.repository.RoleRepository;
import me.ride.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {

    User getAuthorizedUser() throws UsernameNotFoundException;

    User findUserById(Long userId);

    List<User> allUsers();

    boolean saveUser(User user);

    boolean deleteUser(Long userId);

    List<User> usergtList(Long idMin);
}
