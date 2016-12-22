package View;

import Presenter.BPresenter;
import Presenter.IPresenter;

public class ViewServer implements IViewServer {
    private IPresenter presenter;
    
    public ViewServer() {
        presenter = BPresenter.createPresenter(this);
    }    

    @Override
    public void StartServer() {
        presenter.StartServer();
    }

    @Override
    public void StopServer() {
        presenter.StopServer();
    }
    
}
