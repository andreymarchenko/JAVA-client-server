package Model;

public class BModelServer {
    static IModelServer m = new ModelServer();
    public static IModelServer createModelServer() {
        return m;
    }
}
