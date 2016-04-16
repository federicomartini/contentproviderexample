package app.com.ttins.contentproviderexample.ui;

public interface MainMVP {

    interface PresenterOps {
        void onButtonClick ();
    }

    interface ModelOps {
        void queryRandomData();
    }

    interface RequiredPresenterOps {
        void onLoadFinished();
    }

    interface RequiredViewOps {
        void onShowQueryResult();
    }

}
