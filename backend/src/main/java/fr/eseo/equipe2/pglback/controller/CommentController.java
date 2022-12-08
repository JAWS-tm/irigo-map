package fr.eseo.equipe2.pglback.controller;

import fr.eseo.equipe2.pglback.payload.request.OpinionRequest;
import fr.eseo.equipe2.pglback.payload.request.mapper.CommentRequestMapper;
import fr.eseo.equipe2.pglback.payload.response.Response;
import fr.eseo.equipe2.pglback.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

//import static jdk.internal.net.http.common.Utils.encode;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * give a comment accordingly to the user
     * @param principal current user
     * @return comment (notation and commentary)
     */
    @GetMapping
    public Response getComment(Principal principal) {
        if (principal == null)
            return Response.unauthorized();

        return Response.ok().setPayload(commentService.findByUserEmail(principal.getName()));
    }


    /**
    * add an opinion (notation and commentary) to the DB
    * @param OpinionRequest and principal
    * @return unauthorized if the current user is not allowed and register it in a new row or on the old opinion
    */
    @PostMapping
    public Response addComment(@RequestBody OpinionRequest OpinionRequest, Principal principal) {
        if (principal == null)
            return Response.unauthorized();

        return Response.ok().setPayload(commentService.register(CommentRequestMapper.toCommentDto(OpinionRequest), principal.getName()));
    }
}
