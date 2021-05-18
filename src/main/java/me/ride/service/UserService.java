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

    public User getAuthorizedUser() throws UsernameNotFoundException;

    public User findUserById(Long userId);

    public List<User> allUsers();

    public boolean saveUser(User user);

    public boolean deleteUser(Long userId);

    public List<User> usergtList(Long idMin);
}
