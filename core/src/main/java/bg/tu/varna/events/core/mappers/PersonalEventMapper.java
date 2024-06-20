package bg.tu.varna.events.core.mappers;

import bg.tu.varna.events.api.model.PersonalEventModel;
import bg.tu.varna.events.persistence.entities.PersonalEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonalEventMapper implements Converter<PersonalEvent, PersonalEventModel> {

	@Override
	public PersonalEventModel convert(PersonalEvent source) {
		return PersonalEventModel
				.builder()
				.personalEventId(source.getPersonalEventId())
				.eventDescription(source.getEventDescription())
				.eventDateTime(source.getEventDateTime())
				.eventName(source.getEventName())
				.eventLocation(source.getEventLocation())
				.build();
	}
}
