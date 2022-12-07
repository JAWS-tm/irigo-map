package fr.eseo.equipe2.pglback;

import fr.eseo.equipe2.pglback.controller.request.OpinionRequest;
import fr.eseo.equipe2.pglback.controller.request.mapper.CommentRequestMapper;
import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.dto.response.Response;
import fr.eseo.equipe2.pglback.enumeration.UserSex;
import fr.eseo.equipe2.pglback.model.User;
import fr.eseo.equipe2.pglback.service.CommentService;
import jdk.internal.jrtfs.JrtPath;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Date;

public class CommentDaoTests {
    private CommentService commentService;

    @PostMapping
    public Response addComment(@RequestBody OpinionRequest OpinionRequest, Principal principal) {
        if (principal == null)
            return Response.unauthorized();
        
        return Response.ok().setPayload(commentService.register(CommentRequestMapper.toCommentDto(OpinionRequest), principal.getName()));
    }
    @Test
    @Order(1)
    @Autowired
    void creatDefaultUser(){
        UserDao.save(new User(
                "eliot.suzanne24@ootlook.com",
                "Test12345!",
                "Elio",
                "Test",
                UserSex.MALE,
                new Date()
        ));
    }
    @Test
    public void testCreatComment (){
        String numberLine = "6";
        Integer notation = 4;
        String commentary = "coucouc";
        OpinionRequest OpinionRequest = new  OpinionRequest{notation, commentary, numberLine};
        addComment(OpinionRequest, creatDefaultUser(););
    }
}
