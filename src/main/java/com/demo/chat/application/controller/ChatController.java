package com.demo.chat.application.controller;

import com.demo.chat.application.dto.chat.MessageDto;
import com.demo.chat.application.dto.response.Response;
import com.demo.chat.domain.service.ChatService;
import com.demo.chat.utils.SecurityUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    //Отправка сообщения
    @PostMapping("/message")
    public Response<MessageDto> sendMessage(@AuthenticationPrincipal SecurityUser securityUser, @RequestBody @NonNull String text) {
        MessageDto message = MessageDto.builder().owner(securityUser.getUsername()).text(text).build();
        chatService.sendMessage(message);
        return Response.success(message);
    }

    //Получение истории последних N сообщений
    @GetMapping("/history/{count}")
    public Response<List<MessageDto>> getHistory(@PathVariable Integer count) {
        return Response.success(chatService.getHistory(count));
    }

}
