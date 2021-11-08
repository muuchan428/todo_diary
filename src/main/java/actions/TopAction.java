package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {


    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        //メソッドを実行
        invoke();
    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {



        //セッションからログイン中の従業員情報を取得
       UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);


       putRequestScope(AttributeConst.USER, loginUser);
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数



        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}