package Model;

import Presenter.IPresenter;

public class ModelServer  implements IModelServer {
    private IPresenter presenter;
    private QueueHandlerThread queueHandlerThread;

    public IPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
    
    public ModelServer() {
        queueHandlerThread = new QueueHandlerThread();
    }
}
