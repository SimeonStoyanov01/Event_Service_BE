package bg.tu.varna.events.core.mappers;

import bg.tu.varna.events.api.model.EventModel;
import bg.tu.varna.events.persistence.entities.Event;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EventMapper implements Converter<Event, EventModel> {

	@Override
	public EventModel convert(Event source) {
		return EventModel
				.builder()
				.eventId(source.getEventId())
				.eventDescription(source.getEventDescription())
				.eventDateTime(source.getEventDateTime())
				.eventName(source.getEventName())
				.organizationId(source.getOrganization().getOrganizationId())
				.status(source.getStatus())
				.capacity(source.getCapacity())
				.build();
	}
}
