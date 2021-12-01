package com.demo.chat.domain.service;

import com.demo.chat.application.dto.chat.MessageDto;
import com.demo.chat.domain.entity.Message;
import com.demo.chat.infrastructure.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

//Сервис для работы не посредственно с чатом
@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    //Сохранение сообщения в базу
    public void sendMessage(@Validated MessageDto message) {
        messageRepository.save(modelMapper.map(message, Message.class));
    }
    //Достаем из базы последние N сообщений
    public List<MessageDto> getHistory(Integer count) {
        return messageRepository.findAll(PageRequest.of(0, count, Sort.Direction.ASC, "createdAt")).stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
    }

}
