package com.OnlineApplication.Controller;

import com.OnlineApplication.Entity.UserProfile;
import com.OnlineApplication.Repository.userRepo;
import com.OnlineApplication.ServiceImage.FileService;
import com.OnlineApplication.payload.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private userRepo userRepository;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserProfile userProfile = mapToEntity(userDto);
        UserProfile savedUser = userRepository.save(userProfile);
        UserDto responseDto = mapToDto(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserProfile> allUsers = userRepository.findAll();

        List<UserDto> userDtoList = allUsers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }

        private UserDto mapToDto(UserProfile user) {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setBio(user.getBio());
            dto.setProfilePicture(user.getProfilePicture());
            return dto;
        }

        private UserProfile mapToEntity(UserDto dto) {
            UserProfile user = new UserProfile();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setBio(dto.getBio());

            user.setProfilePicture(dto.getProfilePicture());
            return user;
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User profile deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserProfile(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<UserProfile> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserProfile userProfile = userOptional.get();
            userProfile.setName(userDto.getName());
            userProfile.setBio(userDto.getBio());

            UserProfile updatedUser = userRepository.save(userProfile);
            return ResponseEntity.status(HttpStatus.OK).body(mapToDto(updatedUser));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // implementing the image Posting ;
    @Autowired
    private FileService fileService;

    // for getting path
    @Value("$(project.image)")
    private String path;
    @PostMapping("/{id}")
    public ResponseEntity<String> uploadPostImg(@PathVariable long id, @RequestParam("image")MultipartFile image) throws IOException {
        String fileName = this.fileService.uploadImage(path, image);

        Optional<UserProfile> byId = userRepository.findById(id);
        UserProfile userProfile = byId.get();

        userProfile.setProfilePicture(fileName);
        userRepository.save(userProfile);
        return new ResponseEntity<>("Image Updated " ,HttpStatus.ACCEPTED);
    }
}


//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUserProfileById(@PathVariable Long id) {
//        Optional<UserProfile> userOptional = userRepository.findById(id);
//        return userOptional.map(userProfile -> ResponseEntity.status(HttpStatus.OK).body(mapToDto(userProfile)))
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDto> updateUserProfile(@PathVariable Long id, @RequestBody UserDto userDto) {
//        return userRepository.findById(id)
//                .map(userProfile -> {
//                    userProfile.setName(userDto.getName());
//                    userProfile.setBio(userDto.getBio());
//                    // Update other fields as needed
//                    UserProfile updatedUser = userRepository.save(userProfile);
//                    return ResponseEntity.status(HttpStatus.OK).body(MapToDto(updatedUser));
//                })
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUserProfile(@PathVariable Long id) {
//        if (userRepository.existsById(id)) {
//            userRepository.deleteById(id);
//            return ResponseEntity.status(HttpStatus.OK).body("User profile deleted successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
//        }
//    }