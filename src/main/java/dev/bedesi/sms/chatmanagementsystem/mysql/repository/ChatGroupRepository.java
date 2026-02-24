package dev.bedesi.sms.chatmanagementsystem.mysql.repository;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ChatGroupRepository extends JpaRepository<ChatGroupEntity, Integer> {
    @Query("SELECT cg.id, cg.name, " +
            "CASE " +
            "  WHEN NOT EXISTS (SELECT 1 FROM ChatGroupAccessEntity cga WHERE cga.chatGroup.id = cg.id AND cga.userId = :userId) THEN 'NO_ACCESS' " +
            "  WHEN EXISTS (SELECT 1 FROM ChatGroupAccessEntity cga WHERE cga.chatGroup.id = cg.id AND cga.userId = :userId AND cga.active = false) THEN 'PENDING' " +
            "  WHEN EXISTS (SELECT 1 FROM ChatGroupAccessEntity cga WHERE cga.chatGroup.id = cg.id AND cga.userId = :userId AND cga.active = true) THEN 'ALLOWED' " +
            "END as accessStatus " +
            "FROM ChatGroupEntity cg")
    Optional<List<Object[]>> findGroupByUserAccess(@Param("userId") String userId);
}


/*
select id, name,
coalesce((select true from chat_group_access cga where user_id = 'ashwija' and cga.group_id = cg.id),false) as user_acess FROM
chat_group cg;
 */