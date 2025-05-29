package dev.bedesi.sms.chatmanagementsystem.service;
import dev.bedesi.sms.chatmanagementsystem.dto.ChatGroupDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.repository.ChatGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatGroupService {

    @Autowired
    private ChatGroupRepository chatGroupRepository;


//    public Optional<List<ChatGroupDTO>> getAllChatGroupEntities() {
//        Optional<List<ChatGroupEntity>> chatGroupEntityList= chatGroupRepository.findAllActive();
//        List<ChatGroupDTO> chatGroupDTOList =new ArrayList<>();
//        if(chatGroupEntityList.isPresent()){
//            chatGroupDTOList= chatGroupEntityList.get().stream().map(chatGroupEntity -> {
//                ChatGroupDTO chatGroupDTO=new ChatGroupDTO();
//                chatGroupDTO.setAllFieldsFromEntity(chatGroupEntity);
//                return chatGroupDTO;
//            }).collect(Collectors.toList());
//        }
//        return Optional.of(chatGroupDTOList);
//    }


    public Optional<List<ChatGroupDTO>> getAllChatGroupEntitiesByUserId(String user_id) {
        Optional<List<Object[]>> queryResult= chatGroupRepository.findGroupByUserAccess(user_id);
        List<ChatGroupDTO> chatGroupDTOList =new ArrayList<>();
        if(queryResult.isPresent()){
            chatGroupDTOList= queryResult.get().stream().map(result -> {
                ChatGroupDTO chatGroupDTO=new ChatGroupDTO();
                chatGroupDTO.setFieldsFromQueryResult(result);
                return chatGroupDTO;
            }).collect(Collectors.toList());
        }
        return Optional.of(chatGroupDTOList);
    }

}
