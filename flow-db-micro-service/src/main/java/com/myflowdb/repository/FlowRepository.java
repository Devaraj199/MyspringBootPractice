package com.myflowdb.repository;

import com.myflowdb.entity.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FlowRepository extends JpaRepository<Flow,Long> {

    Optional<Flow> findOneByFlowName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE flows SET ui_monitoring_status = 'mute', updated_on = now() WHERE flow_name = :flowName",nativeQuery = true)
    void muteFlow(@Param("flowName") String flowName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE flows SET ui_monitoring_status = null, updated_on = now() WHERE flow_name = :flowName",nativeQuery = true)
    void unMuteFlow(@Param("flowName") String flowName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE flows SET mute_alarm = 'mute', updated_on = now() WHERE flow_name = :flowName",nativeQuery = true)
    void muteAlarmFlow(@Param("flowName") String flowName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE flows SET mute_alarm = null, updated_on = now() WHERE flow_name = :flowName",nativeQuery = true)
    void unMuteAlarmFlow(@Param("flowName") String flowName);
}
