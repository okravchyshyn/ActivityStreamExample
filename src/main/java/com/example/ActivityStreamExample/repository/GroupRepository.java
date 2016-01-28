package com.example.ActivityStreamExample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ActivityStreamExample.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
