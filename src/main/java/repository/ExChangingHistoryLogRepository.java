package repository;

import model.history.ExChangeHistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExChangingHistoryLogRepository extends JpaRepository<ExChangeHistoryLog, Long> {
}
