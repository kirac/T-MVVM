package com.code.mvvm.core.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.code.mvvm.callback.OnResultCallBack;
import com.code.mvvm.config.Constants;
import com.code.mvvm.core.data.pojo.dynamic.DynamicListVo;
import com.code.mvvm.core.data.source.DynamicRepository;

/**
 * @author：tqzhang  on 18/8/13 15:21
 */
public class DynamicViewModel extends BaseViewModel<DynamicRepository> {

    private MutableLiveData<DynamicListVo> mDynamicListData;

    public DynamicViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<DynamicListVo> getDynamicList() {
        if (mDynamicListData == null) {
            mDynamicListData = new MutableLiveData<>();
        }
        return mDynamicListData;
    }

    public void getDynamicList(String rn, String token,String lastId) {
        mRepository.loadDynamicList(rn, token,lastId, new OnResultCallBack<DynamicListVo>() {
            @Override
            public void onNoNetWork() {
                loadState.postValue(Constants.NET_WORK_STATE);
            }

            @Override
            public void onNext(DynamicListVo dynamicListVo) {
                mDynamicListData.postValue(dynamicListVo);
                loadState.postValue(Constants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {
                loadState.postValue(Constants.ERROR_STATE);
            }
        });

    }
}
