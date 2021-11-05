package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.User;
import models.validators.UserValidator;
import utils.EncryptUtil;

/**
 * ユーザーテーブルの操作にかかわる処理を行うクラス
 *
 */
public class UserService extends ServiceBase {

    /**
     * ユーザーID、パスワードを条件に取得したデータをUserViewのインスタンスで返却する
     * @param userId ユーザーID
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得データのインスタンス 取得できない場合null
     */
    public UserView findOne(String userId, String plainPass, String pepper) {
        User u = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //ユーザーIDとハッシュ化済パスワードを条件に未削除のユーザーを1件取得する
            u = em.createNamedQuery(JpaConst.Q_USR_GET_BY_CODE_AND_PASS, User.class)
                    .setParameter(JpaConst.JPQL_PARM_USR_ID, userId)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return UserConverter.toView(u);

    }
    /**
     * idを条件に取得したデータをUserViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public UserView findOne(int id) {
        User u = findOneInternal(id);
        return UserConverter.toView(u);
    }

    public long countByUserId(String userId) {

        //指定した社員番号を保持する従業員の件数を取得する
        long employees_count = (long) em.createNamedQuery(JpaConst.Q_USR_COUNT_RESISTERED_BY_CODE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_USR_ID, userId)
                .getSingleResult();
        return employees_count;
    }
/**
 * 画面から入力されたユーザーの登録内容をもとにデータを一件作成し、ユーザーテーブルに登録する
 * @param uv   画面から入力されたユーザーの登録内容
 * @param pepper    pepper文字列
 * @return  バリデーションや登録処理中に発生したエラーのリスト
 */
    public List<String> create(UserView uv, String pepper) {

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper);
        uv.setPassword(pass);

        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        uv.setCreatedAt(now);
        uv.setUpdatedAt(now);

        //登録内容のバリデーションを行う
        List<String> errors = UserValidator.validate(this, uv, true, true);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            create(uv);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }
    /**
     * 画面から入力されたユーザーの更新内容をもとにデータを一件作成し、ユーザーテーブルを更新する
     * @param uv    画面から入力されたユーザーの登録情報
     * @param pepper    Pepper文字列
     * @return  バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(UserView uv, String pepper) {

        //idを条件に登録済みのユーザー情報を取得する
        UserView savedUsr = findOne(uv.getId());

        boolean validateCode = false;
        if (!savedUsr.getUserId().equals(uv.getUserId())) {
            //ユーザーIDを更新する場合

            //ユーザーIDについてのバリデーションを行う
            validateCode = true;
            //変更後のユーザーIDを設定する
            savedUsr.setUserId(uv.getUserId());
        }

        boolean validatePass = false;
        if (uv.getPassword() != null && !uv.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリデーションを行う
            validatePass = true;

            //変更後のパスワードをハッシュ化し設定する
            savedUsr.setPassword(
                    EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper));
        }

        savedUsr.setName(uv.getName()); //変更後の氏名を設定する
        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedUsr.setUpdatedAt(today);

        //更新内容についてバリデーションを行う
        List<String> errors = UserValidator.validate(this, savedUsr, validateCode, validatePass);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedUsr);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }
    /**idを条件にユーザーデータを削除する
     *
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みのユーザー情報を取得する
       UserView savedUsr = findOne(id);


        //削除処理を行う
        destroy(savedUsr);

    }
    /**
     * ユーザーIDとパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param userId    ユーザーID
     * @param plainPass パスワード
     * @param pepper
     * @return  認証結果を返却する（成功:true    失敗:false）
     */
    public Boolean validateLogin(String userId, String plainPass, String pepper) {

        boolean isValidUser = false;
        if (userId != null && !userId.equals("") && plainPass != null && !plainPass.equals("")) {
            UserView uv = findOne(userId, plainPass, pepper);

            if (uv != null && uv.getId() != null) {

                //データが取得できた場合、認証成功
                isValidUser = true;
            }
        }
        //認証結果を返却する
        return isValidUser;
    }
    /**
     * idを条件にデータを一件取得し、Userのインスタンスで返却する
     * @param id
     * @return  取得データのインスタンス
     */
    private User findOneInternal(int id) {
        User u = em.find(User.class, id);

        return u;
    }
    /**
     * ユーザーデータを一件登録する
     * @param uv    ユーザーデータ
     *
     */
    private void create(UserView uv) {

        em.getTransaction().begin();
        em.persist(UserConverter.toModel(uv));
        em.getTransaction().commit();

    }
    /**
     * ユーザーデータを更新する
     * @param uv
     */
    private void update(UserView uv) {

        em.getTransaction().begin();
        User u = findOneInternal(uv.getId());
        UserConverter.copyViewToModel(u, uv);
        em.getTransaction().commit();

    }
    /**
     * ユーザーデータを削除する
     */
    private void destroy(UserView uv) {
        User usr = (User)em.createNamedQuery(JpaConst.Q_USR_GET_BY_ID,User.class)
                .setParameter(JpaConst.JPQL_PARM_USER,uv.getId())
                .getSingleResult();


em.getTransaction().begin();
em.remove(usr);
em.getTransaction().commit();

    }

}
