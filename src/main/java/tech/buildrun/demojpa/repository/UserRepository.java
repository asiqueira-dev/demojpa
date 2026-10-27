package tech.buildrun.demojpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.buildrun.demojpa.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM tb_users WHERE name = ?1",
            countQuery = "SELECT COUNT(*) FROM tb_users WHERE name = ?1",
            nativeQuery = true)
    Page<UserEntity> findByName(String name, Pageable pageable);

    @Query(value = "SELECT * FROM tb_users WHERE age >= ?1",
            countQuery = "SELECT COUNT(*) FROM tb_users WHERE age >= ?1",
            nativeQuery = true)
    Page<UserEntity> findByAgeGreaterThanEqual(Long age, Pageable pageable);

    @Query(value = "SELECT * FROM tb_users WHERE name = ?1 AND age >= ?2",
            countQuery = "SELECT COUNT(*) FROM tb_users WHERE name = ?1 AND age >= ?2",
            nativeQuery = true)
    Page<UserEntity> findByNameAndAgeGreaterThanEqual(String name, Long age, Pageable pageable);

    @Query(value = "SELECT * FROM tb_users",
            countQuery = "SELECT COUNT(*) FROM tb_users ",
            nativeQuery = true)
    Page<UserEntity> findAll(PageRequest pageRequest);

}