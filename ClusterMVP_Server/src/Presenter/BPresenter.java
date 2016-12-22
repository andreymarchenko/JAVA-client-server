package Presenter;

import Model.IModelServer;
import View.IViewServer;

public class BPresenter {
    public static IPresenter createPresenter(IViewServer v) {
        return new Presenter(v);
    }
}
