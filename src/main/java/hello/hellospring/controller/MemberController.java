package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberForm;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Map;

@Controller
public class MemberController {
    private final MemberService memberService;

    //logger
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


//    @GetMapping("/join")
//    public String createForm(MemberForm memberForm){
//        return "join";
//    }

    @GetMapping("/join")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "join";
    }

    /**
     *????????????
     */
    @PostMapping("/join")
    public String create(@Valid @ModelAttribute MemberForm memberForm, BindingResult result){

        if (result.hasErrors()) {   //create ??????

            logger.info("binding result={}",result);            //

            return "join";
        }

        //memberService???????????? join???????????? ????????????.(????????????+????????????)
        memberService.join(memberForm);         //memberService???????????? join???????????? ????????????.(????????????+????????????)
        return "redirect:/";
        //??????????????? ????????? ?????????????????? ??????????????? ???????????????.
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }
    /**
     *????????????
     */
    @GetMapping("/myPage")
    public String myPage(Model model, Authentication authentication){ //????????? HashMap ????????? ?????? ???????????? key?????? value????????? ????????? ??? ??????
//        UserDetails details=(UserDetails)authentication.getPrincipal();
        Member details=(Member)authentication.getPrincipal();
        model.addAttribute("author",details.getUsername());
        model.addAttribute("name", details.getName());
        model.addAttribute("email",details.getEmail());
        return "myPage";
    }

    /**
     *  ?????? ?????? ??????
     * @param memberForm
     * @param errors
     * @param model
     * @return
     */
    @PostMapping("/myPage")
    public String update(@Valid MemberForm memberForm,Errors errors,Model model){

        if (errors.hasErrors()) {
            logger.info("update??????");

            //?????? ????????? ??????
            model.addAttribute("memberForm", memberForm);

            Map<String, String> validatorResult
                    = memberService.validateHandling(errors);

            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "myPage";
        }

        memberService.update(memberForm);
        return "myPage";
    }
}
