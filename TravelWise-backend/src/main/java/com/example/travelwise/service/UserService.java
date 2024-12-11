package com.example.travelwise.service;

import com.example.travelwise.dto.UserDTO;
import com.example.travelwise.entity.User;
import com.example.travelwise.mapper.UserMapper;
import com.example.travelwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, ImageService imageService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageService = imageService;
    }

    public List<UserDTO> getAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable)
                .stream()
                .map(userMapper::mapToDTO)
                .toList();
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new ResourceNotFoundException("No user with id: " + userId);
        }
        return userMapper.mapToDTO(user);
    }

    public UserDTO createUser(MultipartFile profilePic, UserDTO userDTO) {
        if (profilePic != null) {
           String profilePicUrl = this.imageService.uploadImageToBucket(profilePic);
           userDTO.setProfilePictureUrl(profilePicUrl);
        }
        User user = userRepository.save(userMapper.mapToEntity(userDTO));
        return userMapper.mapToDTO(user);
    }

    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new ResourceNotFoundException("No user with such email");
        }
        if(!user.getPassword().equals(password)){
            throw new ResourceNotFoundException("Password does not match email");
        }
        return userMapper.mapToDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO, Long userId) {
        if(userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("No user with such id");
        }
        if(userDTO.getId() != null && !userDTO.getId().equals(userId)) {
            throw new InvalidParameterException("user id in body does not match id in request url");
        }
        userDTO.setId(userId);
        User updatedUser = userRepository.save(userMapper.mapToEntity(userDTO));
        return userMapper.mapToDTO(updatedUser);
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
