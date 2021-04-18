package com.prakashcodes.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<jobs, Integer>{
	java.util.List<jobs> findAll();

}
