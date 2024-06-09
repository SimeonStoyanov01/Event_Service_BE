package bg.tu.varna.events.api.operations.businessevent.getall;

import bg.tu.varna.events.api.base.ProcessorRequest;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllEventsRequest implements ProcessorRequest{

 	private Boolean includeSuspended;
}