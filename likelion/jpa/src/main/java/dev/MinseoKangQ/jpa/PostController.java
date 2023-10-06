package dev.MinseoKangQ.jpa;

import com.google.gson.Gson;
import dev.MinseoKangQ.jpa.aspect.LogExecutionTime;
import dev.MinseoKangQ.jpa.aspect.LogArguments;
import dev.MinseoKangQ.jpa.aspect.LogReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    public PostController(@Autowired PostService postService, @Autowired Gson gson) {
        this.postService = postService;
        logger.info(gson.toString());
    }

    @LogArguments
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody PostDto postDto) {
        this.postService.createPost(postDto);
    }

    @LogReturn
    @GetMapping("{id}")
    public PostDto readPost(@PathVariable("id") int id) {
        return this.postService.readPost(id);
    }

    @LogExecutionTime
    @GetMapping("")
    public List<PostDto> readPostAll() {
        return this.postService.readPostAll();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePost(@PathVariable("id") int id, @RequestBody PostDto postDto) {
        this.postService.updatePost(id, postDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePost(@PathVariable("id") int id) {
        this.postService.deletePost(id);
    }

    @GetMapping("test-log")
    public void testLong() {
        logger.trace("TRACE Log Message");
        logger.debug("DEBUG Log Message");
        logger.info("INFO Log Message");
        logger.warn("WARN Log Message");
        logger.error("ERROR Log Message");
    }

}
