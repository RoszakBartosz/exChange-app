package repository;

import model.ExChangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExChangingRepository extends JpaRepository<ExChangeRate, Long> {
}



