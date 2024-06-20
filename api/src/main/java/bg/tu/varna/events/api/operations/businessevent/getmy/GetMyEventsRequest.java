package bg.tu.varna.events.api.operations.businessevent.getmy;

import bg.tu.varna.events.api.base.ProcessorRequest;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMyEventsRequest implements ProcessorRequest{

 	private Boolean includeSuspended;
}