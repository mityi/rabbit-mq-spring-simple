package rda.rabbitmq;

public interface QueueName {
    String welcome = "queue-welcome";
    String offer = "queue-offer";

    String info = "info";
    String problem = "problem";


    //topics
    String in = "*.in.*";
    String start = "start.#";
    String end = "*.*.end";

    //generate address for topics
    default String in(String first, String last) {
        return first + ".in." + last;
    }

    default String start(String... second) {
        return "start." + String.join(".", second);
    }

    default String end(String first, String second) {
        return first + '.' + second + ".end";
    }

}
