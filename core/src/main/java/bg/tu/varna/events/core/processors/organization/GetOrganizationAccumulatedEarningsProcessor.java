package bg.tu.varna.events.core.processors.organization;

import bg.tu.varna.events.api.operations.businessevent.getearnings.GetEventEarningsResponse;
import bg.tu.varna.events.api.operations.organization.getaccumulated.GetOrganizationAccumulatedEarningsOperation;
import bg.tu.varna.events.api.operations.organization.getaccumulated.GetOrganizationAccumulatedEarningsRequest;
import bg.tu.varna.events.api.operations.organization.getaccumulated.GetOrganizationAccumulatedEarningsResponse;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsOperation;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsRequest;
import bg.tu.varna.events.api.operations.organization.getearnings.GetOrganizationEarningsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GetOrganizationAccumulatedEarningsProcessor implements GetOrganizationAccumulatedEarningsOperation {
	private final GetOrganizationEarningsOperation getOrganizationEarningsOperation;
	@Override
	public GetOrganizationAccumulatedEarningsResponse process(GetOrganizationAccumulatedEarningsRequest request) {
		GetOrganizationEarningsRequest processorRequest = GetOrganizationEarningsRequest
				.builder()
				.organizationId(request.getOrganizationId())
				.build();
		GetOrganizationEarningsResponse process = getOrganizationEarningsOperation.process(processorRequest);
		BigDecimal accumulation = process.getOrganizationEarningsPerEvent().stream().map(GetEventEarningsResponse::getActualEarnings).reduce(BigDecimal.ZERO, BigDecimal::add);
		return GetOrganizationAccumulatedEarningsResponse.builder().accumulatedEarnings(accumulation).build();
	}
}
