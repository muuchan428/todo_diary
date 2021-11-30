package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import actions.views.UserConverter;
import actions.views.UserView;
import actions.views.TaskConverter;
import actions.views.TaskView;
import constants.AttributeConst;
import constants.JpaConst;
import models.Task;

import models.validators.TaskValidator;
/**
 *
 * タスクテーブルの捜査にかかわる処理を行うクラス
 *
 */
public class TaskService extends ServiceBase {

    /**
     * 指定したユーザーの完了したタスクデータの件数を取得
     * @param user
     * @return  日記データの件数
     */
    public long countAllFin(UserView user) {

        long count = (long) em.createNamedQuery(JpaConst.Q_TSK_COUNT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .setParameter(JpaConst.JPQL_PARM_FLAG, AttributeConst.FIN_FLAG_TRUE.getIntegerValue())
                .getSingleResult();

        return count;
    }
    public long countAllMine(UserView user) {
        long count = (long)em.createNamedQuery(JpaConst.Q_TSK_COUNT_ALL,Long.class)
                            .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                            .getSingleResult();
        return count;
    }
    /**
     * idを条件に取得したデータをTaskViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public TaskView findOne(int id) {
        return TaskConverter.toView(findOneInternal(id));
    }
    /**
     * 指定されたユーザーが指定された日付に完了したタスクを表示
     * @param user
     * @param date
     * @return
     */
    public List<TaskView> getByDay(UserView user, LocalDate date){

        LocalDateTime startDate = LocalDateTime.of(date, LocalTime.of(0, 0));
        LocalDateTime endDate = LocalDateTime.of(date, LocalTime.of(23, 59, 59));

        List<Task> tasks = em.createNamedQuery(JpaConst.Q_TSK_GET_BY_DATE, Task.class)
                                        .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                                        .setParameter(JpaConst.JPQL_PARM_FLAG, AttributeConst.FIN_FLAG_TRUE.getIntegerValue())
                                        .setParameter(JpaConst.JPQL_PARM_S_DATE, startDate)
                                        .setParameter(JpaConst.JPQL_PARM_E_DATE,endDate)
                                        .getResultList();

        return TaskConverter.toViewList(tasks);
    }
    /**
     * 指定したユーザーがタスクを完了した年月のリストを返却する
     * @param user
     * @return
     */
    public List<YearMonth> getMonths(UserView user){
       List<LocalDate> dates = getDates(user);
       List<YearMonth> month = new ArrayList<YearMonth>();
       for(LocalDate d: dates ) {
           YearMonth m = YearMonth.of(d.getYear(), d.getMonthValue());
           month.add(m);
       }
         List<YearMonth> months = new ArrayList<YearMonth>(new LinkedHashSet<>(month));
         return months;
    }
    public List<TaskView> getAllNotFinish(UserView user) {
        List<Task> tasks = em.createNamedQuery(JpaConst.Q_TSK_GET_NOT_FIN, Task.class)
                                        .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                                        .setParameter(JpaConst.JPQL_PARM_FLAG, AttributeConst.FIN_FLAG_FALSE.getIntegerValue())
                                        .getResultList();
        return TaskConverter.toViewList(tasks);
    }

    /**
     * 指定したユーザーの指定した日付に作成されたタスクデータのリストを返却
     * @param user
     * @param date
     * @return タスクデータのリスト
     */
    public List<TaskView> getCreatedAtMonth(UserView user, YearMonth date){
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonthValue(), 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth(), 23, 59);

        List<Task> tasks = em.createNamedQuery(JpaConst.Q_TSK_GET_BY_DATE, Task.class)
                .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                .setParameter(JpaConst.JPQL_PARM_FLAG, AttributeConst.FIN_FLAG_TRUE.getIntegerValue())
                .setParameter(JpaConst.JPQL_PARM_S_DATE, startDate)
                .setParameter(JpaConst.JPQL_PARM_E_DATE, endDate)
                .getResultList();

        return TaskConverter.toViewList(tasks);

    }
    /**
     * 指定したユーザーがタスクを完了した日付のリストを取得し返却する
     * @param user
     * @return 日付のリスト
     */
    public  List<LocalDate> getDates(UserView user){
        List<LocalDateTime> d = em.createNamedQuery(JpaConst.Q_DATES_GET_FIN_TSK, LocalDateTime.class)
                                                             .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                                                             .setParameter(JpaConst.JPQL_PARM_FLAG, AttributeConst.DEL_FLAG_TRUE.getIntegerValue())
                                                             .getResultList();
        List<LocalDate>  dateList = new ArrayList<LocalDate>();
        for(LocalDateTime date : d) {
            LocalDate i = LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
            dateList.add(i);
        }
        List<LocalDate> dates = new ArrayList<LocalDate>(new LinkedHashSet<>(dateList));
        return dates;

    }

