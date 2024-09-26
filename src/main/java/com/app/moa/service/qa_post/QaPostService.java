package com.app.moa.service.qa_post;

import com.app.moa.domain.post.Pagination;
import com.app.moa.domain.qa_post.QaPostDTO;

import java.util.List;

public interface QaPostService {
    public void write(QaPostDTO qaPostDTO);
    public  QaPostDTO selectById(Long id);
    public List<QaPostDTO> getList(Pagination pagination);
    public int getTotal();
    public void updatePost(QaPostDTO qaPostDTO);
    public void deletePost(Long id);
}