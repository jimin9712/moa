package com.app.moa.controller.thesis_post;

import com.app.moa.domain.member.MemberVO;
import com.app.moa.domain.post.Pagination;
import com.app.moa.domain.post.Search;
import com.app.moa.domain.thesis_post.ThesisPostDTO;
import com.app.moa.domain.thesis_post.ThesisPostVO;
import com.app.moa.service.thesis_post.ThesisPostService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/thesis/*")
@RequiredArgsConstructor
@Slf4j
public class ThesisPostController {
    private final ThesisPostService thesisPostService;
    private final HttpSession session;

    @GetMapping("thesis-list")
    public String getList(Pagination pagination, Model model, @RequestParam(defaultValue = "recent") String view, Search search) {

        pagination.setTotal(thesisPostService.getTotal());
        pagination.progress();
        List<ThesisPostDTO> posts = thesisPostService.getList(pagination,search);

        if (posts.isEmpty()) {
            log.info("포스트 없음");
        }

//        if ("popular".equals(view)) {
//            // postView 기준으로 정렬
//            posts = thesisPostService.getListByPopular(pagination);
//        } else {
//            // 최신순으로 정렬
//            posts = thesisPostService.getList(pagination);
//        }

        model.addAttribute("pagination", pagination);
        model.addAttribute("posts", posts);

        return "thesis/thesis-list";
    }


    // 글 작성 페이지 이동1
    @GetMapping("thesis-write1")
    public String getToWriteForm1(ThesisPostDTO thesisPostDTO) {
        return "thesis/thesis-write1";
    }

    @PostMapping("thesis-write1")
    public RedirectView thesisWrite1(ThesisPostDTO thesisPostDTO) {
        thesisPostDTO.setMemberId(1L);
        thesisPostDTO.setPostType(2);
        log.info("Received ThesisPostDTO: {}", thesisPostDTO);

        if (thesisPostDTO.getPostTitle() == null || thesisPostDTO.getResearchStartDate() == null || thesisPostDTO.getResearchStartDate() == null) {
            log.error("필수 데이터가 없습니다.");
            return new RedirectView("/thesis/thesis-write1");
        }

        // 데이터가 문제없으면 세션에 저장
        session.setAttribute("thesisPost", thesisPostDTO);

        return new RedirectView("/thesis/thesis-write2");
    }


    // 글 작성 페이지 이동2
    @GetMapping("thesis-write2")
    public String getToWriteForm2(ThesisPostDTO thesisPostDTO) {
        return "thesis/thesis-write2";
    }

    @PostMapping("thesis-write2")
    public RedirectView thesisWrite2(ThesisPostDTO thesisPostDTO) {

        // 세션에서 thesisPost 객체를 가져옵니다.
        ThesisPostDTO thesisPost1Data = (ThesisPostDTO) session.getAttribute("thesisPost");

        // 세션에서 thesisPost1Data가 null이 아닌지 확인합니다.
        if (thesisPost1Data == null) {
            log.error("제대로 전달 안됨");
        }

        // 전달받은 데이터 검증
        log.info("받아온 ThesisPostDTO (2단계): {}", thesisPostDTO);

        // 필수 입력 데이터인 postContent와 researchSchedule 검증
        if (thesisPostDTO.getPostContent() == null || thesisPostDTO.getPostContent().trim().isEmpty() ||
                thesisPostDTO.getResearchSchedule() == null || thesisPostDTO.getResearchSchedule().trim().isEmpty()) {
            log.error("필수 데이터가 없습니다.");
            return new RedirectView("/thesis/thesis-write2"); // 다시 작성 페이지로 리다이렉트
        }

        // 정상적으로 세션에서 데이터를 가져온 경우
        thesisPost1Data.setResearchSchedule(thesisPostDTO.getResearchSchedule());
        thesisPost1Data.setResearchRequirement(thesisPostDTO.getResearchRequirement());
        thesisPost1Data.setPostContent(thesisPostDTO.getPostContent());

        // 최종적으로 데이터베이스에 저장
        thesisPostService.write(thesisPost1Data);

        return new RedirectView("/thesis/thesis-list");
    }



    // 글 조회
    @GetMapping("/thesis/thesis-inquiry")
    public String getThesisInquiry(@RequestParam("postId") Long postId, Model model) {
        Optional<ThesisPostVO> post = thesisPostService.getById(postId);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
        } else {
            return "redirect:/thesis/thesis-list";
        }

        log.info(String.valueOf(post.get()));
        return "thesis/thesis-inquiry";

    }

    // 글 수정 페이지 이동 1
    @GetMapping("/thesis-modification1")
    public String getToUpdateForm1(@RequestParam("postId") Long postId, Model model) {

        Optional<ThesisPostVO> post = thesisPostService.getById(postId);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
        } else {
            return "redirect:/thesis/thesis-list";
        }
        return "thesis/thesis-modification1";
    }

    @PostMapping("/thesis-modification1")
    public RedirectView thesisModification1(ThesisPostDTO thesisPostDTO, HttpSession session) {
        thesisPostDTO.setMemberId(1L);
        thesisPostDTO.setPostType(2);
        // postId가 null일 경우 처리
        if (thesisPostDTO.getId() == null) {
            log.error("postId가 null입니다.");
            return new RedirectView("/thesis/thesis-list");
        }

        // 필수 데이터 확인
        if (thesisPostDTO.getPostTitle() == null || thesisPostDTO.getResearchStartDate() == null) {
            log.error("필수 데이터가 없습니다.");
            return new RedirectView("/thesis/thesis-modification1?postId=" + thesisPostDTO.getId());
        }

        // 세션에 수정된 데이터 저장
        session.setAttribute("thesisPost", thesisPostDTO);

        // 로그로 데이터 확인
        log.info("Session에 저장된 데이터: {}", thesisPostDTO);

        return new RedirectView("/thesis/thesis-modification2?postId=" + thesisPostDTO.getId());
    }

    @GetMapping("/thesis-modification2")
    public String getToUpdateForm2(@RequestParam("postId") Long postId, Model model) {
        Optional<ThesisPostVO> post = thesisPostService.getById(postId);  // postId로 데이터를 조회
        if (post.isPresent()) {
            model.addAttribute("post", post.get());  // 조회한 데이터를 model에 담아서 화면에 전달
        } else {
            return "redirect:/thesis/thesis-list";
        }
        return "thesis/thesis-modification2";  // 해당 페이지로 이동
    }


    @PostMapping("/thesis-modification2")
    public RedirectView thesisModification2(ThesisPostDTO thesisPostDTO, HttpSession session) {
        // 1단계에서 세션에 저장된 데이터를 가져옴
        ThesisPostDTO sessionPost = (ThesisPostDTO) session.getAttribute("thesisPost");

        if (sessionPost == null) {
            log.error("세션에서 데이터를 가져오지 못함.");
            return new RedirectView("/thesis/thesis-modification1?postId=" + thesisPostDTO.getId());
        }

        // 2단계에서 입력받은 데이터와 +
        sessionPost.setPostContent(thesisPostDTO.getPostContent() != null ? thesisPostDTO.getPostContent() : sessionPost.getPostContent());
        sessionPost.setResearchSchedule(thesisPostDTO.getResearchSchedule() != null ? thesisPostDTO.getResearchSchedule() : sessionPost.getResearchSchedule());
        sessionPost.setResearchRequirement(thesisPostDTO.getResearchRequirement() != null ? thesisPostDTO.getResearchRequirement() : sessionPost.getResearchRequirement());

        // 최종적으로 데이터를 업데이트
        thesisPostService.update(sessionPost);

        // 세션 데이터 날림
        session.removeAttribute("thesisPost");

        return new RedirectView("/thesis/thesis-inquiry?postId=" + sessionPost.getId());
    }

//  글 삭제
    @GetMapping("/delete")
    public RedirectView deletePost(@RequestParam("postId") Long postId) {
        // postId에 해당하는 게시글 삭제
        thesisPostService.delete(postId);

        // 삭제 후 리스트 페이지로 리다이렉트
        return new RedirectView("/thesis/thesis-list");
    }
}
