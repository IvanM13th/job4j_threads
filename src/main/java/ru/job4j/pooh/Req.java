package ru.job4j.pooh;

public class Req {

    private static final String POST = "POST";
    private static final String QUEUE = "queue";

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] rsl = content.split("\r\n");
        String[] parse = rsl[0].split("/");
        String type = parse[0].substring(0, parse[0].length() - 1);
        String mode = parse[1];
        String[] sourceToParse = parse[2].split(" ");
        String source = sourceToParse[0];
        String param;
        if (POST.equals(type)) {
           param = rsl[rsl.length - 1];
        } else {
            param = QUEUE.equals(mode)
                    ? "" : parse[3].substring(0, parse[3].length() - 5);
        }
        return new Req(type, mode, source, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}