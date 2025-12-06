package com.example.travelwise.service;

import com.example.travelwise.dto.CartDTO;
import com.example.travelwise.dto.UserDTO;
import com.example.travelwise.entity.User;
import com.example.travelwise.mapper.UserMapper;
import com.example.travelwise.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final CartService cartService;
    private final ImageCloudinaryService imageCloudinaryService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper,
                       ImageService imageService, CartService cartService,
                       ImageCloudinaryService imageCloudinaryService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageService = imageService;
        this.cartService = cartService;
        this.imageCloudinaryService = imageCloudinaryService;
    }

    public List<UserDTO> getAllUsers(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAll(pageable)
                .stream()
                .map(userMapper::mapToDTO)
                .toList();
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user with id: " + userId));
        return userMapper.mapToDTO(user);
    }

    public UserDTO createUser(MultipartFile profilePic, UserDTO userDTO) {
        User user = userRepository.save(userMapper.mapToEntity(userDTO));
        if (profilePic != null) {
//           String profilePicUrl = this.imageService.uploadImageToBucket(profilePic);
            try {
                String profilePicUrl = this.imageCloudinaryService.uploadImage(profilePic);
                user.setProfilePictureUrl(profilePicUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
           userRepository.save(user);
        }
        CartDTO newUserCart = new CartDTO(0.0,0,user.getId());
        CartDTO newCart = cartService.createCart(newUserCart);
        user.setCartId(newCart.getId());
        return userMapper.mapToDTO(user);
    }

    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No user with email: " + email));
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

    public boolean userExist(String userEmail) {
        return userRepository.findByEmail(userEmail).isPresent();
    }

    @Transactional
    public void deleteUserById(Long userId) {
        User userToBeDeleted = userRepository.findById(userId).orElse(null);
        if(userToBeDeleted != null) {
            cartService.deleteCartByUserId(userId);
        }
//        if(userToBeDeleted.getProfilePictureUrl() != null) {
//            imageService.deleteFile(userToBeDeleted.getProfilePictureUrl());
//        }
        userRepository.deleteById(userId);
    }
}
