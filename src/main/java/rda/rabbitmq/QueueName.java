package rda.rabbitmq;

public interface QueueName {
    String welcome = "queue-welcome";
    String offer = "queue-offer";

    String info = "info";
    String problem = "problem";


    String in = "*.in.*";

    default String in(String first, String last) {
        return first + ".in." + last;
    }

    String start = "start.#";

    default String start(String... second) {
        return "start." + String.join(".", second);
    }

    String end = "*.*.end";

    default String end(String first, String second) {
        return first + '.' + second + ".end";
    }

}
