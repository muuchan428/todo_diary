package actions.views;


import java.util.ArrayList;
import java.util.List;

import models.Task;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class TaskConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param tv TaskViewのインスタンス
     * @return Taskのインスタンス
     */
    public static Task toModel(TaskView tv) {
        return new Task(
                tv.getId(),
                UserConverter.toModel(tv.getUser()),
                tv.getContent(),
                tv.getCreatedAt(),
                tv.getFinishedAt(),
                tv.getFinishFlag());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param t Taskのインスタンス
     * @return TaskViewのインスタンス
     */
    public static TaskView toView(Task t) {

        if (t == null) {
            return null;
        }

        return new TaskView(
                t.getId(),
                UserConverter.toView(t.getUser()),
                t.getContent(),
                t.getCreatedAt(),
                t.getFinishedAt(),
                t.getFinishFlag());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<TaskView> toViewList(List<Task> list) {
        List<TaskView> tvs = new ArrayList<>();

        for (Task t : list) {
            tvs.add(toView(t));
        }

        return tvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param t DTOモデル(コピー先)
     * @param tv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Task t, TaskView tv) {
        t.setId(tv.getId());
        t.setUser(UserConverter.toModel(tv.getUser()));
        t.setContent(tv.getContent());
        t.setCreatedAt(tv.getCreatedAt());
        t.setFinishedAt(tv.getFinishedAt());
        t.setFinishFlag(tv.getFinishFlag());

    }

    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param t DTOモデル(コピー元)
     * @param tv Viewモデル(コピー先)
     */
    public static void copyModelToView(Task t, TaskView tv) {
        tv.setId(t.getId());
        tv.setUser(UserConverter.toView(t.getUser()));
        tv.setCreatedAt(t.getCreatedAt());
        tv.setFinishedAt(t.getFinishedAt());
        tv.setFinishFlag(t.getFinishFlag());
    }

}