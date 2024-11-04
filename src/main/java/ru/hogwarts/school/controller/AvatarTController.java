package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class AvatarTController {
    public AvatarService avatarService;

    @GetMapping
    public List<Avatar> getAvatarsPerPage(@RequestParam ("page") Integer pageNumber, @RequestParam("size") Integer pageSize){
        List<Avatar> avatarList= avatarService.getAvatarsPerPage(pageNumber,pageSize);
        return ResponseEntity.ok(avatarList).getBody();
    }
}
