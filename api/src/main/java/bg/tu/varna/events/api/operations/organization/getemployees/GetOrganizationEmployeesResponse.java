package bg.tu.varna.events.api.operations.organization.getemployees;

import bg.tu.varna.events.api.base.ProcessorResponse;
import bg.tu.varna.events.api.model.UserModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationEmployeesResponse implements ProcessorResponse {

	private List<UserModel> employeeModels;
}

