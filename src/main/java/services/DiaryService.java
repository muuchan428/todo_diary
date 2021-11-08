package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.UserConverter;
import actions.views.UserView;
import actions.views.DiaryConverter;
import actions.views.DiaryView;
import constants.JpaConst;
import models.Diary;
import models.validators.DiaryValidator;
/**
 *
 * 日記テーブルの捜査にかかわる処理を行うクラス
 *
 */
public class DiaryService extends ServiceBase {
    /**
     * 指定したユーザーが作成した日記データを、指定されたページ数の一覧画面に表示する分取得しDiaryViewのリストで返却する
     * @param user  ユーザー
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<DiaryView> getMinePerPage(UserView user, int page) {

        List<Diary> diaries = em.createNamedQuery(JpaConst.Q_DIA_GET_ALL_MINE, Diary.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return DiaryConverter.toViewList(diaries);
    }
    /**
     * 指定したユーザーが作成した日記データの件数を取得
     * @param user
     * @return  日記データの件数
     */
    public long countAllMine(UserView user) {

        long count = (long) em.createNamedQuery(JpaConst.Q_DIA_COUNT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .getSingleResult();

        return count;
    }
    /**
     * idを条件に取得したデータをDiaryViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public DiaryView findOne(int id) {
        return DiaryConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された日記の登録内容を元にデータを1件作成し、日記テーブルに登録する
     * @param dv 日記の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(DiaryView dv) {
        List<String> errors = DiaryValidator.validate(dv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            dv.setCreatedAt(ldt);
            dv.setUpdatedAt(ldt);
            createInternal(dv);
        }
        //バリデーションで発生したエラーを返却
        return errors;
    }
    /**
     * 画面から入力された日記の登録内容を元に、日記データを更新する
     * @param dv 日記の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(DiaryView dv) {

        //バリデーションを行う
        List<String> errors = DiaryValidator.validate(dv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            dv.setUpdatedAt(ldt);

            updateInternal(dv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Diary findOneInternal(int id) {
        return em.find(Diary.class, id);
    }

    /**
     * 日記データを1件登録する
     * @param dv 日記データ
     */
    private void createInternal(DiaryView dv) {

        em.getTransaction().begin();
        em.persist(DiaryConverter.toModel(dv));
        em.getTransaction().commit();

    }

    /**
     * 日記データを更新する
     * @param dv 日記データ
     */
    private void updateInternal(DiaryView dv) {

        em.getTransaction().begin();
        Diary d = findOneInternal(dv.getId());
        DiaryConverter.copyViewToModel(d, dv);
        em.getTransaction().commit();

    }

}
