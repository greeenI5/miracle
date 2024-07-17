package com.green.miracle.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity {

	@ManyToOne
    @JoinColumn(name = "board_no", nullable = false) // 게시글 외래키
    private BoardEntity board; // 게시글 엔티티
	
	@ManyToOne
    @JoinColumn(name = "no", nullable = false) // 사원 외래키
    private EmployeeEntity employee; // 사용자 엔티티
	
	@Column(columnDefinition = "blob", nullable = false)
	private String comment_content; //댓글 내용
	
	@Column(nullable = false)
	private int likeCount; //좋아요 수
	
}