    /**
     * 指定したユーザーが指定した月にタスクを完了した日付のリストを取得し返却する
     * @param user
     * @return 日付のリスト
     */
    public  List<LocalDate> getDates(UserView user, YearMonth m){
        List<TaskView> tasks = getCreatedAtMonth(user, m);
        List<LocalDate>  dateList = new ArrayList<LocalDate>();
        for(TaskView task : tasks) {
            LocalDateTime i = task.getFinishedAt();
            dateList.add(LocalDate.of(i.getYear(),i.getMonthValue(),i.getDayOfMonth()) );
        }
        List<LocalDate> dates = new ArrayList<LocalDate>(new LinkedHashSet<>(dateList));
        return dates;

    }





    /**
     * 画面から入力されたタスクの登録内容を元にデータを1件作成し、タスクテーブルに登録する
     * @param tv タスクの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(TaskView tv) {
        List<String> errors = TaskValidator.validate(tv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            tv.setCreatedAt(ldt);
            tv.setFinishedAt(ldt);
            createInternal(tv);
        }
        //バリデーションで発生したエラーを返却
        return errors;
    }
    public List<String> update(TaskView tv){
      //バリデーションを行う
        List<String> errors = TaskValidator.validate(tv);

        if (errors.size() == 0) {

            updateInternal(tv);

        }
        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
            return errors;
    }
    /**
     * IDをもとに、タスクデータを更新する
     * @param tv タスクの更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> complete(TaskView tv) {

        //バリデーションを行う
        List<String> errors = TaskValidator.validate(tv);

        if (errors.size() == 0) {
            //完了日時を現在時刻に設定し、完了フラグを立てる
            tv.setFinishedAt(LocalDateTime.now());
            tv.setFinishFlag(AttributeConst.FIN_FLAG_TRUE.getIntegerValue());

            updateInternal(tv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    public void destroy(Integer id) {
       Task task = em.find(Task.class, id);

        em.getTransaction().begin();
        em.remove(task);
        em.getTransaction().commit();

    }
    /**
     * 指定したユーザーの作成したタスクデータをすべて削除する
     * （アカウント削除の際に使用する）
     * @param user
     */
    public void destroyAll(UserView user) {
        //ユーザーを条件にすべてのタスクを取得
        List<Task> tasks = em.createNamedQuery(JpaConst.Q_TSK_GET_ALL_MINE, Task.class)
                                         .setParameter(JpaConst.JPQL_PARM_USER, UserConverter.toModel(user))
                                        .getResultList();
        //タスクをすべて削除
        if(tasks.size() != 0) {
            for(Task task : tasks) {
                em.getTransaction().begin();
                em.remove(task);
                em.getTransaction().commit();
            }
        }
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Task findOneInternal(int id) {
        return em.find(Task.class, id);
    }

    /**
     * タスクデータを1件登録する
     * @param tv タスクデータ
     */
    private void createInternal(TaskView tv) {

        em.getTransaction().begin();
        em.persist(TaskConverter.toModel(tv));
        em.getTransaction().commit();

    }

    /**
     * タスクデータを更新する
     * @param tv タスクデータ
     */
    private void updateInternal(TaskView tv) {

        em.getTransaction().begin();
        Task t = findOneInternal(tv.getId());
        TaskConverter.copyViewToModel(t, tv);
        em.getTransaction().commit();

    }

}
