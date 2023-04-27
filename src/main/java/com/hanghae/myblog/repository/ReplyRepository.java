package com.hanghae.myblog.repository;

import com.hanghae.myblog.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
