package yfu.practice.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yfu.practice.springbatch.entity.YfuCard;

@Repository
public interface YfuCardRepo extends JpaRepository<YfuCard, String> {
}