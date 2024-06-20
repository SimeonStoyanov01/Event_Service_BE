package bg.tu.varna.events.api.base;

public interface VoidProcessor <I extends ProcessorRequest> {
	void process(I request);
}