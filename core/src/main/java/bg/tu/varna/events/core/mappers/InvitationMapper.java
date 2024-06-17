package bg.tu.varna.events.core.mappers;

import bg.tu.varna.events.api.model.InvitationModel;
import bg.tu.varna.events.persistence.entities.Invitation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvitationMapper implements Converter<Invitation, InvitationModel> {

	@Override
	public InvitationModel convert(Invitation source) {
		var convertedMenuOrNull = source.getMenu() != null ? source.getMenu().getMenuName() : null;
		return InvitationModel
				.builder()
				.invitationId(source.getInvitationId())
				.invitationStatus(source.getInvitationStatus())
				.inviteeEmail(source.getInviteeEmail())
				.personalEventId(source.getPersonalEvent().getPersonalEventId())
				.menuName(convertedMenuOrNull)
				.build();
	}
}
