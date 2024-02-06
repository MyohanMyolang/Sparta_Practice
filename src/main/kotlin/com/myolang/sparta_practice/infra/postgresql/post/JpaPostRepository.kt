package com.myolang.sparta_practice.infra.postgresql.post

import com.myolang.sparta_practice.domain.post.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface JpaPostRepository : CrudRepository<PostEntity, Long> {

}