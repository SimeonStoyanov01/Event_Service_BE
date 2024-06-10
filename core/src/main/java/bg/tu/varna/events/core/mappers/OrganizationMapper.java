package bg.tu.varna.events.core.mappers;


import bg.tu.varna.events.api.model.OrganizationModel;
import bg.tu.varna.events.persistence.entities.Organization;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper implements Converter<Organization, OrganizationModel> {

	@Override
	public OrganizationModel convert(Organization source) {
		return OrganizationModel.builder()
				.organizationId(source.getOrganizationId())
				.organizationName(source.getOrganizationName())
				.organizationAddress(source.getOrganizationAddress())
				.credibilityScore(source.getCredibilityScore())
				.organizationStatus(source.getOrganizationStatus().name())
				.build();
	}
}