package com.myflowdb.repository;

import com.myflowdb.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<UserTag,Long> {

    Optional<UserTag> findOneByTagNameAndTagValue(String name, String value);

    @Query(value = "select distinct(t.tag_name) from tags t where t.id in( select ft.tag_id from flow_tags ft)", nativeQuery = true)
    List<String> getAllFlowTagNames();

    @Query(value = "select distinct(t.tag_value) from tags t where t.id in (select ft.tag_id from flow_tags ft " +
            "where ft.tag_id in (select t1.id from tags t1 where t1.tag_name=:tagname))", nativeQuery = true)
    List<String> findTagValuesForFlowByTagName(@NonNull String tagname);

}
