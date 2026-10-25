package tech.buildrun.demojpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.buildrun.demojpa.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findByName(String name, Pageable pageable);

    Page<UserEntity> findByAgeGreaterThanEqual(Long age, Pageable pageable);

    Page<UserEntity> findByNameAndAgeGreaterThanEqual(String name, Long age, Pageable pageable);
}