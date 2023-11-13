package capstone.bookdiary.controller;

import capstone.bookdiary.domain.dto.LoginDto;
import capstone.bookdiary.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 관련 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return memberService.login(loginDto);
    }
}
