/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Игорь
 */
public class BModelServer {
    static IModelServer m = new ModelServer();
    public static IModelServer createModelServer() {
        return m;
    }
}
