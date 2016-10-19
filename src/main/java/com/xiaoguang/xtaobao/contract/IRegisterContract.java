package com.xiaoguang.xtaobao.contract;

import com.xiaoguang.xtaobao.base.BasePresenter;
import com.xiaoguang.xtaobao.base.BaseView;

/**
 * 契约类、使view 和 Presenter 之前的方法清晰
 * Created by 11655 on 2016/10/18.
 */

public class IRegisterContract {
   public interface IIRegisterView extends BaseView<IIRegisterPrensenter>{

    }
    public interface IIRegisterPrensenter extends BasePresenter<IIRegisterView>{

    }
}
