package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.dto.UserDTO;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.OrderRepository;
import AIDeliver.com.example.AIDeliver.repository.UserRepository;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void save(User user) {
        userRepository.save(modelMapper.map(user, User.class));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Boolean addNewUser(UserDTO userDTO) {

        User curUser = userRepository.findUserByEmail(userDTO.getEmail());
        if (curUser != null) {
            throw new IllegalStateException("Email has been taken");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        userDTO.setCredit(100.0);
        userRepository.save(modelMapper.map(userDTO, User.class));
        return true;
    }

    @Override
    public Boolean updateUser(UserDTO userDTO) {
        Long userId = userRepository.findUserByEmail(userDTO.getEmail()).getId();
        User user = userRepository.getOne(userId);
        if (userDTO.getPassword() != null && userDTO.getPassword() != user.getPassword()) {
            user.setPassword(userDTO.getPassword());
        }
        if (userDTO.getName() != null && userDTO.getName() != user.getName()) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getAddress() != null && userDTO.getAddress() != user.getAddress()) {
            user.setAddress(userDTO.getAddress());
        }
        if (userDTO.getZipCode() != null && userDTO.getZipCode() != user.getZipCode()) {
            user.setZipCode(userDTO.getZipCode());
        }
        if (userDTO.getMobile() != null && userDTO.getMobile() != user.getMobile()) {
            user.setMobile(userDTO.getMobile());
        }
        userRepository.save(user);
        return true;
    }

    public void deleteUser(Long studentId) {
        boolean exist = userRepository.existsById(studentId);
        if (!exist) {
            throw new IllegalStateException("User " + studentId + "does not exist");
        }
        userRepository.deleteById(studentId);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}
