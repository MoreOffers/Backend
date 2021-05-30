package AIDeliver.com.example.AIDeliver.service.Impl;

import AIDeliver.com.example.AIDeliver.dto.UserDTO;
import AIDeliver.com.example.AIDeliver.enity.Orders;
import AIDeliver.com.example.AIDeliver.enity.User;
import AIDeliver.com.example.AIDeliver.repository.OrderRepository;
import AIDeliver.com.example.AIDeliver.repository.UserRepository;
import AIDeliver.com.example.AIDeliver.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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

        userRepository.save(modelMapper.map(userDTO, User.class));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
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
