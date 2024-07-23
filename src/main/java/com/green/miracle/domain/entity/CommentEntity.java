
package com.green.miracle.domain.entity;

import org.hibernate.annotations.ColumnDefault;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentNo; // 게시글 엔티티

	@ManyToOne
	@JoinColumn(name = "empNo", nullable = false) // 사원 외래키 private
	private EmployeeEntity employee; // 작성자
	
	@ManyToOne
	@JoinColumn(name = "boardNo", nullable = false) // 사원 외래키 private
	private BoardEntity board; // 게시글

	@Column(columnDefinition = "text", nullable = false)
	private String commentContent; // 댓글 내용

	@Column(nullable = false)
	@ColumnDefault("0")
	private int likeCount; // 좋아요 수

}
