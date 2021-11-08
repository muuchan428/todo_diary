package actions.views;


import java.util.ArrayList;
import java.util.List;

import models.Diary;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class DiaryConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param dv DiaryViewのインスタンス
     * @return Diaryのインスタンス
     */
    public static Diary toModel(DiaryView dv) {
        return new Diary(
                dv.getId(),
                UserConverter.toModel(dv.getUser()),
                dv.getDiaryDate(),
                dv.getContent(),
                dv.getCreatedAt(),
                dv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param d Diaryのインスタンス
     * @return DiaryViewのインスタンス
     */
    public static DiaryView toView(Diary d) {

        if (d == null) {
            return null;
        }

        return new DiaryView(
                d.getId(),
                UserConverter.toView(d.getUser()),
                d.getDiaryDate(),
                d.getContent(),
                d.getCreatedAt(),
                d.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<DiaryView> toViewList(List<Diary> list) {
        List<DiaryView> dvs = new ArrayList<>();

        for (Diary d : list) {
            dvs.add(toView(d));
        }

        return dvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param d DTOモデル(コピー先)
     * @param dv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Diary d, DiaryView dv) {
        d.setId(dv.getId());
        d.setUser(UserConverter.toModel(dv.getUser()));
        d.setDiaryDate(dv.getDiaryDate());
        d.setContent(dv.getContent());
        d.setCreatedAt(dv.getCreatedAt());
        d.setUpdatedAt(dv.getUpdatedAt());

    }

    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param d DTOモデル(コピー元)
     * @param dv Viewモデル(コピー先)
     */
    public static void copyModelToView(Diary d, DiaryView dv) {
        dv.setId(d.getId());
        dv.setUser(UserConverter.toView(d.getUser()));
        dv.setDiaryDate(d.getDiaryDate());
        dv.setCreatedAt(d.getCreatedAt());
        dv.setUpdatedAt(d.getUpdatedAt());
    }

}