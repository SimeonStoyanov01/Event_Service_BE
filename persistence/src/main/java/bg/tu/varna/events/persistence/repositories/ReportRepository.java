package bg.tu.varna.events.persistence.repositories;

import bg.tu.varna.events.persistence.entities.Report;
import bg.tu.varna.events.persistence.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
	List<Report> findAllByReportStatus(ReportStatus reportStatus);

}
