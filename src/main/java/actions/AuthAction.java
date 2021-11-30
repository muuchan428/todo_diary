package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;


/**
 * 認証に関する処理を行うActionクラス
 *
 */
public class AuthAction extends ActionBase {

    private UserService userService;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        userService = new UserService();

        //メソッドを実行
        invoke();

        userService.close();
    }

    /**
     * ログイン画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void showLogin() throws ServletException, IOException {

        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());



        //ログイン画面を表示
        forward(ForwardConst.FW_LOGIN);
    }

    /**
     * ログイン処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void login() throws ServletException, IOException {

        String userId = getRequestParam(AttributeConst.USR_USR_ID);
        String plainPass = getRequestParam(AttributeConst.USR_PASS);
        String pepper = getContextScope(PropertyConst.PEPPER);

        //有効な従業員か認証する
        Boolean isValidUser = userService.validateLogin(userId, plainPass, pepper);

        if (isValidUser) {
            //認証成功の場合

            //CSRF対策 tokenのチェック
            if (checkToken()) {

                //ログインした従業員のDBデータを取得
                UserView uv = userService.findOne(userId, plainPass, pepper);
                //セッションにログインした従業員を設定
                putSessionScope(AttributeConst.LOGIN_USR, uv);
                //セッションにログイン完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLASH, MessageConst.I_LOGINED.getMessage());
                //トップページへリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
            }
        } else {
            //認証失敗の場合

            //CSRF対策用トークンを設定
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            //認証失敗エラーメッセージ表示フラグをたてる
            putRequestScope(AttributeConst.LOGIN_ERR, "ログインIDかパスワードが間違っています。");
            //入力された従業員コードを設定
            putRequestScope(AttributeConst.USR_USR_ID, userId);

            //ログイン画面を表示
            forward(ForwardConst.FW_LOGIN);
        }
    }

    /**
     * ログアウト処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void logout() throws ServletException, IOException {

        //セッションからログイン従業員のパラメータを削除
        removeSessionScope(AttributeConst.LOGIN_USR);

        //セッションにログアウト時のフラッシュメッセージを追加
        putSessionScope(AttributeConst.FLASH, MessageConst.I_LOGOUT.getMessage());

        //ログイン画面にリダイレクト
        redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);
    }

}