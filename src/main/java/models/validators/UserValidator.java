package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.UserView;
import constants.MessageConst;
import services.UserService;

/**
 * ユーザーインスタンスの設定されている値のバリデーションを行うクラス
 *
 */
public class UserValidator {

    /**
     * 従業員インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param uv UserServiceのインスタンス
     * @param codeDuplicateCheckFlag ユーザーIDの重複チェックをするかどうか(実施する:true,実施しない:false)
     * @param passwordCheckFlag  パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(UserService service, UserView uv, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //ユーザーIDのチェック
        String codeError = validateCode(service, uv.getUserId(), codeDuplicateCheckFlag);
        if (!codeError.equals("")) {
            errors.add(codeError);
        }

        //名前のチェック
        String nameError = validateName(uv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //パスワードのチェック
        String passError = validatePassword(uv.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }
    /**
     * ユーザーIDの入力チェックを行い、エラーメッセージを返却
     * @param service UserService のインスタンス
     * @param userId ユーザーID
     * @param codeDuplicateCheckFlag ユーザーIDの重複チェックをするかどうか(実施する:true,実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateCode(UserService service, String userId, Boolean codeDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if (userId == null || userId.equals("")) {
            return MessageConst.E_NOUSR_CODE.getMessage();
        }

        if (codeDuplicateCheckFlag) {
            //ユーザーIDの重複チェックを実施

            long usersCount = isDuplicateUser(service, userId);

            //同一IDが既に登録されている場合はエラーメッセージを返却
            if (usersCount > 0) {
                return MessageConst.E_EMP_CODE_EXIST.getMessage();
            }
        }
        //エラーがない場合は空文字を返却
        return "";
    }
        /**
         * @param service UserServiceのインスタンス
         * @param userId ユーザーId
         * @return ユーザーテーブルに登録されている同一IDのデータの件数
         */
        private static long isDuplicateUser(UserService service, String userId) {

            long usersCount = service.countByUserId(userId);
            return usersCount;
        }
    /**
     * 名前に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 名前
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

            if (name == null || name.equals("")) {
                return MessageConst.E_NONAME.getMessage();
            }

            //入力値がある場合は空文字を返却
            return "";
        }
    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }
}
