package org.woodpecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.woodpecker.repository.model.HasId;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.woodpecker.service.util.validation.ValidationUtil.checkExisted;
import static org.woodpecker.service.util.validation.ValidationUtil.checkModification;

// https://stackoverflow.com/questions/42781264/multiple-base-repositories-in-spring-data-jpa
@NoRepositoryBean
@Transactional(readOnly = true)
public interface BaseRepository<T extends HasId> extends JpaRepository<T, Integer> {

    //    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query.spel-expressions
    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.id=:id")
    int delete(int id);

    @Transactional
    default void deleteExisted(int id) {
        checkModification(delete(id), id);
    }

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id")
    T get(int id);

    default T getExisted(int id) {
        return checkExisted(get(id), id);
    }
    @Transactional
    default T update(T updated, Integer id){
        if (isNull(updated.getId())) {
            throw new IllegalStateException("Updated entity has null id");
        }
        if (!updated.getId().equals(id)){
            throw new IllegalStateException("Updated entity has id not equals received");
        }
        return save(updated);
    }

    @Transactional
    default T create(T created){
        if (nonNull(created.getId())){
            throw new IllegalStateException("Created entity has not null id");
        }
        return save(created);
    }
}