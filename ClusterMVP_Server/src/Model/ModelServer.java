/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Presenter.IPresenter;

/**
 *
 * @author Игорь
 */
public class ModelServer  implements IModelServer {
    private IPresenter presenter;

    public IPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
    
    public ModelServer() {
        
    }
}
