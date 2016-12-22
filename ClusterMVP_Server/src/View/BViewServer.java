/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author Игорь
 */
public class BViewServer {
    static IViewServer v = new ViewServer();
    public static IViewServer createViewServer() {
        return v;
    }
}
