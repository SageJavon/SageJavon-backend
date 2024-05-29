package com.springboot.cli.controller;

import com.springboot.cli.common.base.BaseResponse;
import com.springboot.cli.common.exception.OpException;
import com.springboot.cli.model.VO.ChatVO;
import com.springboot.cli.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chat")
public class ChatController {

    @Resource
    ChatService chatService;

    @PostMapping
    public BaseResponse<Long> createChat() {
        try {
            return BaseResponse.buildSuccess(chatService.insert());
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @GetMapping("/list")
    public BaseResponse<List<ChatVO>> getChatList() {
        try {
            return BaseResponse.buildSuccess(chatService.getList());
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }

    @DeleteMapping
    public BaseResponse<Void> deleteChat(Long chatId) {
        try {
            return BaseResponse.buildSuccess(chatService.delete(chatId));
        } catch (OpException e) {
            return BaseResponse.buildBizEx(e);
        } catch (Exception e) {
            return BaseResponse.buildSysEx(e);
        }
    }
}
