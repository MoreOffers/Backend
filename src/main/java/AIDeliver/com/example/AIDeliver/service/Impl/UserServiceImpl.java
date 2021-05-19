package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.UserRepository;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(@RequestBody User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email has been taken");
        }
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getEmail());
        user.setPassword(encodedPassword);

        userRepository.save(user);

    }

    public void deleteUser(Long studentId) {
        boolean exist = userRepository.existsById(studentId);
        if (!exist) {
            throw new IllegalStateException("User " + studentId + "does not exist");
        }
        userRepository.deleteById(studentId);
    }

    @Transactional
    public void updateUser(@RequestBody User user) {

        User existinguser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + user.getId() + " does not exist."
                ));

        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}

