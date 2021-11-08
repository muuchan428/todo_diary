package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.DiaryView;
import constants.MessageConst;

/**
 * 日報インスタンスに設定されている値のバリデーションを行うクラス
 */
public class DiaryValidator {

    /**
     * 日報インスタンスの各項目についてバリデーションを行う
     * @param dv 日報インスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(DiaryView dv) {
        List<String> errors = new ArrayList<String>();



        //内容のチェック
        String contentError = validateContent(dv.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }

        return errors;
    }


    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param content 内容
     * @return エラーメッセージ
     */
    private static String validateContent(String content) {
        if (content == null || content.equals("")) {
            return MessageConst.E_NOCONTENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
}