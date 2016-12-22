/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter;

import Model.IModelServer;
import View.IViewServer;

/**
 *
 * @author Игорь
 */
public class BPresenter {
    public static IPresenter createPresenter(IModelServer m, IViewServer v) {
        return new Presenter(m, v);
    }
}
