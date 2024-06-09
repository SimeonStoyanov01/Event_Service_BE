package bg.tu.varna.events.core.scheduled;

import bg.tu.varna.events.persistence.entities.Event;
import bg.tu.varna.events.persistence.entities.EventStatus;
import bg.tu.varna.events.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpiredEventsScheduler {
	private final EventRepository eventRepository;

	@Scheduled(cron = "0 0 0 * * ?")
	public void updateExpiredEvents() {
		LocalDateTime now = LocalDateTime.now();
		List<Event> expiredEvents = eventRepository.findByEventDateTimeBeforeAndStatusNot(now, EventStatus.EXPIRED);
		for (Event event : expiredEvents) {
			if (!event.getStatus().equals(EventStatus.EXPIRED)) {
				event.setStatus(EventStatus.EXPIRED);
			}
		}
		eventRepository.saveAll(expiredEvents);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void onStartup() {
		updateExpiredEvents();
	}
}
