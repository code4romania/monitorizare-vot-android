package ro.code4.monitorizarevot.domain.params;

public class BaseDataParams {

    private boolean mIsLocal = false;

    public BaseDataParams() {
        this(false);
    }

    public BaseDataParams(boolean isLocal) {
        mIsLocal = isLocal;
    }

    public boolean isLocal() {
        return mIsLocal;
    }

    public void setLocal(boolean local) {
        mIsLocal = local;
    }
}
