/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Presenter.BPresenter;
import Presenter.IPresenter;

/**
 *
 * @author Игорь
 */
public class ViewServer implements IViewServer {
    private IPresenter presenter;
    
    public ViewServer() {
        presenter = BPresenter.createPresenter(this);
    }
}
