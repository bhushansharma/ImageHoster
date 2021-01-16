package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value="/image/{id}/{title}/comments",method = RequestMethod.POST)
    public String updateComment(@PathVariable("id") Integer id, @PathVariable("title") String title, @RequestParam("comment") String strComment, Model model, HttpSession session) {
        Image image = imageService.getImage(id);
        User user = (User) session.getAttribute("loggeduser");
        System.out.println(strComment);
        Comment comment = new Comment();
        comment.setCreateDate(new Date());
        comment.setText(strComment);
        comment.setImage(image);
        comment.setUser(user);
        commentService.addComment(comment);
        model.addAttribute("image", image);
        model.addAttribute("tags", image.getTags());
        model.addAttribute("comments", image.getComments());
        return "redirect:/images/"+image.getId()+"/"+image.getTitle();
    }
}
