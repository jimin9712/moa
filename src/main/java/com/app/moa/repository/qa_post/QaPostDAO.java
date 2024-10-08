package com.app.moa.repository.qa_post;



import com.app.moa.domain.post.Pagination;
import com.app.moa.domain.qa_post.QaPostDTO;
import com.app.moa.domain.qa_post.QaPostVO;
import com.app.moa.domain.thesis_post.ThesisPostVO;
import com.app.moa.mapper.qa_post.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QaPostDAO {
    private final QaPostMapper qaPostMapper;

    public void save(QaPostVO qaPostVO) {qaPostMapper.insert(qaPostVO);}

    public Optional<QaPostVO> findById(long id) {return qaPostMapper.selectById(id);}
    //    전체 조회
    public List<QaPostDTO> findAll(Pagination pagination) {
        return qaPostMapper.selectAll(pagination);
    }
    //    전체 개수
    public int findCount(){
        return qaPostMapper.selectCount();
    }

    // ID로 프로젝트 포스트 수정
    public void update(QaPostDTO qaPostDTO) {
        qaPostMapper.update(qaPostDTO);
    }

    // ID로 프로젝트 포스트 삭제
    public void delete(Long id) {
        qaPostMapper.deleteById(id);
    }
}