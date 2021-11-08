package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
/**
 *
 * ユーザーにかかわる処理を行うActionクラス
 *
 */
import services.UserService;

public class UserAction extends ActionBase {
        private UserService userService;

        /**
         *
         */
        @Override
        public void process() throws ServletException, IOException {
            userService = new UserService();
            //メソッドを実行
            invoke();

          userService.close();
        }

        public void entryNew() throws ServletException, IOException {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.USER, new UserView()); //空のユーザーインスタンス
            //新規登録画面を表示
            forward(ForwardConst.FW_USR_NEW);
        }
        /**
         * 新規登録を行う
         * @throws ServletException
         * @throws IOException
         */
        public void create() throws ServletException, IOException {

                //CSRF対策 tokenのチェック
                if (checkToken()) {

                    //パラメータの値を元にユーザー情報のインスタンスを作成する
                    UserView uv = new UserView(
                            null,
                            getRequestParam(AttributeConst.USR_USR_ID),
                            getRequestParam(AttributeConst.USR_NAME),
                            getRequestParam(AttributeConst.USR_PASS),
                            null,
                            null);


                    //アプリケーションスコープからpepper文字列を取得
                    String pepper = getContextScope(PropertyConst.PEPPER);

                    //ユーザー情報登録
                    List<String> errors = userService.create(uv, pepper);

                    if (errors.size() > 0) {
                        //登録中にエラーがあった場合



                        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                        putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                        putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                        //新規登録画面を再表示
                        forward(ForwardConst.FW_USR_NEW);

                    } else {
                        //登録中にエラーがなかった場合

                        uv =  userService.findOne(getRequestParam(AttributeConst.USR_USR_ID), getRequestParam(AttributeConst.USR_PASS), pepper);//登録されたユーザー情報の取得
                       System.out.println(uv.getId());
                        putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());//セッションに登録完了のフラッシュメッセージを設定
                        putSessionScope(AttributeConst.LOGIN_USR, uv); //入力されたユーザー情報

                        redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
                    }



                }
            }
        /**
         * 詳細画面を表示する
         * @throws ServletException
         * @throws IOException
         */
        public void show() throws ServletException, IOException {

          //セッションからログイン中のユーザー情報を取得
            UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

            putRequestScope(AttributeConst.USER, loginUser); //取得したユーザー情報

            //詳細画面を表示
            forward(ForwardConst.FW_USR_SHOW);
        }
        /**
         * 編集画面を表示する
         * @throws ServletException
         * @throws IOException
         */
        public void edit() throws ServletException, IOException {

            //セッションからログイン中のユーザー情報を取得
            UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.USER, loginUser); //取得したユーザー情報

            //編集画面を表示する
            forward(ForwardConst.FW_USR_EDIT);

        }
        /**
         * 更新を行う
         * @throws ServletException
         * @throws IOException
         */
        public void update() throws ServletException, IOException {

            //CSRF対策 tokenのチェック
            if (checkToken()) {
                //パラメータの値を元にユーザー情報のインスタンスを作成する
                UserView uv = new UserView(
                        toNumber(getRequestParam(AttributeConst.USR_ID)),
                        getRequestParam(AttributeConst.USR_USR_ID),
                        getRequestParam(AttributeConst.USR_NAME),
                        getRequestParam(AttributeConst.USR_PASS),
                        null,
                        null);

                //アプリケーションスコープからpepper文字列を取得
                String pepper = getContextScope(PropertyConst.PEPPER);

                //情報更新
                List<String> errors = userService.update(uv, pepper);

                if (errors.size() > 0) {
                    //更新中にエラーが発生した場合

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                    putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                    //編集画面を再表示
                    forward(ForwardConst.FW_USR_EDIT);
                } else {
                    //更新中にエラーがなかった場合

                    uv =  userService.findOne(toNumber(getRequestParam(AttributeConst.USR_ID)));
                    //セッションに更新完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                    //セッションからログインユーザーのパラメータを削除
                    removeSessionScope(AttributeConst.LOGIN_USR);
                    //セッションにログインしたユーザーを設定
                    putSessionScope(AttributeConst.LOGIN_USR, uv);

                    //showにリダイレクト
                    redirect(ForwardConst.ACT_USR, ForwardConst.CMD_SHOW);
                }
            }
        }
        public void destroy() throws ServletException, IOException {

            //CSRF対策 tokenのチェック
            if (checkToken()) {

                //idを条件にアカウントを削除する
                userService.destroy(toNumber(getRequestParam(AttributeConst.USR_ID)));
                //セッションからログインユーザーのパラメータを削除
                removeSessionScope(AttributeConst.LOGIN_USR);
                //セッションに削除完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

                //ログイン画面の表示
                forward(ForwardConst.FW_LOGIN);
            }
        }

}
