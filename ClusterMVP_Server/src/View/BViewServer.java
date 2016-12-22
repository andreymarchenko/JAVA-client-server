package View;

public class BViewServer {
    static IViewServer v = new ViewServer();
    public static IViewServer createViewServer() {
        return v;
    }
}
