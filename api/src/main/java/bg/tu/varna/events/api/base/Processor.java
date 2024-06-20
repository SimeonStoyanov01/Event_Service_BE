package bg.tu.varna.events.api.base;

public interface Processor <R extends ProcessorResponse, I extends ProcessorRequest> {
	R process(I request);
}