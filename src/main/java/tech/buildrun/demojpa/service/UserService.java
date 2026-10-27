package tech.buildrun.demojpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.buildrun.demojpa.controller.dto.CreateUserDto;
import tech.buildrun.demojpa.controller.dto.UserUpdateDto;
import tech.buildrun.demojpa.entity.UserEntity;
import tech.buildrun.demojpa.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(CreateUserDto dto) {
        var entity = new UserEntity();

        entity.setName(dto.name());
        entity.setAge(dto.age());
        entity.setCreatedAt(LocalDateTime.now());

        return userRepository.save(entity);
    }

    public Page<UserEntity> findAll(Integer page, Integer pageSize, String orderBy, String name, Long age) {
        var pageRequest = getPageRequest(page, pageSize, orderBy);

        return findByFilter(name, age, pageRequest)
                .orElseGet(() -> userRepository.findAll(pageRequest));
    }

    private Optional<Page<UserEntity>> findByFilter(String name, Long age, PageRequest pageRequest) {
        if (hasText(name) && !isNull(age)) {
            return Optional.of(userRepository.findByNameAndAgeGreaterThanEqual(name, age, pageRequest));
        }

        if (hasText(name)) {
            return Optional.of(userRepository.findByName(name, pageRequest));
        }

        if (!isNull(age)) {
            return Optional.of(userRepository.findByAgeGreaterThanEqual(age, pageRequest));
        }

        return Optional.of(userRepository.findAll(pageRequest));
    }

    private PageRequest getPageRequest(Integer page, Integer pageSize, String orderBy) {
        var direction = "asc".equalsIgnoreCase(orderBy) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, pageSize, direction, "created_at");
    }

    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<UserEntity> updateById(Long userId, UserUpdateDto dto) {
        return userRepository.findById(userId).map(user -> {
            updateFields(dto, user);
            return userRepository.save(user);
        });
    }

    private void updateFields(UserUpdateDto dto, UserEntity user) {
        if (hasText(dto.name())) {
            user.setName(dto.name());
        }

        if (!isNull(dto.age())) {
            user.setAge(dto.age());
        }
    }

    public boolean deleteById(Long userId) {
        var exists = userRepository.existsById(userId);

        if (exists) {
            userRepository.deleteById(userId);
        }

        return exists;
    }
}