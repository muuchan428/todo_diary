package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    public List<DiaryView> getMinePerPage(UserView user) {

        List<Diary> diaries = em.createNamedQuery(JpaConst.Q_DIA_GET_ALL_MINE, Diary.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
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
     * 指定されたページに表示する、指定したユーザーが日記を作成した日付のリストを返却
     * @param user
     * @param page
     * @return
     */
    public List<LocalDate> getDates(UserView user, int page){
        List<LocalDate> dates = getDates(user);
        List<LocalDate> datesPerPage = new ArrayList<LocalDate>();
        int max = page * JpaConst.ROW_PER_PAGE - 1;//ページに表示されるリスト要素の最終
        int mini = (page - 1) * JpaConst.ROW_PER_PAGE;//ページに表示されるリスト要素の初めの番号
        int size = dates.size();

        if(size > max) {//リストサイズがページ最大データ数より大きいとき
            for(int i = 0; i < JpaConst.ROW_PER_PAGE; i++) {
                datesPerPage.add(dates.get(mini + i));

            }

        } else if(size > mini && size <= max) {//ページ最小～最大データ数内の時
            for(int i = 0; i < (size % JpaConst.ROW_PER_PAGE); i++) {
                datesPerPage.add(dates.get(mini + i));

            }

        }else {

            return null;
        }


        return datesPerPage;

    }
    /**
     * 指定したユーザーが日記を作成した日付のリスト件数を返却
     * @param user
     * @return  日付リスト件数
     */
    public int getAllDate(UserView user) {
        int dates = getDates(user).size();

        return dates;

    }
    /**
     * 指定したユーザーの指定した日付に作成された日記データのリストを返却
     * @param user
     * @param date
     * @return 日記データのリスト
     */
    public List<DiaryView> getCreatedAtDate(UserView user, LocalDate date){
        List<Diary> diaries = em.createNamedQuery(JpaConst.Q_DIA_GET_DATE, Diary.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .setParameter(JpaConst.JPQL_PARM_DIA_DATE, date)
                .getResultList();

        return DiaryConverter.toViewList(diaries);

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
/**
     * 指定したユーザーが日記を書いた日付のリストを取得し返却する
     * @param user
     * @return 日付のリスト
     */
    private List<LocalDate> getDates(UserView user){
        List<LocalDate> d = em.createNamedQuery(JpaConst.Q_DATES_GET_DIA, LocalDate.class)
                                                 .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                                                 .getResultList();
        System.out.println("private da size" +d.size());
        List<LocalDate> dates = new ArrayList<LocalDate>(new LinkedHashSet<>(d));
        return dates;

    }
}
