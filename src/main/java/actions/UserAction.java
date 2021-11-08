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

                        //セッションに登録完了のフラッシュメッセージを設定
                        putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                        //一覧画面にリダイレクト
                        redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
                    }



                }
            }

}
