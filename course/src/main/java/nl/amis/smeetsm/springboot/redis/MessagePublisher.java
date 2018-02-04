package nl.amis.smeetsm.springboot.redis;

public interface MessagePublisher {
    void publish(final String message);
}
