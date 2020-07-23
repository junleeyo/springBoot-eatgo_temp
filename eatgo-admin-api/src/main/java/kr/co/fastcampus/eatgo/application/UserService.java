package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(String email, String name) {
       User user = User.builder()
                .email(email)
                .name(name)
                .level(1L)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, String email, String name, Long level) {
        User user = userRepository.findById(id).orElse(null);
        user.setEmail(email);
        user.setName(name);
        user.setLevel(level);

        return user;
    }

    @Transactional
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.deativate();

        return user;
    }
}
